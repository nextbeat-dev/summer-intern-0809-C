/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package persistence.applicant.model

import java.time.LocalDateTime

// 施設情報 (sample)
//~~~~~~~~~~~~~
case class LoginApplicant(
  id:        Option[LoginApplicant.Id],          //ID
  aid:       Applicant.Id,                       //応募者のID
  password:  String,                             //パスワード
  updatedAt: LocalDateTime = LocalDateTime.now,  // データ更新日
  createdAt: LocalDateTime = LocalDateTime.now   // データ作成日
)

object LoginApplicant {
  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long
}