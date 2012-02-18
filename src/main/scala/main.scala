import scaltex.article2012._

import java.io.{OutputStreamWriter, FileOutputStream}

object Hello {
  def main(args: Array[String]) {
    val vorwort = new Section("vorwort.md")
    //vorwort.newPage("at position")
    val article = new Article(List(vorwort))

    // Save Article
    val articleStr = article.gen

    val out = new OutputStreamWriter(
      new FileOutputStream("output.html"), "UTF-8")
    out.append(articleStr)
    out.close
  }
}
