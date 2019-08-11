package persistence.applicant_post.dao

import java.time.{LocalDate, LocalDateTime, ZoneId}
import java.util.Date

import scala.concurrent.Future
import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import persistence.applicant_post.model.ApplicantPost
import persistence.geo.model.Location
import persistence.applicant.model.Applicant
import persistence.category.model.Category

// DAO: 施設情報
//~~~~~~~~~~~~~~~~~~
class ApplicantPostDAO @javax.inject.Inject()(
  val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // --[ リソース定義 ] --------------------------------------------------------
  lazy val slick = TableQuery[ApplicantPostTable]

  // --[ データ処理定義 ] ------------------------------------------------------
  /**
   * 施設を取得
   */
  def get(id: ApplicantPost.Id): Future[Option[ApplicantPost]] = 
   db.run {
    slick
        .filter(_.id === id)
        .result.headOption
    }

  /**
   * 施設を全件取得する
   */
  def findAll: Future[Seq[ApplicantPost]] =
    db.run {
      slick.result
    }
  
  def create(data: ApplicantPost): Future[ApplicantPost.Id] =
    db.run {
      data.id match {
        case None    => slick returning slick.map(_.id) += data
        case Some(_) => DBIO.failed(
          new IllegalArgumentException("The given object is already assigned id.")
        )
      }
    }
  
  def update(id: Long, title: String, destination: String, description: String):  Unit  = 
    db.run {
      slick
        .filter(_.id === id)
        .map(
          p => (p.title, p.destination, p.description)        
        )
        .update((
          title, destination, description
        ))
    }    

  def delete(id: Long): Unit = 
    db.run {
      slick
        .filter(_.id === id)
        .delete
    }

  /**
   * 地域から施設を取得
   * 検索業件: ロケーションID
   */
  def filterByLocationIds(locationIds: Seq[Location.Id]): Future[Seq[ApplicantPost]] =
    db.run {
      slick
        .filter(_.locationId inSet locationIds)
        .result
    }

  // --[ テーブル定義 ] --------------------------------------------------------
  class ApplicantPostTable(tag: Tag) extends Table[ApplicantPost](tag, "applicant_post") {


    // Table's columns
    def id            = column[ApplicantPost.Id]    ("id", O.PrimaryKey, O.AutoInc)
    def applicant_id  = column[Applicant.Id]        ("applicant_id")
    def locationId    = column[Location.Id]         ("location_id")
    def title         = column[String]              ("title")
    def destination   = column[String]              ("destination")
    def description   = column[String]              ("description")
    def category_id_1 = column[Option[Category.Id]] ("category_id_1")
    def category_id_2 = column[Option[Category.Id]] ("category_id_2")
    def category_id_3 = column[Option[Category.Id]] ("category_id_3")
    def done          = column[Boolean]             ("done")
    def free_date     = column[LocalDate]           ("free_date")
    def updatedAt     = column[LocalDateTime]       ("updated_at")
    def createdAt     = column[LocalDateTime]       ("created_at")

    def date2LocalDate(date: Date): LocalDate = {
      date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    } 

    def localDate2Date(localDate: LocalDate): Date = {
      Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

  type TableElementTuple = (
    ApplicantPost.Id, Applicant.Id, Location.Id, String, String, String,
    Category.Id, Category.Id, Category.Id, Boolean, LocalDate, LocalDateTime, LocalDateTime
  )
    // The * projection of the table
    def * = (
      id.?, applicant_id, locationId, title, destination, description,
      category_id_1, category_id_2, category_id_3, done, free_date,
      updatedAt, createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (ApplicantPost.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => ApplicantPost.unapply(v).map(_.copy(
        _12 = LocalDateTime.now
      ))
    )
  }
}
