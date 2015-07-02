package client

import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scala.scalajs.js
import scala.scalajs.js.Any.fromString
import scala.scalajs.js.JSConverters.JSRichGenTraversableOnce
import scala.scalajs.js.annotation.JSExport

import org.scalajs.dom

import api.CommentApi
import autowire.clientCallable
import autowire.unwrapClientProxy
import model.Comment
import upickle.MapW

@JSExport
object CommentAction {

  @JSExport
  def getTodos(c: RiactComponent) {
    Post[CommentApi].list().call().onSuccess {
      case todos => {
        if (todos.isEmpty) {
          println("todos is empty")
          c.setState(js.Array())
        } else {
          val state = todos.map(t => js.Dynamic.literal("author" -> t.author, "text" -> t.text)).toJSArray
          println(state)
          c.setState(js.Dynamic.literal("data" -> state))
        }
      }
    }

    //    val a = Post[Api].getTodos().call().
    //    Await.result(a, Duration.MinusInf)
    //      Post.write(a.value)
  }

  @JSExport
  def update(item: Comment): Seq[Comment] = Seq() //await(Post[Api].updateTodo(item).call())

  @JSExport
  def delete(itemId: String): Seq[Comment] = Seq() //await(Post[Api].deleteTodo(itemId).call())
}