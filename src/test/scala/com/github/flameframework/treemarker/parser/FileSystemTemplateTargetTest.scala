package com.github.flameframework.treemarker.parser

import java.io.File._
import java.io.PrintWriter

import com.github.flameframework.treemarker.FileSystemTemplateTarget
import com.github.flameframework.util.AutoCloseableUtils._
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner

import scala.io.Source._

/**
 * Created by michel on 09-01-15.
 */
@RunWith(classOf[JUnitRunner])
class FileSystemTemplateTargetTest extends FlatSpec {

  trait Fixture {
    val existingFile = createTempFile("file_for_test_purposes", null)
    tryWithResource(new PrintWriter(existingFile)) { writer => writer.print("content was not overwritten") }
    val templateWriter = new FileSystemTemplateTarget(existingFile.getParentFile)
  }

  "A template writer" should "overwrite an existing file by default" in new Fixture {
    // when
    tryWithResource(templateWriter.getWriterFor(existingFile.getName)) {
      writer => writer.write("content was overwritten")
    }
    // then
    assert(fromFile(existingFile).mkString == "content was overwritten")
  }

  "A template writer" should "not overwrite an existing file if overwrite = false" in new Fixture {
    // when
    tryWithResource(templateWriter.getWriterFor(existingFile.getName, overwrite = false)) {
      writer => writer.write("content was overwritten")
    }
    // then
    assert(fromFile(existingFile).mkString == "content was not overwritten")
  }
  
  
  
}
