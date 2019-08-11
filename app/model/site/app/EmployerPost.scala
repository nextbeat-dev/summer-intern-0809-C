package model.site.app

import model.component.util.ViewValuePageLayout
import persistence.category.model.Category
import persistence.employer.model.Employer
import persistence.employer_post.model.EmployerPost
import persistence.employer.model.Employer
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
  employer: Option[Employer],
  categorys: Seq[Category]
)

case class SiteViewValueEmployerPost(
  layout:   ViewValuePageLayout,
  location: Seq[Location],
  eid:      Employer.Id
)