package controllers.login

import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}
import persistence.applicant.model.ApplicantLogin.formForApplicantLogin
import persistence.employer.model.EmployerLogin.formForEmployerLogin
import model.component.util.ViewValuePageLayout
import model.site.app.{SiteViewValueApplicantLogin, SiteViewValueEmployerLogin}
import persistence.applicant.dao.{ApplicantDAO, ApplicantLoginDAO}
import persistence.employer.dao.{EmployerDAO, EmployerLoginDAO}
import persistence.geo.model.Location
import persistence.udb.dao.UserDAO
import persistence.udb.model.User.formForNewUser

import scala.concurrent.Future


// ログイン
//~~~~~~~~~~~~~~~~~~~~~
class LoginController @javax.inject.Inject()(
  val applicantDAO:      ApplicantDAO,
  val applicantLoginDAO: ApplicantLoginDAO,
  val employerDAO:       EmployerDAO,
  val employerLoginDAO:  EmployerLoginDAO,
  cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext

  /**
    * 応募者ログイン画面
    */
  def applicantLogin = Action { implicit request => {
    val vv = SiteViewValueApplicantLogin(
      layout = ViewValuePageLayout(id = request.uri)
    )
    Ok(views.html.site.login.applicant.Main(vv, formForApplicantLogin))
  }}

  /**
    * 応募者ログイン処理
    */
  def applicantAuth = Action.async { implicit request => {
    formForApplicantLogin.bindFromRequest.fold(
      errors => {
        val vv = SiteViewValueApplicantLogin(
          layout = ViewValuePageLayout(id = request.uri)
        )
        Future(BadRequest(views.html.site.login.applicant.Main(vv, errors)))
      },
      user   => {
        for {
          loginInfo <- applicantLoginDAO.auth(user)
        } yield {
          loginInfo match {
            case Some(x) => {
              // TODO: セッション追加処理
              println(x.aid.get)
              Redirect(s"/applicant/${x.aid.get}")
                .withSession(
                  request.session + ("aid" -> x.aid.get.toString)
                )
            }
            case None    => {
              val vv = SiteViewValueApplicantLogin(
                layout = ViewValuePageLayout(id = request.uri)
              )
              BadRequest(views.html.site.login.applicant.Main(vv, formForApplicantLogin))
            }
          }
        }
      }
    )
  }}

  /**
    * 募集者ログイン画面
    */
  def employerLogin = Action.async { implicit request => {
    val vv = SiteViewValueEmployerLogin(
      layout = ViewValuePageLayout(id = request.uri)
    )
    Future(Ok(views.html.site.login.employer.Main(vv, formForEmployerLogin)))
  }}

  /**
    * 募集者ログイン処理
    */
  def employerAuth = Action.async { implicit request => {
    formForEmployerLogin.bindFromRequest.fold(
      errors => {
        val vv = SiteViewValueEmployerLogin(
          layout = ViewValuePageLayout(id = request.uri)
        )
        Future(BadRequest(views.html.site.login.employer.Main(vv, errors)))
      },
      user   => {
        for {
          loginInfo <- employerLoginDAO.auth(user)
        } yield {
          loginInfo match {
            case Some(x) => {
              // TODO: セッション追加処理
              Redirect(s"/employer/${x.eid.get}")
                .withSession(
                  request.session + ("eid" -> x.eid.get.toString)
                )
            }
            case None    => {
              val vv = SiteViewValueEmployerLogin(
                layout = ViewValuePageLayout(id = request.uri)
              )
              BadRequest(views.html.site.login.employer.Main(vv, formForEmployerLogin))
            }
          }
        }
      }
    )
  }}

  def logout = Action.async { implicit request => {
    Future(Redirect("/").withNewSession)
  }}

}
