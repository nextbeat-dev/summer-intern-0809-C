package persistence.applicant.dao

import java.time.LocalDateTime

import persistence.applicant.model.{Applicant, ApplicantLogin}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

// DAO: 施設情報
//~~~~~~~~~~~~~~~~~~
class ApplicantLoginDAO @javax.inject.Inject()(
  val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // --[ リソース定義 ] --------------------------------------------------------
  lazy val slick = TableQuery[ApplicantLoginTable]

  // --[ データ処理定義 ] ------------------------------------------------------

  /**
    * 認証
    */
  def auth(data: ApplicantLogin): Future[Option[ApplicantLogin]] =
    db.run {
      slick
        .filter(_.email === data.email)
        .filter(_.password === data.password)
        .result.headOption
    }

  /**
    * ログイン情報を追加する
    */
  def update(data: ApplicantLogin): Future[Unit] =
    db.run {
      val row = slick.filter(_.aid === data.aid)
      for {
        old <- row.result.headOption
        _   <- old match {
          case None    => slick += data
          case Some(_) => row.update(data)
        }
      } yield ()
    }

  /**
   * 施設を取得
   */
  def findByAid(aid: Applicant.Id): Future[Option[ApplicantLogin]] =
    db.run {
      slick
        .filter(_.aid === aid)
        .result.headOption
    }


  // --[ テーブル定義 ] --------------------------------------------------------
  class ApplicantLoginTable(tag: Tag) extends Table[ApplicantLogin](tag, "applicant_login") {


    // Table's columns
    def aid       = column[Applicant.Id]  ("aid", O.PrimaryKey)
    def email     = column[String]        ("email")
    def password  = column[String]        ("password")
    def updatedAt = column[LocalDateTime] ("updated_at")
    def createdAt = column[LocalDateTime] ("created_at")

    def ukey01 = index("ukey01", aid, unique = true)

    // The * projection of the table
    def * = (
      aid.?, email, password, updatedAt, createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (ApplicantLogin.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => ApplicantLogin.unapply(v).map(_.copy(
        _5 = LocalDateTime.now
      ))
    )
  }
}
