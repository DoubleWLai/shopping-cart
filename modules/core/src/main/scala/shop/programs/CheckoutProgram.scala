package shop.programs

import cats.effect.IO
import shop.domain.checkout.Card
import shop.domain.order.OrderId
import shop.domain.payment.Payment
import shop.domain.user.UserId
import shop.services.{Orders, PaymentClient, ShoppingCart}

final class CheckoutProgram[F[_]](
    paymentClient: PaymentClient[IO],
    shoppingCart: ShoppingCart[IO],
    orders: Orders[IO]
) {

  def checkout(userId: UserId, card: Card): IO[OrderId] =
    for {
      cart <- shoppingCart.get(userId)
      paymentId <- paymentClient.process(Payment(userId, cart.total, card))
      orderId <- orders.create(userId, paymentId, cart.items, cart.total)
      _ <- shoppingCart.delete(userId).attempt.void
    } yield orderId

}
