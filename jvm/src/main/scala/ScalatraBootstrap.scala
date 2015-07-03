import servlets._
import org.scalatra._
import javax.servlet.ServletContext
import servlets.api.CommentApiImpl
import shared.api.CommentApi
import scala.concurrent.ExecutionContext.Implicits.global
import servlets.api.SharedApiServlet



class ScalatraBootstrap extends LifeCycle {

  override def init(context: ServletContext) {

    context.mount(new RootServlet, "/*")
    val sharedApi = new SharedApiServlet    
    context.mount(sharedApi, sharedApi.rootPackageUri)

  }
}

