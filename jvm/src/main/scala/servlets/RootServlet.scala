package servlets

import org.scalatra.ScalatraServlet
import scalatags.Text.{ all => tags }
import scalatags.Text.all.stringAttr
import servlets.api.CommentApiImpl
import shared.api.CommentApi
import org.scalatra.scalate.ScalateSupport

class RootServlet extends ScalatraServlet with ScalateSupport {

  override def isScalateErrorPageEnabled = false
  
  get("/") {
    contentType = "text/html"
    
    ssp("/index")
  }
}
