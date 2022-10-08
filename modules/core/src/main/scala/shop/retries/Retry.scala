package shop.retries

import cats.effect.Temporal

import scala.concurrent.duration.DurationInt


trait Retry[F[_]] {

  def retry[A](fa: F[A]): F[A] =
    Temporal[F].sleep(50.milliseconds) >> retry(fa)

}
