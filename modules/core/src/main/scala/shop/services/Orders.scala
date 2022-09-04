package main.scala.shop.services

import main.scala.shop.domain.cart.CartItem
import main.scala.shop.domain.order.{Order, OrderId, PaymentId}
import squants.market.Money

trait Orders[F[_]] {

  def get(userId: UserId, orderId: OrderId): F[Option[Order]]

  def findBy(userId: UserId): F[List[Order]]

  def create(
      userId: UserId,
      paymentId: PaymentId,
      items: List[CartItem],
      total: Money
  ): F[OrderId]

}