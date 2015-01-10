package com.github.mvollebregt.treemarker.parser

import java.io.Writer

import scala.util.matching.Regex

/**
 * Created by michel on 09-01-15.
 */
class TokenizingWriter(tokenWriter: TokenProcessor) extends Writer {

  private val tagPatterns = tokenWriter.tagPatterns

  private val anyTag = new Regex(tagPatterns.mkString("(", ")|(",")"), tagPatterns :_*)

  private val buffer = new StringBuilder

  override def write(cbuf: Array[Char], off: Int, len: Int): Unit = {

    buffer ++= String.valueOf(cbuf).substring(off, len)

    var processedIndex = 0

    anyTag.findAllMatchIn(buffer).foreach { m =>

      val literal = buffer.substring(processedIndex, m.start)
      if (!literal.isEmpty) tokenWriter.writeLiteral(literal)

      val tag = tagPatterns.find(m.group(_) != null).get
      tokenWriter.writeTag(tag, m.matched)

      processedIndex = m.end

    }

    buffer.delete(0, processedIndex)

  }

  override def flush(): Unit = tokenWriter.onFlush()

  override def close(): Unit = {
    tokenWriter.writeLiteral(buffer.toString)
    buffer.clear()
    tokenWriter.onClose()
  }
}
