package client

import org.scalajs.dom
import scala.concurrent.Future
import scalatags.JsDom._
import all._
import tags2.section
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
//import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import upickle._
import autowire._
import api.Api
import model.TodoItem
import scala.util.Success
import scala.util.Failure
import scala.scalajs.js.annotation.JSExportAll
import scala.concurrent.Await
import scala.concurrent.duration.Duration
//import scala.concurrent.blocking
import model.TodoItem
import scala.scalajs.js
import js.JSConverters._
import japgolly.scalajs.react._
//import japgolly.scalajs.react.extra.OnUnmount

@JSExport
object TodoStore {

  @JSExport
  def bar(): String = "***"

}

trait RComp extends js.Object {
  def setState(obj: js.Object): Unit = js.native
}
@JSExport
object TodoAction {

  //  @JSExport
  //  def await[T](f: Future[T]): T = {
  //    println("async")
  //    var result: Option[T] = None
  //    f.foreach { data =>
  //      result = Some(data)
  //    }
  //    result.getOrElse(throw new IllegalAccessException)
  //  }
  //
  //  def foo[T](fanc: () => T) = {
  //    fanc()
  //  }

  @JSExport
  def getTodos(c: RComp) {
    Post[Api].getTodos().call().onSuccess {
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
  def updateTodo(item: TodoItem): Seq[TodoItem] = Seq() //await(Post[Api].updateTodo(item).call())

  @JSExport
  def deleteTodo(itemId: String): Seq[TodoItem] = Seq() //await(Post[Api].deleteTodo(itemId).call())
}

object Post extends autowire.Client[String, upickle.Reader, upickle.Writer] {

  override def doCall(req: Request): Future[String] = {
    val url = req.path.mkString("/")
    val r = dom.ext.Ajax.post(
      url = "http://localhost:8080/" + url,
      data = upickle.write(req.args))
    r.map(_.responseText)
  }

  def read[Result: upickle.Reader](p: String) = upickle.read[Result](p)

  def write[Result: upickle.Writer](r: Result) = upickle.write(r)
}
