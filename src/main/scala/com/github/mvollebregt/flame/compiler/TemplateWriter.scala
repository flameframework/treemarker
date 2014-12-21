package com.github.mvollebregt.flame.compiler

import java.io.{Writer, FileOutputStream, OutputStream}

/**
 * Created by michel on 22-11-14.
 */
trait TemplateWriter {

  def getWriterFor(template: String, output: Option[String] = None) : Writer

}
