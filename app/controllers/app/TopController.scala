/*
 * This file is part of Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package controllers.app

import javax.inject.Inject
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}
import persistence.geo.dao.LocationDAO
import persistence.udb.dao.UserDAO
import persistence.geo.model.Location
import persistence.udb.model.User.formForNewUser
import model.site.app.SiteViewValueNewUser
import model.component.util.ViewValuePageLayout
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

// TOPページ
//~~~~~~~~~~~~~~~~~~~~~
class TopController @Inject()(
cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {

  def index = Action { implicit request =>
    Ok(views.html.site.top.Index())
  } 
}