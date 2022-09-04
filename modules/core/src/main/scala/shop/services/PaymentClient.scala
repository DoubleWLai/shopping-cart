package main.scala.shop.services

import main.scala.shop.domain.order.PaymentId
import main.scala.shop.domain.payment.Payment

trait PaymentClient[F[_]] {

  def process(payment: Payment): F[PaymentId]

}
