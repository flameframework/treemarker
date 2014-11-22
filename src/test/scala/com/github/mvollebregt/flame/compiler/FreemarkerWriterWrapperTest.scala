package com.github.mvollebregt.flame.compiler

import java.io.{ByteArrayOutputStream, BufferedOutputStream, StringWriter, PrintWriter}

import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import org.scalatest.FlatSpec

/**
 * Created by michel on 22-11-14.
 */
@RunWith(classOf[JUnitRunner])
class FreemarkerWriterWrapperTest extends FlatSpec with MockitoSugar {

  val templateWriter = mock[TemplateWriter]
  val defaultOutput = new StringWriter()
  when(templateWriter.getWriterFor("default file")).thenReturn(defaultOutput)
  val processor = new PrintWriter(new FreemarkerWriterWrapper("default file", templateWriter))


  "A template without file output markers" should "write a copy of the template to the default file" in {

    // when
    processor.print("a template without file output markers")

    // then
    assert(defaultOutput.toString == "a template without file output markers")

  }

}
