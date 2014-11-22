package com.github.mvollebregt.flame.compiler

import java.io.{FileWriter, Writer, FileOutputStream, OutputStream}

/**
 * Created by michel on 21-11-14.
 */
class FileSystemTemplateWriter extends TemplateWriter {

  def getWriterFor(template: String) : Writer = new FileWriter(template)

}
