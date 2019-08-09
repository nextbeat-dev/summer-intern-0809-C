/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package persistence.applicant_feedback_log.model

import play.api.data._
import play.api.data.Forms._
import java.time.LocalDateTime
import java.time.LocalDate

import persistence.geo.model.Location
import persistence.applicant.model.Applicant
import persistence.employer.model.Employer


// 施設情報 (sample)
//~~~~~~~~~~~~~
case class ApplicantFeedbackLog(
  id:          Option[ApplicantFeedbackLog.Id],                // 施設ID
  employerId: Employer.Id,
  applicantId: Applicant.Id,
  score     : Int,  
  updatedAt:   LocalDateTime = LocalDateTime.now,  // データ更新日
  createdAt:   LocalDateTime = LocalDateTime.now   // データ作成日
)

// 施設検索
// case class ApplicantFeedbackLogSearch(
//   locationIdOpt: Option[Location.Id]
// )

// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object ApplicantFeedbackLog {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long

  // --[ フォーム定義 ]---------------------------------------------------------
  // val formForApplicantFeedbackLogSearch = Form(
  //   mapping(
  //     "locationId" -> optional(text),
  //   )(ApplicantFeedbackLogSearch.apply)(ApplicantFeedbackLogSearch.unapply)
  // )
}

