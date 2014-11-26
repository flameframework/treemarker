package com.github.mvollebregt.flame.compiler

import scala.collection.mutable.Stack
import scala.util.matching.Regex
import scala.util.matching.Regex._
import java.io.{StringWriter, Writer}

/**
 * Created by michel on 21-11-14.
 */
class FreemarkerWriterWrapper(defaultFile: String, templateWriter: TemplateWriter) extends Writer {

  val startTagSingleQuote = "\\<&\\s*output\\s+file\\s*=\\s*'([^']*)'\\s*>"
  val startTagDoubleQuote = "\\<&\\s*output\\s+file\\s*=\\s*\"([^\"]*)\"\\s*>"
  val endTag = "\\</&\\s*output\\s*>"

  val anyTag = new Regex(s"(?:$startTagSingleQuote)|(?:$startTagDoubleQuote)|($endTag)", "filesq", "filedq", "end")
  private val buffer = new StringWriter()
  val writers = new Stack[Writer]
  writers.push(templateWriter.getWriterFor(defaultFile))

  override def write(cbuf: Array[Char], off: Int, len: Int): Unit = {
    buffer.write(cbuf, off, len)
  }

  override def flush(): Unit = ()

  override def close(): Unit = {
    writeAll(0)
    while (writers.nonEmpty) writers.pop.close
  }

  private def writeAll(offset: Int) : Unit = {
    val text = buffer.toString.substring(offset)
    if (!text.isEmpty) {
      val processed = anyTag.findFirstMatchIn(text) match {
        case Some(m) => writePart(text.substring(0, m.start)); processTag(m)
        case None => writePart(text)
      }
      writeAll(offset + processed)
    }
  }

  private def writePart(text : String) : Int = {
    writers.top.write(text)
    text.length
  }

  private def processTag(m: Match) : Int = {
    val file = Option(m.group("filesq")).orElse(Option(m.group("filedq")))
    val endtag = Option(m.group("end"))
    if (file.isDefined) {
      writers.push(templateWriter.getWriterFor(file.get))
    } else if (endtag.isDefined) {
      writers.top.close()
      writers.pop()
    }
    m.end
  }


}
