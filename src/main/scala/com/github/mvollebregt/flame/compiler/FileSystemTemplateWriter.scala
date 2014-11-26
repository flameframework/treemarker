package com.github.mvollebregt.flame.compiler

import java.io.{File, FileWriter, Writer}

/**
 * Created by michel on 21-11-14.
 */
class FileSystemTemplateWriter(target: File) extends TemplateWriter {

  def getWriterFor(template: String) : Writer = {
    val file = new File(target, template)
    if (file.getParentFile != null) file.getParentFile.mkdirs()
    new FileWriter(file)
  }

}
