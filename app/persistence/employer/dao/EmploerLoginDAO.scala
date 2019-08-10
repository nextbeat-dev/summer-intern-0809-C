package persistence.employer.dao

import java.time.LocalDateTime

import persistence.employer.model.{Employer, EmployerLogin}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

// DAO: 施設情報
//~~~~~~~~~~~~~~~~~~
class EmployerLoginDAO @javax.inject.Inject()(
  val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // --[ リソース定義 ] --------------------------------------------------------
  lazy val slick = TableQuery[EmployerLoginTable]

  // --[ データ処理定義 ] ------------------------------------------------------

  /**
    * 認証
    */
  def auth(data: EmployerLogin): Future[Option[EmployerLogin]] =
    db.run {
      slick
        .filter(_.email === data.email)
        .filter(_.password === data.password)
        .result.headOption
    }

  /**
    * ログイン情報を追加する
    */
  def update(data: EmployerLogin): Future[Unit] =
    db.run {
      val row = slick.filter(_.eid === data.eid)
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
  def findByEid(eid: Employer.Id): Future[Option[EmployerLogin]] =
    db.run {
      slick
        .filter(_.eid === eid)
        .result.headOption
    }


  // --[ テーブル定義 ] --------------------------------------------------------
  class EmployerLoginTable(tag: Tag) extends Table[EmployerLogin](tag, "employer_login") {


    // Table's columns
    def eid       = column[Employer.Id]  ("eid", O.PrimaryKey)
    def email     = column[String]        ("email")
    def password  = column[String]        ("password")
    def updatedAt = column[LocalDateTime] ("updated_at")
    def createdAt = column[LocalDateTime] ("created_at")

    def ukey01 = index("employer_login_ukey01", eid, unique = true)

    // The * projection of the table
    def * = (
      eid.?, email, password, updatedAt, createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (EmployerLogin.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => EmployerLogin.unapply(v).map(_.copy(
        _5 = LocalDateTime.now
      ))
    )
  }
}
