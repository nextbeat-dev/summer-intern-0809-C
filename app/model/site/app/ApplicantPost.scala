package model.site.app

import model.component.util.ViewValuePageLayout
import persistence.category.model.Category
import persistence.applicant_post.model.{ApplicantItem, ApplicantPost}
import persistence.applicant.model.Applicant
import persistence.geo.model.Location

// 応募者
//~~~~~~~~~~~~~~~~~~~~~
case class SiteViewValueApplicantPostIndex(
  layout:   ViewValuePageLayout,
  posts: Seq[ApplicantItem]
)

case class SiteViewValueApplicantPostShow(
  layout:   ViewValuePageLayout,
  post: ApplicantPost,
  location: Location,
  applicant: Option[Applicant],
  categorys: Seq[Category]
)

case class SiteViewValueApplicantPost(
  layout:   ViewValuePageLayout,
  location: Seq[Location],
  aid:      Applicant.Id
)
