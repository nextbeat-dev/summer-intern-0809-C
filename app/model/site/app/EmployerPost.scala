package model.site.app

import model.component.util.ViewValuePageLayout
import persistence.category.model.Category
import persistence.employer_post.model.EmployerPost
import persistence.geo.model.Location


// 応募者
//~~~~~~~~~~~~~~~~~~~~~
case class SiteViewValueEmployerPostIndex(
  layout:   ViewValuePageLayout,
  posts: Seq[EmployerPost]
)

case class SiteViewValueEmployerPostShow(
  layout:   ViewValuePageLayout,
  post: EmployerPost,
  location: Location,
  categorys: Seq[Category]
)