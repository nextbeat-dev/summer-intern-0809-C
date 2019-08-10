/*
 * This file is part of the Nextbeat services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package persistence.employer.model

import java.time.LocalDateTime

// 募集者ログイン情報
//~~~~~~~~~~~~~
case class EmployerLogin(
  eid:       Option[Employer.Id],
  email:     String, //募集者のID
  password:  String, //パスワード
  updatedAt: LocalDateTime = LocalDateTime.now, // データ更新日
  createdAt: LocalDateTime = LocalDateTime.now // データ作成日
)


