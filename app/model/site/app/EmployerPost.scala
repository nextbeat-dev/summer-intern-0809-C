package model.site.app

import model.component.util.ViewValuePageLayout

import persistence.employer_post.model.EmployerPost

// 応募者
//~~~~~~~~~~~~~~~~~~~~~
case class SiteViewValueEmployerPostIndex(
  layout:   ViewValuePageLayout,
  posts: Seq[EmployerPost]
)