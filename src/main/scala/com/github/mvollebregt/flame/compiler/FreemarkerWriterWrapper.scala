package com.github.mvollebregt.flame.compiler

import freemarker.template.utility.NullWriter

import scala.collection.mutable
import java.io.Writer

/**
 * Created by michel on 21-11-14.
 */
class FreemarkerWriterWrapper(defaultFile: String, templateWriter: TemplateWriter) extends TokenWriter {

  private val startTag = "\\<&(?:(?:\"[^\"]*\"?|'[^'?]*'|[^'\">]*)*)>?"
  private val endTag = "\\</&(?:(?:\"[^\"]*\"?|'[^'?]*'|[^'\">]*)*)>?"

  val tokenizer = new Tokenizer(this, startTag, endTag)

  private val writers = new mutable.Stack[Writer]
  writers.push(NullWriter.INSTANCE)

  override def writeLiteral(literal: String): Unit = writers.top.write(literal)

  override def writeTag(tagPattern: String, contents: String): Unit = {
    tagPattern match {
      case `startTag` => {
        TagParser.parse(contents.substring(2, contents.length - 1)) match {
          case ("output", attributes) => {
            val file = attributes.get("file").flatten
            val overwrite = attributes.get("overwrite").flatten.map(_.trim.toLowerCase) != Some("false")
            val writerFor: Writer = templateWriter.getWriterFor(defaultFile, file, overwrite)
            writers.push(writerFor)
          }
          case _ => throw new Exception
        }
      }
      case `endTag` => {
        writers.top.close()
        writers.pop()
      }
    }
  }

  override def flush(): Unit = ()

  override def close(): Unit = {
    while (writers.nonEmpty) writers.pop().close()
  }


}
