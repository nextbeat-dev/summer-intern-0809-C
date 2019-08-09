
package controllers.facility

import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}
import persistence.applicant.model.Applicant.formForNewApplicant

import model.component.util.ViewValuePageLayout
import model.site.app.SiteViewValueApplicant
import persistence.udb.dao.UserDAO

import scala.concurrent.Future


// ログイン
//~~~~~~~~~~~~~~~~~~~~~
class LoginController @javax.inject.Inject()(
  val userDAO: UserDAO,
  cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext

  /**
    * ログイン画面
    */
  def login = Action.async { implicit request => {
    val vv = SiteViewValueApplicant(
      layout = ViewValuePageLayout(id = request.uri)
    )
    Future(Ok(views.html.site.login.applicant.Main(vv, formForNewApplicant)))
  }}

}
