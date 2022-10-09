package shop.programs

import cats.MonadThrow
import cats.data.NonEmptyList
import cats.syntax.all._
import retry.RetryPolicies._
import retry.RetryPolicy
import shop.domain.checkout.Card
import shop.domain.order.{EmptyCartError, OrderError, OrderId, PaymentError, PaymentId}
import shop.domain.payment.Payment
import shop.domain.user.UserId
import shop.retries.{Retriable, Retry}
import shop.services._
import org.typelevel.log4cats.Logger
import shop.domain.cart.CartItem
import squants.market.Money

import scala.concurrent.duration._

final class Checkout[F[_]: MonadThrow: Retry: Logger](
    payments: PaymentClient[F],
    cart: ShoppingCart[F],
    orders: Orders[F],
    policy: RetryPolicy[F]
) {

  private def ensureNonEmpty[A](xs: List[A]): F[NonEmptyList[A]] =
    MonadThrow[F].fromOption(
      NonEmptyList.fromList(xs),
      EmptyCartError
    )

  private def processPayment(in: Payment): F[PaymentId] =
    Retry[F]
      .retry(policy, Retriable.Payments)(payments.process(in))
      .adaptError { case e =>
        PaymentError(Option(e.getMessage).getOrElse("Unknown"))
      }

  private def createOrder(
      userId: UserId,
      paymentId: PaymentId,
      items: NonEmptyList[CartItem],
      total: Money
  ): F[OrderId] = {
    val action = Retry[F]
      .retry(policy, Retriable.Orders)(orders.create(userId, paymentId, items, total))
      .adaptError {
        case e => OrderError(Option(e.getMessage).getOrElse("Unknown"))
      }
    def bgAction(fa: F[OrderId]): F[OrderId] =
      fa.onError {
        case _ =>
          Logger[F].error(
            s"Failed to create order for: ${paymentId.show}"
          ) *> Background[F]
      }
  }

  val retryPolicy =
    limitRetries[F](3) |+| exponentialBackoff[F](10.milliseconds)

  def process(userId: UserId, card: Card): F[OrderId] =
    for {
      c <- cart.get(userId)
      its <- ensureNonEmpty(c.items)
      pid <- processPayment(Payment(userId, c.total, card))
      oid <- orders.create(userId, pid, its, c.total)
      _ <- cart.delete(userId).attempt.void
    } yield oid
}
