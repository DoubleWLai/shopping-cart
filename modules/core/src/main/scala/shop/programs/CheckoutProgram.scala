package main.scala.shop.domain.programs

import cats.Monad
import main.scala.shop.domain.cart.CartTotal
import main.scala.shop.domain.checkout.Card
import main.scala.shop.domain.order.OrderId
import main.scala.shop.domain.payment.Payment
import main.scala.shop.domain.user.UserId
import main.scala.shop.services.{Orders, PaymentClient, ShoppingCart}

final class CheckoutProgram[F[_]: Monad](
    paymentClient: PaymentClient[F],
    shoppingCart: ShoppingCart[F],
    orders: Orders[F]
) {

  def checkout(userId: UserId, card: Card): F[OrderId] =
    for {
      cart <- shoppingCart.get(userId)
      paymentId <- paymentClient.process(Payment(userId, cart.total, card))
    } yield


}
