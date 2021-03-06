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

package com.github.flameframework.treemarker

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
