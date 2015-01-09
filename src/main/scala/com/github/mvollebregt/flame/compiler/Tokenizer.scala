package com.github.mvollebregt.flame.compiler

import java.io.{Writer, Reader}

import scala.annotation.tailrec
import scala.util.matching.Regex

trait Token
case class Tag(pattern: String, contents: String) extends Token
case class Literal(literal: String) extends Token

/**
 * Created by michel on 09-01-15.
 */
class Tokenizer(tokenWriter: TokenWriter, tagPatterns: String*) extends Writer {

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

  override def flush(): Unit = tokenWriter.flush()

  override def close(): Unit = {
    tokenWriter.writeLiteral(buffer.toString)
    buffer.clear()
    tokenWriter.close()
  }
}
