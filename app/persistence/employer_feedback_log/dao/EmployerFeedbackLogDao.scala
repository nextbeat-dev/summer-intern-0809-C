package persistence.applicant_feedback_log.dao

import java.time.LocalDateTime
import java.time.LocalDate
import scala.concurrent.Future

import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

import persistence.employer_feedback_log.model.EmployerFeedbackLog
import persistence.geo.model.Location
import persistence.applicant.model.Applicant
import persistence.employer.model.Employer


// DAO: 施設情報
//~~~~~~~~~~~~~~~~~~
class EmployerFeedbackLogDAO @javax.inject.Inject()(
  val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // --[ リソース定義 ] --------------------------------------------------------
  lazy val slick = TableQuery[EmployerFeedbackLogTable]

  // --[ データ処理定義 ] ------------------------------------------------------
  /**
   * 施設を取得
   */
  def get(id: EmployerFeedbackLog.Id): Future[Option[EmployerFeedbackLog]] =
    db.run {
      slick
        .filter(_.id === id)
        .result.headOption
    }

  /**
   * 施設を全件取得する
   */
  def findAll: Future[Seq[EmployerFeedbackLog]] =
    db.run {
      slick.result
    }

  /**
   * 地域から施設を取得
   * 検索業件: ロケーションID
   */
  def filterByLocationIds(locationIds: Seq[Location.Id]): Future[Seq[EmployerFeedbackLog]] =
    db.run {
      slick
        .filter(_.locationId inSet locationIds)
        .result
    }

  // --[ テーブル定義 ] --------------------------------------------------------
  class EmployerFeedbackLogTable(tag: Tag) extends Table[EmployerFeedbackLog](tag, "applicant_feedback_log") {


    // Table's columns
    def id            = column[EmployerFeedbackLog.Id]    ("id", O.PrimaryKey, O.AutoInc)
    def employer_id  = column[Employer.Id] ("employer_id")
    def applicant_id  = column[Applicant.Id] ("applicant_id")    
    def score         = column[Int] ("score")
    def updatedAt     = column[LocalDateTime]  ("updated_at")
    def createdAt     = column[LocalDateTime]  ("created_at")

    // The * projection of the table
    def * = (
      id.?, employer_id, applicant_id, score,
      updatedAt, createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (EmployerFeedbackLog.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => EmployerFeedbackLog.unapply(v).map(_.copy(
        _5 = LocalDateTime.now
      ))
    )
  }
}
