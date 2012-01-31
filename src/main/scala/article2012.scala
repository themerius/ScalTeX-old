package scaltex.article2012

import org.fusesource.scalate._
import scala.xml._

class Section(section: String) {
  def make: String = {
    val engine = new TemplateEngine
    var output = """<?xml version="1.0"?><html>"""
    output = output + engine.layout(this.section) + "</html>"
    // den inhalt extrahieren, referenzen, bilder und all sowas,
    // das muss dann intern wieder in Picture etc. gesteckt werden...
    val xmlout = XML.loadString(output)
    for ( line <- xmlout ) {
      println(line)
    }
    // und dann in das style template stecken
    val test = engine.layout("article2012/headings.scaml", Map("nr" -> "1",
                             "heading" -> "The Heading", "order" -> 1))
    println(test)
    return output
  }

  def newPage(position: String) {
    println(position)
  }
}

class Article(items: List[Section]) {
  def gen: String = {
    // den artikel zusammenbauen
    "hi"
  }
}

class Picture
