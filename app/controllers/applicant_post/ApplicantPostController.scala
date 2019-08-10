package controllers.applicant_post

import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}
// persistence: 永続化
import persistence.applicant_post.model.ApplicantPost
import persistence.applicant_post.dao.ApplicantPostDAO

import persistence.applicant.dao.ApplicantDAO

import persistence.geo.model.Location
import persistence.geo.dao.LocationDAO
// model
import model.site.app.SiteViewValueApplicantPostIndex
// import model.site.facility.SiteViewValueFacilityShow
// import model.site.facility.SiteViewValueFacilityEdit
// import model.site.facility.SiteViewValueFacilityAdd

import model.component.util.ViewValuePageLayout
import persistence.category.dao.CategoryDAO
import model.site.app.SiteViewValueApplicantPostShow
import persistence.category.model.Category
import persistence.applicant_post.model.ApplicantItem
import persistence.geo.model.Location



// 施設
//~~~~~~~~~~~~~~~~~~~~~
class ApplicantPostController @javax.inject.Inject()(
  // val: immutable, var: mutable
  val applicantPostDao: ApplicantPostDAO,
  val applicantDao: ApplicantDAO,
  val daoLocation: LocationDAO,
  val categoryDao: CategoryDAO,
  cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext

  def index = Action.async { implicit request =>
    for {
      applicantPost <- applicantPostDao.findAll
      categorys <- categoryDao.findAll
      locations <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
    } yield {
      val item = applicantPost.map(p => {
        val location = locations.find(_.id == p.locationId)
        val category1 = categorys.find(_.id == p.categoryId1)
        val category2 = categorys.find(_.id == p.categoryId2)
        val category3 = categorys.find(_.id == p.categoryId3)
        ApplicantItem(
          applicantPost = p,
          locationName = location.map(_.namePref).getOrElse("none"),
          category1 = category1.map(_.name).getOrElse("none"),
          category2 = category2.map(_.name).getOrElse("none"),
          category3 = category3.map(_.name).getOrElse("none")
        )
      })

      val vv = SiteViewValueApplicantPostIndex(
        layout = ViewValuePageLayout(id = request.uri),
        posts = item    
      )
      Ok(views.html.site.applicant_post.index.Main(vv))
    }
  }

  def add = TODO

  def create = TODO

  def show(id: Long) = TODO

  def edit(id: Long) = TODO

  def update(id: Long) = TODO

  def destroy(id: Long) = TODO
}