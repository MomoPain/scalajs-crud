package client

import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scala.scalajs.js
import scala.scalajs.js.Dynamic._
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.annotation.JSExport
import autowire._
import shared.api.CommentApi
import shared.model.Comment
import upickle.MapW
import scala.scalajs.js.annotation.JSExportAll

@JSExport
object CommentAction {

  def setComments(comp: ReactComponent, comments: Seq[Comment]) {
    if (comments.isEmpty) {
      comp.setState(js.Array())
    } else {
      val data = comments.map(t => literal("key" -> t.id, "author" -> t.author, "text" -> t.text)).toJSArray
      comp.setState(literal("data" -> data))
    }
  }
  @JSExport
  def list(comp: ReactComponent) {
    PostClient[CommentApi].list().call().onSuccess {
      case comments => setComments(comp, comments)
    }
  }

  @JSExport
  def update(c: js.Dictionary[String], comp: ReactComponent) {
    val comment = new Comment(c.getOrElse("author", ""), c.getOrElse("text", ""))
    PostClient[CommentApi].update(comment).call().onSuccess {
      case comments => setComments(comp, comments)
    }
  }

  @JSExport
  def delete(id: Int, comp: ReactComponent) = {
    PostClient[CommentApi].delete(id).call().onSuccess {
      case comments => setComments(comp, comments)
    }
  }
}