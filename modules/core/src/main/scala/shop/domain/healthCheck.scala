package shop.domain

import derevo.circe.magnolia.encoder
import derevo.derive
import io.estatico.newtype.macros.newtype

object healthCheck {

  sealed trait Status
  object Status {
    case object OKay extends Status
    case object Unreachable extends Status
  }

  @newtype
//  @derive(encoder)
  case class RedisStatus(value: Status)

  @newtype
  //  @derive(encoder)
  case class PostgresStatus(value: Status)

  case class AppStatus(redis: RedisStatus, postgres: PostgresStatus)

}
