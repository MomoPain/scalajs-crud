package servlets.api

import servlets.AutowireRoutingServlet
import servlets.UpickleAutowireServer
import shared.api.CommentApi
import scala.concurrent.ExecutionContext.Implicits.global

class SharedApiServlet extends AutowireRoutingServlet {
  val rootPackage = "shared.api"

  addRoutor[CommentApi](UpickleAutowireServer.route[CommentApi](CommentApiImpl))
}