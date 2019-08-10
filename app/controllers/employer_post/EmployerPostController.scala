package controllers.employer_post

import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}
// persistence: 永続化
import persistence.employer_post.model.EmployerPost.formForEmployerPost
import persistence.employer_post.dao.EmployerPostDAO

import persistence.employer.dao.EmployerDAO

import persistence.geo.model.Location
import persistence.geo.dao.LocationDAO
// model
import model.site.app.SiteViewValueEmployerPostIndex
import model.site.app.SiteViewValueEmployerPost
import model.component.util.ViewValuePageLayout


// 施設
//~~~~~~~~~~~~~~~~~~~~~
class EmployerPostController @javax.inject.Inject()(
  // val: immutable, var: mutable
  val employerPostDao: EmployerPostDAO,
  val employerDao: EmployerDAO,
  val daoLocation: LocationDAO,
  cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext

  def index = Action.async { implicit request =>
    for {
        postSeq <- employerPostDao.findAll
    } yield {
        val vv = SiteViewValueEmployerPostIndex(
            layout = ViewValuePageLayout(id = request.uri),
            posts = postSeq
        )
        Ok(views.html.site.employer_post.index.Main(vv))
    }
  }

  def add = Action.async { implicit request =>
    for {
      locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
    } yield {
      val vv = SiteViewValueEmployerPost(
        layout   = ViewValuePageLayout(id = request.uri),
        location = locSeq
      )
      Ok(views.html.site.employer_post.add.Main(vv, formForEmployerPost))
    }
  }

  def create = Action.async { implicit request =>   
    formForEmployerPost.bindFromRequest.fold(
      errors => {
        for {
          locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
        } yield {
          println(errors)
          val vv = SiteViewValueEmployerPost(
            layout   = ViewValuePageLayout(id = request.uri),
            location = locSeq
          )
          BadRequest(views.html.site.employer_post.add.Main(vv, errors))
        }
      },
      form   => {        
        for {
          locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
          employer <- employerDao.get(1)
          _ <- employerPostDao.create(form)
        } yield {          
          val vv = SiteViewValueEmployerPost(
            layout   = ViewValuePageLayout(id = request.uri),
            location = locSeq
          )
          Ok(views.html.site.employer_post.add.Main(vv, formForEmployerPost))
        }
      }
    )
  }

  def show(id: Long) = TODO

  def edit(id: Long) = TODO

  def update(id: Long) = TODO

  def destroy(id: Long) = TODO
}