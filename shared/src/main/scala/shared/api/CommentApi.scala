package shared.api

import shared.model.Comment

trait CommentApi {
  def list(): Seq[Comment]
  def update(model: Comment): Seq[Comment]
  def delete(id: Int): Seq[Comment]
}