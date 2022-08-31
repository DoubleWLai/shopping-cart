package main.scala.shop.services

import main.scala.shop.domain.item.{CreateItem, Item, ItemId, UpdateItem}
import shop.domain.brand.BrandName

trait Items[F[_]] {

  def findAll: F[List[Item]]

  def findBy(brand: BrandName): F[List[Item]]

  def findById(itemId: ItemId): F[Option[Item]]

  def create(item: CreateItem): F[Unit]

  def update(item: UpdateItem): F[Unit]

}
