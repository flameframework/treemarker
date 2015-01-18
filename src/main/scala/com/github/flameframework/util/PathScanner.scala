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

package com.github.flameframework.util

import scala.collection.JavaConverters._
import java.io.File
import java.util.zip.ZipFile
import com.github.flameframework.util.AutoCloseableUtils._

/**
 * Created by michel on 21-11-14.
 */
object PathScanner  {
  
  def classpathEntries : Iterable[String] = {
    val classpath = System.getProperty("java.class.path", ".")
    val classpathElements = classpath.split(":")
    classpathElements.flatMap(entriesFor)
  }

  def entriesFor(directoryOrJarFile: String) : Iterable[String] = {
    val file = new File(directoryOrJarFile)
    if (file.isDirectory) {
      directoryEntries(file)
    } else {
      jarFileEntries(file)
    }
  }

  def directoryEntries(directory: File) : Iterable[String] = directoryEntries(directory, directory.getCanonicalPath.size + 1)

  private def directoryEntries(directory : File, prefixLength : Int) : Iterable[String] = {
    directory.listFiles().flatMap { file =>
      if (file.isDirectory) {
        directoryEntries(file, prefixLength)
      } else {
        Seq(file.getCanonicalPath.substring(prefixLength))
      }
    }
  }

  def jarFileEntries(file: File) : Iterable[String] = {
    tryWithResource(new ZipFile(file)) { zip =>
      for (entry <- zip.entries.asScala.toList) yield entry.getName
    }
  }
}