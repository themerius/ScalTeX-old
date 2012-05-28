package controllers

import play.api._
import play.api.mvc._

import scala.sys.process._

//import java.io.{OutputStreamWriter, FileOutputStream}

object Application extends Controller {
  
  def index = Action {
    val heading = views.html.heading(1, "Mein Paper")
    val par = views.html.par("Das ist der Anfang von meinem Paper.")

    val math = views.html.math(
      """Ich kann auch einen mathematischen Paragraph machen. """ +
      """Zum Beispiel mit Inline-Mathe $\alpha_1^2 = 45° ± 1\%$ """ +
      """auch komplexe und lange Ausdrücke:""" +
      """$$x = {-b \pm \sqrt{b^2-4ac} \over 2a}.$$""" +
      """Das ganze kann man auch mit UTF16-Zeichen mischen!""")

    val par2 = views.html.par("Abbildungen kann man auch anzeigen lassen:")

    execPython("mpl.py")
    val fig = views.html.figure("plot.png", "Ein einfacher Plot!")

    val par3 =  views.html.par(
      "Referenzen, wie z.B. auf Abb. 1 sollen auch möglich sein und " +
      "auf Literaturangaben, ein Inhaltsverzeichnis muss noch automatisch " +
      "generiert werden, die Kapitel-Nummerierungen, Seitenzahlen etc. auch!")

    val subheading = views.html.heading(2, "Ein längerer Abschnitt")
    // Hier kann dann auch markdown/html/xml was auch immer geladen werden
    var par4 = views.html.par(scala.io.Source.fromFile("ipsum.txt").mkString)

    val out = views.html.index(heading + par + math + par2 + fig + par3 +
      subheading + par4 + par4 + par4)
    Ok(out)
  }

  def footer(pageNr: String, pageOverall: String) = Action {
    Ok(views.html.foot(pageNr, pageOverall))
  }


/*url   --header "Content-type: application/json"   --request POST   --data '{"name": "Guillaume"}'   http://localhost:9000/sayHello*/
  def sayHello = Action(parse.json) { request =>
    (request.body \ "name").asOpt[String].map { name =>
      Ok("Hello " + name)
    }.getOrElse {
      BadRequest("Missing parameter [name]")
    }
  }

/*
  def writeFile(content: String): String = {
    val out = new OutputStreamWriter(
      new FileOutputStream("tmp_script.py"), "UTF-8")
    out.append(content)
    out.close
    return "tmp_script.py"
  }
*/

  def execPython(path: String) = {
    //val path = writeFile(s)
    val command = "python " + path
    command.!
  }

}


// Minimal "DSL"
object PythonScript {

  var scriptString = "#!/usr/bin/env python\n"

  def >>>(line: String) = {
    scriptString = scriptString + line + '\n'
    this
  }
}
