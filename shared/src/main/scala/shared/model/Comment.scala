package shared.model

case class Comment(var id: Int, var author: String, var text: String) {
   def this(author: String, text: String) {
    this(0, author, text)
  }
}