import org.fusesource.scalate._

object Hello {
  def main(args: Array[String]) {
    println("Hello World!")
    print(this.tmpl)
  }

  def tmpl : String = {
    val engine = new TemplateEngine
    val output = engine.layout("test.scaml")
    return output
  }
}
