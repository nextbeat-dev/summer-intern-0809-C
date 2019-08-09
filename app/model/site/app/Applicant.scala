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
case class SiteViewValueApplicant(
  layout:   ViewValuePageLayout
)

case class SiteViewValueNewApplicant(
  layout:   ViewValuePageLayout,
  location: Seq[Location]
)
