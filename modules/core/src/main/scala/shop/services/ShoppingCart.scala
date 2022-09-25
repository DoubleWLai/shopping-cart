package main.scala.shop.services

import main.scala.shop.domain.cart.{Cart, CartTotal, Quantity}
import main.scala.shop.domain.item.ItemId
import main.scala.shop.domain.user.UserId

trait ShoppingCart[F[_]] {

  def add(userId: UserId, itemId: ItemId, quantity: Quantity): F[Unit]

  def delete(userId: UserId): F[Unit]

  def get(userId: UserId): F[CartTotal]

  def removeItem(userId: UserId, itemId: ItemId): F[Unit]

  def update(userId: UserId, cart: Cart): F[Unit]

}
