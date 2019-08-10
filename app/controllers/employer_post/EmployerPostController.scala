package controllers.employer_post

import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}
// persistence: 永続化
import persistence.employer_post.model.EmployerPost
import persistence.employer_post.dao.EmployerPostDAO

import persistence.employer.dao.EmployerDAO

import persistence.geo.model.Location
import persistence.geo.dao.LocationDAO
// model
import model.site.app.SiteViewValueEmployerPostIndex
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

  def index = TODO

  def add = TODO

  def create = TODO

  def show(id: Long) = TODO

  def edit(id: Long) = TODO

  def update(id: Long) = TODO

  def destroy(id: Long) = TODO
}