/*
 * Copyright 2015 Michel Vollebregt
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.flameframework.treemarker.parser

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
