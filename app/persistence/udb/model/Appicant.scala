/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package persistence.udb.model

import play.api.data._
import play.api.data.Forms._
import java.time.LocalDateTime
import persistence.geo.model.Location

// ユーザ情報
//~~~~~~~~~~~~~
case class Applicant(
  id:          Option[Applicant.Id],             // ユーザID
  locationID:  Location.Id,                      // 都道府県ID
  name:        String,                           // 名前
  address:     String,                           // 住所
  email:       String,                           // メールアドレス
  phone:       String,                           // 電話番号
  description: String,                           // 説明文
  image:       String,                           // サムネイル画像
  score:       Int,                              // 評価点
  updatedAt: LocalDateTime = LocalDateTime.now,  // データ更新日
  createdAt: LocalDateTime = LocalDateTime.now   // データ作成日
)

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object Applicant {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long

  // --[ フォーム定義 ]---------------------------------------------------------
  val formForNewApplicant = Form(
    mapping(
      "nameLast"  -> nonEmptyText,
      "nameFirst" -> nonEmptyText,
      "email"     -> email,
      "pref"      -> nonEmptyText,
      "address"   -> nonEmptyText,
    )(Function.untupled(
      t => Applicant(None, t._1, t._2, t._3, t._4, t._5)
    ))(Applicant.unapply(_).map(
      t => (t._2, t._3, t._4, t._5, t._6)
    ))
  )
}

