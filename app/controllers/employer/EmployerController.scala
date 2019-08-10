/*
 * This file is part of Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.employer

// import model.component.util.ViewValuePageLayout
// import model.site.app.SiteViewValueNewEmployer
// import persistence.employer.dao.{EmployerDAO, EmployerLoginDAO}
// import persistence.geo.dao.LocationDAO
// import persistence.geo.model.Location
// import persistence.employer.model.Employer.formForNewEmployer
// import persistence.employer.model.EmployerLogin
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}

// 登録: 新規ユーザー
//~~~~~~~~~~~~~~~~~~~~~
class EmployerController @javax.inject.Inject()(
// val daoLocation:  LocationDAO,
// val daoEmployer: EmployerDAO,
// val daoEmployerLogin: EmployerLoginDAO,
cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext

  /**
   * 新規登録ページ
   */
  def newEmployer = TODO
  // Action.async { implicit request =>
  //   for {
  //     locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
  //   } yield {
  //     val vv = SiteViewValueNewEmployer(
  //       layout   = ViewValuePageLayout(id = request.uri),
  //       location = locSeq
  //     )
  //     Ok(views.html.site.employer.Main(vv, formForNewEmployer))
  //   }
  // }

  /**
    * 新規登録処理
    */
  def registerEmployer = TODO
  // Action.async { implicit request =>
  //   formForNewEmployer.bindFromRequest.fold(
  //     errors => {
  //       for {
  //         locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
  //       } yield {
  //         println(errors)
  //         val vv = SiteViewValueNewEmployer(
  //           layout   = ViewValuePageLayout(id = request.uri),
  //           location = locSeq
  //         )
  //         BadRequest(views.html.site.employer.Main(vv, errors))
  //       }
  //     },
  //     employer   => {
  //       for {
  //         aid <- daoEmployer.add(employer)
  //         _   <- daoEmployerLogin.update(
  //           EmployerLogin(
  //             aid = Some(aid),
  //             email = employer.email,
  //             password = employer.password
  //           )
  //         )
  //       } yield {
  //         // TODO: セッション追加処理
  //         Redirect("/employer_post")
  //           .withSession(
  //             request.session + ("aid" -> aid.toString)
  //           )
  //       }
  //     }
  //   )
  // }

}
