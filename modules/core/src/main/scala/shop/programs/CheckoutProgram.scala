package shop.programs

import cats.Monad
import shop.domain.checkout.Card
import shop.domain.order.OrderId
import shop.domain.payment.Payment
import shop.domain.user.UserId
import shop.services.{Orders, PaymentClient, ShoppingCart}

final class CheckoutProgram[F[_]: Monad](
    paymentClient: PaymentClient[F],
    shoppingCart: ShoppingCart[F],
    orders: Orders[F]
) {

  def checkout(userId: UserId, card: Card): F[OrderId] =
    for {
      cart <- shoppingCart.get(userId)
//      paymentId <- paymentClient.process(Payment(userId, cart.total, card))
//      orderId <- orders.create(userId, paymentId, cart.items, cart.total)
//      _ <- shoppingCart.delete(userId)
    } yield cart

}
