/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package persistence.employer.model

import play.api.data._
import play.api.data.Forms._
import java.time.LocalDateTime
import java.time.LocalDate

import persistence.geo.model.Location


// 施設情報 (sample)
//~~~~~~~~~~~~~
case class Employer(
  id:          Option[Employer.Id],                // 施設ID
  locationId:  Location.Id,                        // 地域ID
  name:       String,                             // 施設名
  address: String,                             // 住所(詳細)  
  email: String,                             // 住所(詳細)  
  phone: String,                             // 住所(詳細)  
  description: String,                             // 施設説明
  image: String,                             // 施設説明
  score: Int,                             // 施設説明
  updatedAt:   LocalDateTime = LocalDateTime.now,  // データ更新日
  createdAt:   LocalDateTime = LocalDateTime.now   // データ作成日
)

// 施設検索
// case class EmployerSearch(
//   locationIdOpt: Option[Location.Id]
// )

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object Employer {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long

  // --[ フォーム定義 ]---------------------------------------------------------
  // val formForEmployerSearch = Form(
  //   mapping(
  //     "locationId" -> optional(text),
  //   )(EmployerSearch.apply)(EmployerSearch.unapply)
  // )
}

