package persistence.category.dao

import java.time.LocalDateTime
import java.time.LocalDate

import persistence.category.model.Category

import scala.concurrent.Future
import slick.jdbc.JdbcProfile
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

import persistence.category.model.Category


class CategoryDAO @javax.inject.Inject()(
  val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  // --[ リソース定義 ] --------------------------------------------------------
  lazy val slick = TableQuery[CategoryTable]

  // --[ データ処理定義 ] ------------------------------------------------------
  /**
   * 施設を取得
   */
  def get(id: Category.Id): Future[Option[Category]] =
    db.run {
      slick
        .filter(_.id === id)
        .result.headOption
    }

    def filterSeqId(seqId: Seq[Category.Id]): Future[Seq[Category]] =
      db.run {
        slick
          .filter(_.id inSet seqId)
          .result
      }

  /**
   * 施設を全件取得する
   */
  def findAll: Future[Seq[Category]] =
    db.run {
      slick.result
    }

// --[ テーブル定義 ] --------------------------------------------------------
  class CategoryTable(tag: Tag) extends Table[Category](tag, "applicant_post") {


    // Table's columns
    def id            = column[Category.Id]    ("id", O.PrimaryKey, O.AutoInc)
    def name         = column[String]         ("name")
    def description   = column[String]         ("description")
    def updatedAt     = column[LocalDateTime]  ("updated_at")
    def createdAt     = column[LocalDateTime]  ("created_at")

    // The * projection of the table
    def * = (
      id.?, name, description,
      updatedAt, createdAt
    ) <> (
      /** The bidirectional mappings : Tuple(table) => Model */
      (Category.apply _).tupled,
      /** The bidirectional mappings : Model => Tuple(table) */
      (v: TableElementType) => Category.unapply(v).map(_.copy(
        _4 = LocalDateTime.now
      ))
    )
  }
}
