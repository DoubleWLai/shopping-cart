package main.scala.shop.domain

import main.scala.shop.domain.checkout.Card
import main.scala.shop.domain.user.UserId
import squants.market.Money

object payment {

  case class Payment(id: UserId, total: Money, card: Card)

}
