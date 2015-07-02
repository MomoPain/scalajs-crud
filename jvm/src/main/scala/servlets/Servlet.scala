package servlets

import org.scalatra._
import scala.concurrent.ExecutionContext.Implicits.global
import upickle._
import autowire._
import api._
import model._
import scala.concurrent.duration._
import scala.concurrent.Await
import scalatags.Text.all._
import scalatags.Text.{ all => tags }

object AutowireServer extends autowire.Server[String, upickle.Reader, upickle.Writer] {
  def read[Result: upickle.Reader](p: String) = upickle.read[Result](p)
  def write[Result: upickle.Writer](r: Result) = upickle.write(r)
}

object ApiImpl extends Api {

  var todos: Seq[TodoItem] = Seq(TodoItem("Pete Hunt", "comment"))

  def motd(name: String): String = {
    ""
  }

  def getTodos(): Seq[TodoItem] = {
    todos
  }

  def updateTodo(item: TodoItem): Seq[TodoItem] = {
    Seq()
  }

  def deleteTodo(itemId: String): Seq[TodoItem] = {
    Seq()
  }
}

class Servlet extends ScalatraServlet {

  val basePath = "api"

  get("/") {
    contentType = "text/html"
    tags.html(
      tags.head(
        tags.meta(tags.httpEquiv := "Content-Type", tags.content := "text/html; charset=UTF-8"),
        //tags.link(tags.rel := "stylesheet", tags.`type` := "text/css", href := "css/styleWUI.css"),        
        tags.script(tags.`type` := "text/javascript", tags.src := "js/jquery-1.11.3.min.js"),
        tags.script(tags.`type` := "text/javascript", tags.src := "js/react.js"),
        tags.script(tags.`type` := "text/javascript", tags.src := "js/JSXTransformer.js"),
        tags.script(tags.`type` := "text/javascript", tags.src := "js/client-fastopt.js"),
        tags.script(tags.`type` := "text/javascript", tags.src := "https://cdnjs.cloudflare.com/ajax/libs/marked/0.3.2/marked.min.js")),
      tags.body(
        tags.div(tags.id := "content"),
        tags.script(tags.`type` := "text/jsx", tags.src := "./js/sample.js")))
  }

  post(s"/$basePath/*") {
    Await.result(AutowireServer.route[Api](ApiImpl)(
      autowire.Core.Request(Seq(basePath) ++ multiParams("splat").head.split("/"),
        upickle.read[Map[String, String]](request.body))), Duration.Inf)
  }

}
