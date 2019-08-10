package model.site.app

import model.component.util.ViewValuePageLayout
import persistence.category.model.Category

import persistence.applicant_post.model.ApplicantItem
import persistence.applicant_post.model.ApplicantPost
import persistence.geo.model.Location

// 応募者
//~~~~~~~~~~~~~~~~~~~~~
case class SiteViewValueApplicantPostIndex(
  layout:   ViewValuePageLayout,
  posts: Seq[ApplicantItem]
)

case class SiteViewValueApplicantPostShow(
  layout:   ViewValuePageLayout,
  posts: ApplicantPost,
  location: Location,
  categorys: Seq[Category]
)

