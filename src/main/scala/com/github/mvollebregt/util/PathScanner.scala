package com.github.mvollebregt.util

import scala.collection.JavaConverters._
import java.io.File
import java.util.zip.ZipFile
import com.github.mvollebregt.util.AutoCloseableUtils._

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
      directoryEntries(file).map(_.substring(file.getCanonicalPath.size + 1))
    } else {
      jarFileEntries(file)
    }
  }

  def directoryEntries(directory : File) : Iterable[String] = {
    directory.listFiles().flatMap { file =>
      if (file.isDirectory) {
        directoryEntries(file)
      } else {
        Seq(file.getCanonicalPath)
      }
    }
  }

  def jarFileEntries(file: File) : Iterable[String] = {
    tryWithResource(new ZipFile(file)) { zip =>
      for (entry <- zip.entries.asScala.toList) yield entry.getName
    }
  }
}