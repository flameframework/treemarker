package com.github.mvollebregt.treemarker

import java.io.{File, FileWriter, Writer}

import freemarker.template.utility.NullWriter

/**
 * Created by michel on 21-11-14.
 */
class FileSystemTemplateTarget(target: File) extends TemplateTarget {

  override def getWriterFor(template: String, output: Option[String], overwrite: Boolean): Writer = {
    val base = new File(target, template)
    val file = output.map(out => new File(base.getParent, out)).getOrElse(base)
    if (file.getParentFile != null) file.getParentFile.mkdirs()
    if (file.exists() && !overwrite) NullWriter.INSTANCE else new FileWriter(file)
  }
}
