package servlets

import scala.concurrent.Await
import org.scalatra.ScalatraServlet
import scala.concurrent.duration.Duration
import api.CommentApi
import model.Comment
import autowire._
import scala.concurrent.ExecutionContext.Implicits.global

object CommentServerApi extends AutowireServlet[CommentApi] with CommentApi {

  val basePath = "api"

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

trait AutowireServlet[T] extends ScalatraServlet {
  self: T =>

  val basePath: String

  post(s"/$basePath/*") {
    val f = UpickleAutowireServer.route[T](self)(
      autowire.Core.Request(Seq(basePath) ++ multiParams("splat").head.split("/"),
        upickle.read[Map[String, String]](request.body)))
    Await.result(f, Duration.Inf)

    //    val f = UpickleAutowireServer.route[CommentApi](CommentServerApi)(
    //      autowire.Core.Request(Seq(basePath) ++ multiParams("splat").head.split("/"),
    //        upickle.read[Map[String, String]](request.body)))
    //    Await.result(f, Duration.Inf)
  }

}

