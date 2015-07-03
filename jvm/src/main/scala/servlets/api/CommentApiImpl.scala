package servlets.api

import shared.model.Comment
import shared.api.CommentApi
import scala.collection.mutable.Map

object CommentApiImpl extends CommentApi {

  var idCounter = 3

  val comments = Map[Int, Comment](
    1 -> Comment(1, "Pete Hunt", "comment"),
    2 -> Comment(2, "Body Geen", "comment comment"))

  def list(): Seq[Comment] = comments.values.toSeq.sortBy { c => c.id }

  def update(c: Comment): Seq[Comment] = {
    c.id = idCounter
    comments.put(idCounter, c)
    idCounter = idCounter + 1
    list()
  }

  def delete(id: Int): Seq[Comment] = {
    comments.remove(id)
    list()
  }
}