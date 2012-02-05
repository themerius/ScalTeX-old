package scaltex.article2012

import org.fusesource.scalate._
import scala.xml._

trait ArticleItem {
  var myItems: List[ArticleItem]
  def make: String = ""
}

class Section(section: String) extends ArticleItem {

  var myItems: List[ArticleItem] = Nil

  override def make: String = {
    val engine = new TemplateEngine
    var output = """<?xml version="1.0"?><html>"""
    output = output + engine.layout(this.section) + "</html>"
    // den inhalt extrahieren, referenzen, bilder und all sowas,
    // das muss dann intern wieder in Picture etc. gesteckt werden...
    /*val xmlout = XML.loadString(output)
    for ( line <- xmlout \\ "_" ) {
      line match {
        case <h1>{x}</h1> => val test = new Heading(x, 1)
          println(test.make)
        case <h2>{x}</h2> => val test = new Heading(x, 2)
          println(test.make)
        case <p>{x}</p> => val test = new Paragraph(x)
          println(test.make)
        case _ => //println(line)
      }
    }*/
    // und dann in das style template stecken
    //val test = new Heading(xmlout, 1)
    //println(test.make)
    this.myItems = this.produceItemList(output).toList
    return ""
  }

  def produceItemList(xmlString: String) = {  // TODO
    val xml = XML.loadString(xmlString)
    for ( node <- xml \\ "_" ) yield node match {
        case <h1>{x}</h1> => new Heading(x, 1)
        case <h2>{x}</h2> => new Heading(x, 2)
        case <p>{x}</p> => new Paragraph(x)
        case _ => new Dummy() //println(line)
    }
  }

  def newPage(position: String) {
    println(position)
  }
}

class Article(items: List[ArticleItem]) {

  val engine = new TemplateEngine

  def gen: String = {
    var str = ""
    for ( item <- this.items ) {
      item.make
      for ( innerItem <- item.myItems ) {
        str += innerItem.make
      }
    }
    val output = this.engine.layout("article2012/preamble.scaml",
      Map("title" -> "SCALTEX", "content" -> str))
    return output
  }
}

// SOON: class Picture

class Heading(input: scala.xml.Node, order: Int) extends ArticleItem {

  var myItems = List[ArticleItem]()

  var nr = 1
  var heading = input.text

  override def make: String = {
    val engine = new TemplateEngine
    val output = engine.layout("article2012/headings.scaml",
      Map("nr" -> this.nr.toString, "heading" -> this.heading,
          "order" -> this.order))
    return output
  }

  // Set Number.

}

class Paragraph(input: scala.xml.Node) extends ArticleItem {

  var myItems = List[ArticleItem]()

  var content = input.text
  var kind = "first"

  override def make: String = {
    val engine = new TemplateEngine
    val output = engine.layout("article2012/paragraphs.scaml",
      Map("content" -> this.content, "kind" -> this.kind))
    return output
  }

  // Set Kind

}

class Dummy extends ArticleItem {
  var myItems: List[ArticleItem] = Nil

  override def make: String = ""
}
