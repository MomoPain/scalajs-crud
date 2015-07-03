package servlets.api

import shared.model.Comment
import shared.api.CommentApi

object CommentApiImpl extends CommentApi {

  var comments: Seq[Comment] = Seq(Comment("Pete Hunt", "comment"))

  def list(): Seq[Comment] = comments

  def get(id: String): Comment = comments.head

  def update(comment: Comment): Seq[Comment] = {
    comments
  }

  def delete(id: String): Seq[Comment] = {
    comments
  }
}