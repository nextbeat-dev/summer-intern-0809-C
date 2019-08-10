package controllers.employer_post

import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}
// persistence: 永続化
import persistence.employer_post.model.EmployerPost
import persistence.employer_post.model.EmployerPost.formForEmployerPostAdd
import persistence.employer_post.dao.EmployerPostDAO

import persistence.employer.dao.EmployerDAO

import persistence.geo.model.Location
import persistence.geo.dao.LocationDAO
// model
import model.site.app.SiteViewValueEmployerPostIndex
import model.site.app.SiteViewValueEmployerPostAdd
// import model.site.facility.SiteViewValueFacilityShow
// import model.site.facility.SiteViewValueFacilityEdit
// import model.site.facility.SiteViewValueFacilityAdd

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
      val vv = SiteViewValueEmployerPostAdd(
        layout   = ViewValuePageLayout(id = request.uri),
        location = locSeq
      )
      Ok(views.html.site.employer_post.add.Main(vv, formForEmployerPostAdd))
    }
  }

  def create = Action.async { implicit request =>   
    formForEmployerPostAdd.bindFromRequest.fold(
      errors => {
        for {
          locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
        } yield {
          println(errors)
          val vv = SiteViewValueEmployerPostAdd(
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
          _ <- employerPostDao.create(
            employer.get.id.get,
            form.locationId,
            form.title,
            form.address,
            form.description,
            // form.main_image,
            // form.thumbnail_image,
            form.price,
            // form.categoryId1,
            // form.categoryId2,
            // form.categoryId3,
            // form.job_date,
          )
        } yield {          
          val vv = SiteViewValueEmployerPostAdd(
            layout   = ViewValuePageLayout(id = request.uri),
            location = locSeq
          )
          Ok(views.html.site.employer_post.add.Main(vv, formForEmployerPostAdd))
        }
      }
    )
  }

  def show(id: Long) = TODO

  def edit(id: Long) = TODO

  def update(id: Long) = TODO

  def destroy(id: Long) = TODO
}