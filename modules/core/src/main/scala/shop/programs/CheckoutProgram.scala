package shop.programs

import cats.effect.IO
import org.typelevel.log4cats.Logger
import retry.RetryDetails.{GivingUp, WillDelayAndRetry}
import retry.{RetryDetails, retryingOnAllErrors}
import shop.domain.checkout.Card
import shop.domain.order.{OrderId, PaymentId}
import shop.domain.payment.Payment
import shop.domain.user.UserId
import shop.services.{Orders, PaymentClient, ShoppingCart}

final class CheckoutProgram[F[_]](
    paymentClient: PaymentClient[IO],
    shoppingCart: ShoppingCart[IO],
    orders: Orders[IO]
) {

//  def retry[A](fa: F[A]): F[A] = Timer[F]

  def logError(action: String)(e: Throwable, details: RetryDetails): F[Unit] =
    details match {
      case r: WillDelayAndRetry =>
        Logger[F].error(
          s"Failed on $action. We retried ${r.retriesSoFar} times."
        )
      case g: GivingUp =>
        Logger[F].error(
          s"Giving up on $action after ${g.totalRetries} retries."
        )
    }


//  def processPayment(payment: Payment): F[Payment] = {
//    val action = retryingOnAllErrors[PaymentId](
//      policy = retryPolicy
//    )
//  }

  def checkout(userId: UserId, card: Card): IO[OrderId] =
    for {
      cart <- shoppingCart.get(userId) // let it fail, returning some kind of error message
      paymentId <- paymentClient.process(Payment(userId, cart.total, card))  // 1.other than 409, retry and log. 2.409: extract id and continue
      orderId <- orders.create(userId, paymentId, cart.items, cart.total) // if failed to persist DB: retry
      _ <- shoppingCart.delete(userId).attempt.void
    } yield orderId

}
