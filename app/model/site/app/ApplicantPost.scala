package model.site.app

import model.component.util.ViewValuePageLayout
import persistence.category.model.Category

import persistence.applicant_post.model.ApplicantPost
import persistence.geo.model.Location

// 応募者
//~~~~~~~~~~~~~~~~~~~~~
case class SiteViewValueApplicantPostIndex(
  layout:   ViewValuePageLayout,
  posts: Seq[ApplicantPost]
)

case class SiteViewValueApplicantPostShow(
  layout:   ViewValuePageLayout,
  post: ApplicantPost,
  location: Location,
  categorys: Seq[Category]
)

case class SiteViewValueApplicantPost(
  layout: ViewValuePageLayout,
  location: Seq[Location]
)