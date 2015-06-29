package servlets

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

object JsonHolder {

  def add(v:String) {
    addJson = addJson + v
  }
  var addJson = ""
  def json = s"""
[
  ${addJson}
  {"author": "Pete Hunt", "text": "This is one comment"},
  {"author": "Jordan Walke", "text": "This is *another* comment"}
]
"""
  def apply() = json
}
class GetServlet extends HttpServlet {

  override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
    response.setContentType("application/json")
    response.setCharacterEncoding("UTF-8")
    response.getWriter.write(JsonHolder())
  }

}

class PostServlet extends HttpServlet {

  override def doPost(request: HttpServletRequest, response: HttpServletResponse) {

    
    response.setContentType("application/json")
    response.setCharacterEncoding("UTF-8")

    println("post json!!")
    
    val author = request.getParameter("author")
    val text = request.getParameter("text")
    JsonHolder.add(s"""{"author": "${author}", "text": "${text}"},""")
    
    response.getWriter.write("")

  }

}