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
import persistence.geo.model.Location

// 応募者
//~~~~~~~~~~~~~
case class Employer(
  id:          Option[Employer.Id],               // 施設ID
  locationId:  Location.Id,                        // 地域ID
  name:        String,                             // 施設名
  address:     String,                             // 住所(詳細)
  email:       String,                             // メールアドレス
  password:    String,                             // パスワード
  phone:       String,                             // 電話番号
  description: String,                             // 施設説明
  image:       String,                             // 施設説明
  score:       Int,                                // レビュー平均点
  updatedAt:   LocalDateTime = LocalDateTime.now,  // データ更新日
  createdAt:   LocalDateTime = LocalDateTime.now   // データ作成日
)

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object Employer {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long

  def applyForm(
    locationId: Location.Id,
    name: String,
    address: String,
    email: String,
    password: String,
    phone: String,
    description: String,
    image: String,
  ): Employer = Employer(None, locationId, name, address, email, password, phone, description, image, 0)

  // --[ フォーム定義 ]---------------------------------------------------------
  val formForNewEmployer = Form(
    mapping(
      "locationId"  -> nonEmptyText,
      "name"        -> nonEmptyText,
      "address"     -> nonEmptyText,
      "email"     -> nonEmptyText,
      "password"  -> nonEmptyText,
      "phone"     -> nonEmptyText,
      "description"     -> nonEmptyText,
      "image"     -> nonEmptyText,
    )(Employer.applyForm)(Employer.unapply(_).map(
      t => (t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9)
    ))
  )

}

