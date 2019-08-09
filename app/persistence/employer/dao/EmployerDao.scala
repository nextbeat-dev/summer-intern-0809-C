package persistence.employer.dao

import java.time.LocalDateTime
import java.time.LocalDate
import scala.concurrent.Future

import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

import persistence.employer.model.Employer
import persistence.geo.model.Location

// DAO: 施設情報
//~~~~~~~~~~~~~~~~~~
class EmployerDAO @javax.inject.Inject()(
  val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // --[ リソース定義 ] --------------------------------------------------------
  lazy val slick = TableQuery[EmployerTable]

  // --[ データ処理定義 ] ------------------------------------------------------
  /**
   * 施設を取得
   */
  def get(id: Employer.Id): Future[Option[Employer]] =
    db.run {
      slick
        .filter(_.id === id)
        .result.headOption
    }

  /**
   * 施設を全件取得する
   */
  def findAll: Future[Seq[Employer]] =
    db.run {
      slick.result
    }

  /**
   * 地域から施設を取得
   * 検索業件: ロケーションID
   */
  def filterByLocationIds(locationIds: Seq[Location.Id]): Future[Seq[Employer]] =
    db.run {
      slick
        .filter(_.locationId inSet locationIds)
        .result
    }

  // --[ テーブル定義 ] --------------------------------------------------------
  class EmployerTable(tag: Tag) extends Table[Employer](tag, "employer") {


    // Table's columns
    def id            = column[Employer.Id]    ("id", O.PrimaryKey, O.AutoInc)
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
      (Employer.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => Employer.unapply(v).map(_.copy(
        _10 = LocalDateTime.now
      ))
    )
  }
}
