package persistence.applicant.dao

import java.time.LocalDateTime

import persistence.applicant.model.{Applicant, LoginApplicant}
import persistence.udb.model.User
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

// DAO: 施設情報
//~~~~~~~~~~~~~~~~~~
class LoginApplicantDAO @javax.inject.Inject()(
  val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // --[ リソース定義 ] --------------------------------------------------------
  lazy val slick = TableQuery[LoginApplicantTable]

  // --[ データ処理定義 ] ------------------------------------------------------

  /**
    * ユーザ情報を追加する
    */
  def add(data: LoginApplicant): Future[LoginApplicant.Id] = {
    println(data)
    db.run {
      data.id match {
        case None => slick returning slick.map(_.id) += data
        case Some(_) => DBIO.failed(
          new IllegalArgumentException("The given object is already assigned id.")
        )
      }
    }
  }

  /**
   * 施設を取得
   */
  def findByAid(aid: Applicant.Id): Future[Option[LoginApplicant]] =
    db.run {
      slick
        .filter(_.aid === aid)
        .result.headOption
    }


  // --[ テーブル定義 ] --------------------------------------------------------
  class LoginApplicantTable(tag: Tag) extends Table[LoginApplicant](tag, "login_applicant") {


    // Table's columns
    def id        = column[LoginApplicant.Id]("id", O.PrimaryKey, O.AutoInc)
    def aid       = column[Applicant.Id]    ("aid", O.AutoInc)
    def password  = column[String]         ("password")
    def updatedAt = column[LocalDateTime]  ("updated_at")
    def createdAt = column[LocalDateTime]  ("created_at")

    def ukey01 = index("ukey01", aid, unique = true)

    // The * projection of the table
    def * = (
      id.?, aid, password, updatedAt, createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (LoginApplicant.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => LoginApplicant.unapply(v).map(_.copy(
        _5 = LocalDateTime.now
      ))
    )
  }
}
