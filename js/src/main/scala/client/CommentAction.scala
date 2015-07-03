package client

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scala.scalajs.js
import scala.scalajs.js.Any.fromString
import scala.scalajs.js.JSConverters.JSRichGenTraversableOnce
import scala.scalajs.js.annotation.JSExport

import org.scalajs.dom

import shared.api.CommentApi
import shared.model.Comment
import autowire._
import upickle.MapW

@JSExport
object CommentAction {

  @JSExport
  def list(c: RiactComponent) {
    PostClient[CommentApi].list().call().onSuccess {
      case todos => {
        if (todos.isEmpty) {
          c.setState(js.Array())
        } else {
          val state = todos.map(t => js.Dynamic.literal("author" -> t.author, "text" -> t.text)).toJSArray
          c.setState(js.Dynamic.literal("data" -> state))
        }
      }
    }
  }

  @JSExport
  def update(item: Comment): Seq[Comment] = Seq() //await(Post[Api].updateTodo(item).call())

  @JSExport
  def delete(itemId: String): Seq[Comment] = Seq() //await(Post[Api].deleteTodo(itemId).call())
}