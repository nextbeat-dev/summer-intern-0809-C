package controllers.login

import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}
import persistence.applicant.model.ApplicantLogin.formForApplicantLogin
import model.component.util.ViewValuePageLayout
import model.site.app.{SiteViewValueApplicant, SiteViewValueApplicantLogin, SiteViewValueNewUser}
import persistence.applicant.dao.{ApplicantDAO, ApplicantLoginDAO}
import persistence.geo.model.Location
import persistence.udb.dao.UserDAO
import persistence.udb.model.User.formForNewUser

import scala.concurrent.Future


// ログイン
//~~~~~~~~~~~~~~~~~~~~~
class LoginController @javax.inject.Inject()(
  val applicantDAO     : ApplicantDAO,
  val applicantLoginDAO: ApplicantLoginDAO,
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
              println(x.aid)
              Redirect("/recruit/intership-for-summer-21")
                .withSession(
                  request.session + ("aid" -> x.aid.getOrElse(0).toString)
                )
            }
            case None    => {
              println("NO")
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
    val vv = SiteViewValueApplicantLogin(
      layout = ViewValuePageLayout(id = request.uri)
    )
    Future(Ok(views.html.site.login.applicant.Main(vv, formForApplicantLogin)))
  }}

  /**
    * 応募者ログイン画面
    */
  def employerAuth = Action.async { implicit request => {
    val vv = SiteViewValueApplicantLogin(
      layout = ViewValuePageLayout(id = request.uri)
    )
    Future(Ok(views.html.site.login.applicant.Main(vv, formForApplicantLogin)))
  }}

}
