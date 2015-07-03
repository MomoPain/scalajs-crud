package servlets

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.reflect.ClassTag

import org.scalatra._

import autowire.Core._
import scala.reflect.runtime.universe._
import scala.language.experimental.macros

trait AutowireRoutingServlet extends ScalatraServlet {
  val rootPackage: String
  lazy val rootPackageSeq = rootPackage.split("[.]")

  var routers = Map[String, Router[String]]()  
  
  def rootPackageUri = s"/${rootPackage.replaceAll("[.]", "/")}/*"

  def addRoutor[T](router: Router[String])(implicit ctag: ClassTag[T]) {
    routers += ctag.runtimeClass.getSimpleName -> router
  }

  post(s"/:api/*") {
    val basePath = params("api")
    val autowireRequest = rootPackageSeq ++ Seq(basePath) ++ multiParams("splat").head.split("/")
    routers.get(basePath) match {
      case Some(r) => {
        try {
          Await.result(r(Request(autowireRequest, upickle.read[Map[String, String]](request.body))), Duration.Inf)
        } catch {
          case e: Exception => {
            println(e)
            InternalServerError(s"not found api[${autowireRequest.mkString("/")}]")
          }
        }
      }
      case None => NotFound(s"not found api[${autowireRequest.mkString("/")}]")
    }

  }

}
