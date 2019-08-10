package mvc.action

import persistence.applicant.model.Applicant
import play.api.mvc._
import play.api.mvc.Results._

import scala.concurrent.{ExecutionContext, Future}

case class ApplicantRequest[A](
  aid:  Applicant.Id,
  request: Request[A]
) extends WrappedRequest[A](request)

case class AuthenticationAction()(implicit val executionContext: ExecutionContext
) extends ActionRefiner[Request, ApplicantRequest] {

  protected def refine[A](request: Request[A]): Future[Either[Result, ApplicantRequest[A]]] = {
    val sUserIdOpt = request.session.get("aid")
    val next = sUserIdOpt match {
      case None          => Left(Redirect("/", 301))
      case Some(sApplicantId) => {
        val applicantId      = sApplicantId.toLong
        val applicantRequest = ApplicantRequest(applicantId, request)
        Right(applicantRequest)
      }
    }
    Future.successful(next)
  }
}