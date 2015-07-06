
import org.fusesource.scalate.support.TemplatePackage
import org.fusesource.scalate.TemplateSource
import org.fusesource.scalate.Binding

class ScalatePackage extends TemplatePackage {
  def header(source: TemplateSource, bindings: List[Binding]) = """

  """
}