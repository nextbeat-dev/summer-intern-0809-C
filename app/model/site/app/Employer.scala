/*
 * This file is part of the MARIAGE services.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.site.app

import model.component.util.ViewValuePageLayout
import persistence.geo.model.Location

// 応募者
//~~~~~~~~~~~~~~~~~~~~~
case class SiteViewValueEmployer(
  layout:   ViewValuePageLayout
)

case class SiteViewValueNewEmployer(
  layout:   ViewValuePageLayout,
  location: Seq[Location]
)

case class SiteViewValueEmployerLogin(
  layout:   ViewValuePageLayout
)