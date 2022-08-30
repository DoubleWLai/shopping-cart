package main.scala.shop.services

import main.scala.shop.domain.category.{Category, CategoryName}

trait Categories[F[_]] {

  def findAll: F[List[Category]]

  def create(name: CategoryName): F[Unit]

}
