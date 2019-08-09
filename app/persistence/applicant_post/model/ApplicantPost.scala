/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package persistence.applicant_post.model

import play.api.data._
import play.api.data.Forms._
import java.time.LocalDateTime
import java.time.LocalDate

import persistence.geo.model.Location
import persistence.applicant.model.Applicant
import persistence.category.model.Category


// 施設情報 (sample)
//~~~~~~~~~~~~~
case class ApplicantPost(
  id:          Option[ApplicantPost.Id],                // 施設ID
  applicantId: Applicant.Id,
  locationId:  Location.Id,                        // 地域ID
  title:       String,                             // 施設名
  destination: String,                             // 住所(詳細)
  description: String,                             // 施設説明    
  categoryId1: Category.Id,
  categoryId2: Category.Id,
  categoryId3: Category.Id,
  done: Boolean,
  free_date: LocalDate,
  updatedAt:   LocalDateTime = LocalDateTime.now,  // データ更新日
  createdAt:   LocalDateTime = LocalDateTime.now   // データ作成日
)

// 施設検索
// case class ApplicantPostSearch(
//   locationIdOpt: Option[Location.Id]
// )

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object ApplicantPost {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long

  // --[ フォーム定義 ]---------------------------------------------------------
  // val formForApplicantPostSearch = Form(
  //   mapping(
  //     "locationId" -> optional(text),
  //   )(ApplicantPostSearch.apply)(ApplicantPostSearch.unapply)
  // )
}

