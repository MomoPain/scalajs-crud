
import org.fusesource.scalate.support.TemplatePackage
import org.fusesource.scalate.TemplateSource
import org.fusesource.scalate.Binding

class ScalatePackage extends TemplatePackage {
  /** Returns the Scala code to add to the top of the generated template method */
  def header(source: TemplateSource, bindings: List[Binding]) = """

// some shared imports
//import com.acme._
//import com.acme.something.MyHelper._

// some helper methods
// would be better being imported from a helper class like MyHelper above
//def time = new java.util.Date()

  """
}