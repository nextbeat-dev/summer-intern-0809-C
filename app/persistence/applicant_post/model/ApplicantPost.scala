/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package persistence.applicant_post.model

import play.api.data._
import play.api.data.Forms._
import java.time.{LocalDate, LocalDateTime, ZoneId}
import java.util.Date

import persistence.geo.model.Location
import persistence.employer.model.Employer
import persistence.category.model.Category


// 施設情報 (sample)
//~~~~~~~~~~~~~
case class ApplicantPost(
  id:          Option[ApplicantPost.Id],                // 施設ID
  employerId: Employer.Id,
  locationId:  Location.Id,                        // 地域ID
  title:       String,                             // 施設名
  address: String,                             // 住所(詳細)
  description: String,                             // 施設説明
  main_image: String,
  thumbnail_image: String,
  price: Int,
  categoryId1: Category.Id,
  categoryId2: Category.Id,
  categoryId3: Category.Id,
  done: Boolean,
  job_date: LocalDate,
  updatedAt:   LocalDateTime = LocalDateTime.now,  // データ更新日
  createdAt:   LocalDateTime = LocalDateTime.now   // データ作成日
)
// コンパニオンオブジェクト
//~~~~~~~~~~~~~~~~~~~~~~~~~~
object ApplicantPost {

  // --[ 管理ID ]---------------------------------------------------------------
  type Id = Long

  def date2LocalDate(date: Date): LocalDate =
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

  def localDate2Date(localDate: LocalDate): Date =
    return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())


  def applyForm(
    employerId: Employer.Id,
    locationId: Location.Id,
    title: String,
    address: String,
    description: String,
    main_image:  String,
    thumbnail_image : String,
    price      : Int,
    categoryId1: Category.Id,
    categoryId2: Category.Id,
    categoryId3: Category.Id,
    job_date: Date
  ) = ApplicantPost(
    None, employerId, locationId, title, address, description, main_image, thumbnail_image, price,
    categoryId1, categoryId2, categoryId3, false, date2LocalDate(job_date)
  )

  val formForApplicantPost = Form(
    mapping(
      "employerId" -> longNumber,
      "locationId" -> nonEmptyText,
      "title"      -> nonEmptyText,
      "address"    -> nonEmptyText,
      "description"-> text,
      "main_image" -> text,
      "thumbnail_image"-> text,
      "price"      -> number(min = 0),
      "categoryId1"-> longNumber,
      "categoryId2"-> longNumber,
      "categoryId3"-> longNumber,
      "job_date"   -> date
    )(ApplicantPost.applyForm)(ApplicantPost.unapply(_).map(
      t => (t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, localDate2Date(t._14))
    ))
  )
}
