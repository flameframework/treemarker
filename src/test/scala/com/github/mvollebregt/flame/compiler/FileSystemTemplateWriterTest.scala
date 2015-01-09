package com.github.mvollebregt.flame.compiler

import java.io.PrintWriter
import java.io.File._
import scala.io.Source._

import com.github.mvollebregt.util.AutoCloseableUtils._
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner

/**
 * Created by michel on 09-01-15.
 */
@RunWith(classOf[JUnitRunner])
class FileSystemTemplateWriterTest extends FlatSpec {

  trait Fixture {
    val existingFile = createTempFile("file_for_test_purposes", null)
    tryWithResource(new PrintWriter(existingFile)) { writer => writer.print("content was not overwritten") }
    val templateWriter = new FileSystemTemplateWriter(existingFile.getParentFile)
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
