package persistence.employer.dao

import java.time.LocalDateTime

import scala.concurrent.Future
import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import persistence.employer.model.Employer
import persistence.employer.model.Employer.Id
import persistence.employer.model.EmployerLogin.Id
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
    * ユーザ情報を追加する
    */
  def add(data: Employer): Future[Employer.Id] =
    db.run {
      data.id match {
        case None => slick returning slick.map (_.id) += data
        case Some (_) => DBIO.failed (
          new IllegalArgumentException ("The given object is already assigned id.")
        )
      }
    }

  /**
   * 応募者情報
   */
  def get(id: Employer.Id): Future[Option[Employer]] =
    db.run {
      slick
        .filter(_.id === id)
        .result.headOption
    }

  // --[ テーブル定義 ] --------------------------------------------------------
  class EmployerTable(tag: Tag) extends Table[Employer](tag, "employer") {

    // Table's columns
    def id          = column[Employer.Id]   ("id", O.PrimaryKey, O.AutoInc)
    def locationId  = column[Location.Id]   ("location_id")
    def name        = column[String]        ("name")
    def address     = column[String]        ("address")
    def email       = column[String]        ("email")
    def phone       = column[String]        ("phone")
    def description = column[String]        ("description")
    def image       = column[String]        ("image")
    def score       = column[Int]           ("score")
    def updatedAt   = column[LocalDateTime] ("updated_at")
    def createdAt   = column[LocalDateTime] ("created_at")

    // Indexes
    def ukey01 = index("ukey01", email, unique = true)

    type TableElementTuple = (
      Id, Location.Id, String, String, String, String, String, String, Int, LocalDateTime, LocalDateTime
    )

    // The * projection of the table
    def * = (
      id, locationId, name, address, email, phone, description, image, score,
      updatedAt, createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (t: TableElementTuple) =>
        Employer(
          Some(t._1), t._2, t._3, t._4, t._5, "", t._6, t._7, t._8, t._9, t._10, t._11
        ),
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) =>
        Some((
          v.id.getOrElse(0L), v.locationId, v.name, v.address, v.email, v.phone,
          v.description, v.image, v.score, LocalDateTime.now(), v.createdAt
        ))
    )
  }
}
