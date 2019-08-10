/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package persistence.applicant.model

import java.time.LocalDateTime

import persistence.geo.model.Location
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.Forms.mapping

// 施設情報 (sample)
//~~~~~~~~~~~~~
case class ApplicantLogin(
  aid:       Option[Applicant.Id],
  email:     String, //応募者のID
  password:  String, //パスワード
  updatedAt: LocalDateTime = LocalDateTime.now, // データ更新日
  createdAt: LocalDateTime = LocalDateTime.now // データ作成日
)

object ApplicantLogin {
  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long

  def applyForLogin(
    email: String,
    password: String
  ): ApplicantLogin = ApplicantLogin(None, email, password)

  // --[ フォーム定義 ]---------------------------------------------------------
  val formForApplicantLogin = Form(
    mapping(
      "email"    -> nonEmptyText,
      "password" -> nonEmptyText,
    )(ApplicantLogin.applyForLogin)(ApplicantLogin.unapply(_).map(
      t => (t._2, "")
    ))
  )

}