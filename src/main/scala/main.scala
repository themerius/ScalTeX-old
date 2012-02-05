import scaltex.article2012._

object Hello {
  def main(args: Array[String]) {
    val vorwort = new Section("vorwort.md")
    //vorwort.newPage("at position")
    val article = new Article(List(vorwort))
    println(article.gen)
  }
}
