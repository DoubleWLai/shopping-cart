package shop.services

import shop.domain.user.{JwtToken, PassWord, User, UserName}

trait Auth[F[_]] {

  def findUser(token: JwtToken): F[Option[User]]

  def newUser(username: UserName, password: PassWord): F[JwtToken]

  def login(username: UserName, password: PassWord): F[JwtToken]

  def logout(token: JwtToken, username: UserName): F[Unit]

}
