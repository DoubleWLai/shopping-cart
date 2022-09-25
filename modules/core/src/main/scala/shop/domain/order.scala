package shop.domain

import io.estatico.newtype.macros.newtype
import shop.domain.cart.Quantity
import shop.domain.item.ItemId
import squants.market.Money

import java.util.UUID

object order {

  @newtype case class OrderId(uuid: UUID)

  @newtype case class PaymentId(uuid: UUID)

  case class Order(id: OrderId, pid: PaymentId, items: Map[ItemId, Quantity], total: Money)

}
