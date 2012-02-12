import scaltex.article2012._

import java.io.FileWriter

object Hello {
  def main(args: Array[String]) {
    val vorwort = new Section("vorwort.md")
    //vorwort.newPage("at position")
    val article = new Article(List(vorwort))

    // Save Article
    val articleStr = article.gen
    val writer = new FileWriter("output.html")
    writer.append(articleStr)
    writer.close
  }
}
