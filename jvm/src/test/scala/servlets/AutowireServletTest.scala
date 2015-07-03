package servlets

import org.json4s._
import org.json4s.JsonAST.JValue
import org.json4s.native.JsonMethods._
import org.scalatest.FunSuiteLike
import org.scalatra.test.scalatest._
import servlets.api.CommentApiImpl
import shared.api.CommentApi
import servlets.api.SharedApiServlet

class AutowireServletTest extends ScalatraSuite with FunSuiteLike {

  val servlet = new SharedApiServlet
  mount(servlet, servlet.rootPackageUri)
 
  def postJson[A](uri: String, body: JValue, headers: Map[String, String])(f: => A): A =
    post(uri, compact(render(body)).getBytes("utf-8"), Map("Content-Type" -> "application/json") ++ headers)(f)

    

  test("Post /shared/api/CommentApi/list") {
    val json = parse("{}")

    postJson("/shared/api/CommentApi/list", json, Map()) {
      println(body)
      status should equal(200)
    }
  }

}