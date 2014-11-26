package com.github.mvollebregt.flame.compiler

import java.io.{File, FileWriter, Writer}

/**
 * Created by michel on 21-11-14.
 */
class FileSystemTemplateWriter extends TemplateWriter {

  def getWriterFor(template: String) : Writer = {
    val file = new File(template)
    file.getParentFile.mkdirs()
    new FileWriter(template)
  }

}
