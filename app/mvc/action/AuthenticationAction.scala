package mvc.action

import persistence.applicant.model.Applicant
import play.api.mvc._
import play.api.mvc.Results._

import scala.concurrent.{ExecutionContext, Future}

case class ApplicantRequest[A](
  aid:  Applicant.Id,
  request: Request[A]
) extends WrappedRequest[A](request)

case class AuthenticationAction(userType: Int)(implicit val executionContext: ExecutionContext
) extends ActionRefiner[Request, ApplicantRequest] {

  val userTypeApplicant = 0
  val userTypeEmployer  = 1

  protected def refine[A](request: Request[A]): Future[Either[Result, ApplicantRequest[A]]] = {
    println(request.session)
    val sUserIdOpt = if(userType==userTypeApplicant)request.session.get("aid") else request.session.get("eid")
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