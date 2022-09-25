package shop.services

import shop.domain.user.{PassWord, User, UserId, UserName}

trait Users[F[_]] {

  def find(username: UserName, password: PassWord): F[Option[User]]

  def create(username: UserName, password: PassWord): F[UserId]

}
