package js

import scala.scalajs.js
import scala.annotation.meta.field
import org.scalajs.jquery.jQuery
import org.scalajs.jquery.JQuery
import scala.scalajs.js.annotation.JSExport
import model.Item

/**
 * @author matsushita
 */
object HelloWorld extends js.JSApp {

  def main(): Unit = {
    jQuery("#foo").html("hello world")
  }

  @JSExport
  def foo(jq: JQuery, i: Int) {
    jq.html((i * 2).toString)
  }

  @JSExport
  def fooCase(): Item = Item("Hello", -10)

}