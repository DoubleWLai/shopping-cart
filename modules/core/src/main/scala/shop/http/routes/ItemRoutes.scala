package shop.http.routes

import cats.Monad
import cats.implicits.toBifunctorOps
import eu.timepit.refined.api.{Refined, Validate}
import eu.timepit.refined.refineV
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.{HttpRoutes, ParseFailure, QueryParamDecoder}
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router
import shop.domain.brand.BrandParam
import shop.services.Items

final case class ItemRoutes[F[_]: Monad](items: Items[F]) extends Http4sDsl[F] {

  private[routes] val prefixPath = "/items"

  object BrandQueryParam extends OptionalQueryParamDecoderMatcher[BrandParam]("brand")

  implicit def refinedParamDecoder[T: QueryParamDecoder, P](implicit
      ev: Validate[T, P]
  ): QueryParamDecoder[T Refined P] =
    QueryParamDecoder[T].emap(
      refineV[P](_).leftMap(m => ParseFailure(m, m))
    )

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] {
      case GET -> Root :? BrandQueryParam(brand) =>
        Ok(brand.fold(items.findAll)(b => items.findBy(b.toDomain)))
    }

  val routes: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )

}
