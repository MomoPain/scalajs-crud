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

class Servlet extends ScalatraServlet {

  get("/") {
    contentType = "text/html"
    tags.html(
      tags.head(
        tags.meta(tags.httpEquiv := "Content-Type", tags.content := "text/html; charset=UTF-8"),
        tags.script(tags.`type` := "text/javascript", tags.src := "js/jquery-1.11.3.min.js"),
        tags.script(tags.`type` := "text/javascript", tags.src := "js/react.js"),
        tags.script(tags.`type` := "text/javascript", tags.src := "js/JSXTransformer.js"),
        tags.script(tags.`type` := "text/javascript", tags.src := "js/client-fastopt.js"),
        tags.script(tags.`type` := "text/javascript", tags.src := "https://cdnjs.cloudflare.com/ajax/libs/marked/0.3.2/marked.min.js")),
      tags.body(
        tags.div(tags.id := "content"),
        tags.script(tags.`type` := "text/jsx", tags.src := "./js/sample.js")))
  }

}
