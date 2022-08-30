package main.scala.shop.services

trait Categories[F[_]] {

  def findAll: F[List[Category]]

  def create(name: CategoryName): F[Unit]

}
