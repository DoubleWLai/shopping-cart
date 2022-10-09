package shop.domain

import derevo.cats.show
import derevo.derive
import io.estatico.newtype.macros.newtype
import shop.domain.cart.Quantity
import shop.domain.item.ItemId
import squants.market.Money

import java.util.UUID
import scala.util.control.NoStackTrace

object order {

  @newtype case class OrderId(uuid: UUID)

  @derive(show)
  @newtype case class PaymentId(uuid: UUID)

  case class Order(
      id: OrderId,
      pid: PaymentId,
      items: Map[ItemId, Quantity],
      total: Money
  )


  case object EmptyCartError extends NoStackTrace


  sealed trait OrderOrPaymentError extends NoStackTrace {
    def cause: String
  }

  case class OrderError(cause: String) extends OrderOrPaymentError
  case class PaymentError(cause: String) extends OrderOrPaymentError

}
