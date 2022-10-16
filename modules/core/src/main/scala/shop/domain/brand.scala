package shop.domain

import derevo.circe.magnolia.{decoder, encoder}
import derevo.derive
import io.estatico.newtype.macros.newtype

import java.util.UUID

object brand {
  @newtype case class BrandId(value: UUID)
  @newtype case class BrandName(value: String)

  @derive(decoder, encoder)
  case class Brand(uuid: BrandId, name: BrandName)

}
