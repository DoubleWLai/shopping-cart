package shop.services

trait HealthCheck[F[_]] {

  def status: F[AppStatus]

}
