package controllers.applicant_post

import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}
// persistence: 永続化
import persistence.applicant_post.model.ApplicantPost.formForApplicantPost
import persistence.applicant_post.dao.ApplicantPostDAO

import persistence.employer.dao.EmployerDAO

import persistence.geo.model.Location
import persistence.geo.dao.LocationDAO
import model.site.app.SiteViewValueApplicantPostIndex

import persistence.category.dao.CategoryDAO
import model.site.app.SiteViewValueApplicantPostShow

import model.site.app.SiteViewValueApplicantPost
import model.component.util.ViewValuePageLayout


// 施設
//~~~~~~~~~~~~~~~~~~~~~
class ApplicantPostController @javax.inject.Inject()(
  // val: immutable, var: mutable
  val employerPostDao: ApplicantPostDAO,
  val employerDao: EmployerDAO,
  val daoLocation: LocationDAO,
  val categoryDao: CategoryDAO,
  cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext


  def show(id: Long) = Action.async { implicit request =>
    for {
      employerPost <- employerPostDao.get(id)
      location <- daoLocation.get(employerPost.get.locationId)
      categorySeqId = Seq(employerPost.get.categoryId1, employerPost.get.categoryId2, employerPost.get.categoryId3)
      categorys <- categoryDao.filterSeqId(categorySeqId)
    } yield {
      val vv = SiteViewValueApplicantPostShow(
        layout = ViewValuePageLayout(id = request.uri),
        post = employerPost.get,
        location = location.get,
        categorys = categorys
      )

      Ok(views.html.site.applicant_post.show.Main(vv))
    }
  }

  def index = Action.async { implicit request =>
    for {
        postSeq <- employerPostDao.findAll
    } yield {
        val vv = SiteViewValueApplicantPostIndex(
            layout = ViewValuePageLayout(id = request.uri),
            posts = postSeq
        )
        Ok(views.html.site.applicant_post.index.Main(vv))
    }
  }

  def add = Action.async { implicit request =>
    for {
      locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
    } yield {
      val vv = SiteViewValueApplicantPost(
        layout   = ViewValuePageLayout(id = request.uri),
        location = locSeq
      )
      Ok(views.html.site.applicant_post.add.Main(vv, formForApplicantPost))
    }
  }

  def create = Action.async { implicit request =>   
    formForApplicantPost.bindFromRequest.fold(
      errors => {
        for {
          locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
        } yield {
          println(errors)
          val vv = SiteViewValueApplicantPost(
            layout   = ViewValuePageLayout(id = request.uri),
            location = locSeq
          )
          BadRequest(views.html.site.applicant_post.add.Main(vv, errors))
        }
      },
      form   => {        
        for {
          locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
          employer <- employerDao.get(1)
          _ <- employerPostDao.create(form)
        } yield {          
          val vv = SiteViewValueApplicantPost(
            layout   = ViewValuePageLayout(id = request.uri),
            location = locSeq
          )
          Ok(views.html.site.applicant_post.add.Main(vv, formForApplicantPost))
        }
      }
    )
  }


  def edit(id: Long) = TODO

  def update(id: Long) = TODO

  def destroy(id: Long) = TODO
}