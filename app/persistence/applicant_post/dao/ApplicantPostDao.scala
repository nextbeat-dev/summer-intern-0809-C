package persistence.applicant_post.dao

import java.time.LocalDateTime
import scala.concurrent.Future

import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import persistence.applicant_post.model.ApplicantPost
import persistence.geo.model.Location

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
    /* @1 */ def id            = column[ApplicantPost.Id]    ("id", O.PrimaryKey, O.AutoInc)
    /* @2 */ def locationId    = column[Location.Id]    ("location_id")
    /* @3 */ def name          = column[String]         ("name")
    /* @4 */ def address       = column[String]         ("address")
    /* @5 */ def description   = column[String]         ("description")
    /* @6 */ def updatedAt     = column[LocalDateTime]  ("updated_at")
    /* @7 */ def createdAt     = column[LocalDateTime]  ("created_at")

    // The * projection of the table
    def * = (
      id.?, locationId, name, address, description,
      updatedAt, createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (ApplicantPost.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => ApplicantPost.unapply(v).map(_.copy(
        _6 = LocalDateTime.now
      ))
    )
  }
}
