package persistence.employer_post.dao

import java.time.{LocalDate, LocalDateTime, ZoneId}
import java.util.Date

import scala.concurrent.Future
import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import persistence.employer_post.model.EmployerPost
import persistence.geo.model.Location
import persistence.employer.model.Employer
import persistence.category.model.Category

// DAO: 施設情報
//~~~~~~~~~~~~~~~~~~
class EmployerPostDAO @javax.inject.Inject()(
  val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // --[ リソース定義 ] --------------------------------------------------------
  lazy val slick = TableQuery[EmployerPostTable]

  // --[ データ処理定義 ] ------------------------------------------------------
  /**
   * 施設を取得
   */
  def get(id: EmployerPost.Id): Future[Option[EmployerPost]] = 
   db.run {
    slick
        .filter(_.id === id)
        .result.headOption
    }

  /**
   * 施設を全件取得する
   */
  def findAll: Future[Seq[EmployerPost]] =
    db.run {
      slick.result
    }
  
  def create(data: EmployerPost): Future[EmployerPost.Id] =
    db.run {
      data.id match {
        case None    => slick returning slick.map(_.id) += data
        case Some(_) => DBIO.failed(
          new IllegalArgumentException("The given object is already assigned id.")
        )
      }
    }
  
  def update(id: Long, title: String, address: String, description: String, price: Int):  Unit  = 
    db.run {
      slick
        .filter(_.id === id)
        .map(
          p => (p.title, p.address, p.description, p.price)        
        )
        .update((
          title, address, description, price
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
  def filterByLocationIds(locationIds: Seq[Location.Id]): Future[Seq[EmployerPost]] =
    db.run {
      slick
        .filter(_.locationId inSet locationIds)
        .result
    }

  // --[ テーブル定義 ] --------------------------------------------------------
  class EmployerPostTable(tag: Tag) extends Table[EmployerPost](tag, "employer_post") {


    // Table's columns
    def id              = column[EmployerPost.Id] ("id", O.PrimaryKey, O.AutoInc)
    def employerId      = column[Employer.Id]     ("employer_id")
    def locationId      = column[Location.Id]     ("location_id")
    def title           = column[String]          ("title")
    def address         = column[String]          ("address")
    def description     = column[String]          ("description")
    def main_image      = column[String]          ("main_image")
    def thumbnail_image = column[String]          ("thumbnail_image")
    def price           = column[Int]             ("price")
    def category_id_1   = column[Category.Id]     ("category_id_1")
    def category_id_2   = column[Category.Id]     ("category_id_2")
    def category_id_3   = column[Category.Id]     ("category_id_3")
    def done            = column[Boolean]         ("done")
    def job_date        = column[LocalDate]       ("job_date")
    def updatedAt       = column[LocalDateTime]   ("updated_at")
    def createdAt       = column[LocalDateTime]   ("created_at")

    def date2LocalDate(date: Date): LocalDate = {
      date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    } 

    def localDate2Date(localDate: LocalDate): Date = {
      Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

  type TableElementTuple = (
    EmployerPost.Id, Employer.Id, Location.Id, String, String, String, String, String, Int,
    Category.Id, Category.Id, Category.Id, Boolean, LocalDate, LocalDateTime, LocalDateTime
  )
    // The * projection of the table
    def * = (
      id.?, employerId, locationId, title, address, description, main_image, thumbnail_image, price,
      category_id_1, category_id_2, category_id_3, done, job_date,
      updatedAt, createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (EmployerPost.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => EmployerPost.unapply(v).map(_.copy(
        _15 = LocalDateTime.now
      ))
    )
  }
}
