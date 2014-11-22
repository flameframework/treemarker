package com.github.mvollebregt.flame.compiler

import scala.collection.mutable.Stack
import java.io.Writer

/**
 * Created by michel on 21-11-14.
 */
class FreemarkerWriterWrapper(defaultFile: String, templateWriter: TemplateWriter) extends Writer {

  private val writers = new Stack[Writer]
  private val buffer = new StringBuilder

  private val out = templateWriter.getWriterFor(defaultFile)

  override def write(cbuf: Array[Char], off: Int, len: Int): Unit = {
    // detecteer <& en </& en push of pop dan een writer op de stack, anders gewoon schrijven
    // als stack is leeg en er is een whitespace-karakter dan bufferen
    // als stack is leeg en er is een non whitespace-karakter dan default writer op stack zetten
    out.write(cbuf, off, len)

  }

  override def flush(): Unit = for (writer <- writers) writer.flush()

  override def close(): Unit = for (writer <- writers) writer.close()

}
