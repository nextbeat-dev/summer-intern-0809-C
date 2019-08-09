package persistence.applicant.dao

import java.time.LocalDateTime
import java.time.LocalDate
import scala.concurrent.Future

import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

import persistence.applicant.model.Applicant
import persistence.geo.model.Location

// DAO: 施設情報
//~~~~~~~~~~~~~~~~~~
class ApplicantDAO @javax.inject.Inject()(
  val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // --[ リソース定義 ] --------------------------------------------------------
  lazy val slick = TableQuery[ApplicantTable]

  // --[ データ処理定義 ] ------------------------------------------------------
  /**
   * 施設を取得
   */
  def get(id: Applicant.Id): Future[Option[Applicant]] =
    db.run {
      slick
        .filter(_.id === id)
        .result.headOption
    }

  /**
   * 施設を全件取得する
   */
  def findAll: Future[Seq[Applicant]] =
    db.run {
      slick.result
    }

  /**
   * 地域から施設を取得
   * 検索業件: ロケーションID
   */
  def filterByLocationIds(locationIds: Seq[Location.Id]): Future[Seq[Applicant]] =
    db.run {
      slick
        .filter(_.locationId inSet locationIds)
        .result
    }

  // --[ テーブル定義 ] --------------------------------------------------------
  class ApplicantTable(tag: Tag) extends Table[Applicant](tag, "applicant") {


    // Table's columns
    def id            = column[Applicant.Id]    ("id", O.PrimaryKey, O.AutoInc)
    def locationId    = column[Location.Id]    ("location_id")    
    def name         = column[String]         ("name")
    def address   = column[String]         ("address")
    def email   = column[String]         ("email")
    def phone   = column[String]         ("phone")
    def description   = column[String]         ("description")
    def image   = column[String]         ("image")          
    def score   = column[Int]         ("score")  
    def updatedAt     = column[LocalDateTime]  ("updated_at")
    def createdAt     = column[LocalDateTime]  ("created_at")

    // The * projection of the table
    def * = (
      id.?, locationId, name, address, email, phone, description, image, score,
      updatedAt, createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (Applicant.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => Applicant.unapply(v).map(_.copy(
        _10 = LocalDateTime.now
      ))
    )
  }
}
