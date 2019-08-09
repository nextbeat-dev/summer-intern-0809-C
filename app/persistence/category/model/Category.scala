package persistence.applicant_post.model

import play.api.data._
import play.api.data.Forms._
import java.time.LocalDateTime
import java.time.LocalDate


// 施設情報 (sample)
//~~~~~~~~~~~~~
case class Category(
  id:          Option[Category.Id],                // 施設ID
  name:       String,                             // 施設名
  description: String,                             // 施設説明
  updatedAt:   LocalDateTime = LocalDateTime.now,  // データ更新日
  createdAt:   LocalDateTime = LocalDateTime.now   // データ作成日
)

// 施設検索
// case class CategorySearch(
//   locationIdOpt: Option[Location.Id]
// )

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object Category {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long

  // --[ フォーム定義 ]---------------------------------------------------------
  // val formForCategorySearch = Form(
  //   mapping(
  //     "locationId" -> optional(text),
  //   )(CategorySearch.apply)(CategorySearch.unapply)
  // )
}

