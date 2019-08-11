package controllers.employer_post

import mvc.action.AuthenticationAction
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, MessagesControllerComponents}

import scala.concurrent.Future
// persistence: 永続化
import persistence.employer_post.model.EmployerPost.formForEmployerPost
import persistence.employer_post.dao.EmployerPostDAO

import persistence.employer.dao.EmployerDAO

import persistence.geo.model.Location
import persistence.geo.dao.LocationDAO
import model.site.app.SiteViewValueEmployerPostIndex

import persistence.category.dao.CategoryDAO
import model.site.app.SiteViewValueEmployerPostShow

import model.site.app.SiteViewValueEmployerPost
import model.component.util.ViewValuePageLayout


// 施設
//~~~~~~~~~~~~~~~~~~~~~
class EmployerPostController @javax.inject.Inject()(
  // val: immutable, var: mutable
  val employerPostDao: EmployerPostDAO,
  val employerDao: EmployerDAO,
  val daoLocation: LocationDAO,
  val categoryDao: CategoryDAO,
  cc: MessagesControllerComponents
) extends AbstractController(cc) with I18nSupport {
  implicit lazy val executionContext = defaultExecutionContext

  val userTypeApplicant = 0
  val userTypeEmployer  = 1

  def show(id: Long) = Action.async { implicit request =>
    for {
      employerPost <- employerPostDao.get(id)
      location <- daoLocation.get(employerPost.get.locationId)
      categorySeqId = Seq(employerPost.get.categoryId1, employerPost.get.categoryId2, employerPost.get.categoryId3)
      categorys <- categoryDao.filterSeqId(categorySeqId.flatten)
    } yield {
      val vv = SiteViewValueEmployerPostShow(
        layout = ViewValuePageLayout(id = request.uri),
        post = employerPost.get,
        location = location.get,
        categorys = categorys
      )

      Ok(views.html.site.employer_post.show.Main(vv))
    }
  }

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

  def add = (Action andThen AuthenticationAction(userTypeEmployer)).async { implicit request =>
    val eid = request.session.get("eid")
    eid match {
      case Some(x) => {
        for {
          locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
        } yield {
          val vv = SiteViewValueEmployerPost(
            layout   = ViewValuePageLayout(id = request.uri),
            location = locSeq,
            eid      = x.toLong
          )
          Ok(views.html.site.employer_post.add.Main(vv, formForEmployerPost))
        }
      }
      case None => {
        Future(Redirect("/", 301))
      }
    }


  }

  def create = (Action andThen AuthenticationAction(userTypeEmployer)).async { implicit request =>
    val eid = request.session.get("eid")
    eid match {
      case Some(x) =>{
        formForEmployerPost.bindFromRequest.fold(
          errors => {
            for {
              locSeq <- daoLocation.filterByIds(Location.Region.IS_PREF_ALL)
            } yield {
              val vv = SiteViewValueEmployerPost(
                layout   = ViewValuePageLayout(id = request.uri),
                location = locSeq,
                eid = x.toLong
              )
              BadRequest(views.html.site.employer_post.add.Main(vv, errors))
            }
          },
          form   => {
            for {
              _ <- employerPostDao.create(form)
            } yield {
              Redirect("/employer_post")
            }
          }
        )
      }
      case None    =>{
        Future(Redirect("/", 301))
      }
    }

  }


  def edit(id: Long) = TODO

  def update(id: Long) = TODO

  def destroy(id: Long) = TODO
}