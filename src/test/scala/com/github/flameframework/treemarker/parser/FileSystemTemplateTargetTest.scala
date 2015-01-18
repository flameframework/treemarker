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
