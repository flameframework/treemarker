package com.github.mvollebregt.treemarker.parser

import java.io.{PrintWriter, StringWriter}

import com.github.mvollebregt.treemarker.TemplateTarget
import freemarker.template.utility.NullWriter
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar

/**
 * Created by michel on 22-11-14.
 */
@RunWith(classOf[JUnitRunner])
class TreeMarkerTokenProcessorTest extends FlatSpec with MockitoSugar {

  trait Fixture {
    val templateWriter = mock[TemplateTarget]
    val defaultOutput = new StringWriter()
    when(templateWriter.getWriterFor("default file")).thenReturn(defaultOutput)
    val processor = new PrintWriter(new TokenizingWriter(new TreeMarkerTokenProcessor("default file", templateWriter)))
  }

//  "A template without file output markers" should "write a copy of the template to the default file" in new Fixture {
//    // when
//    processor.print("a template without file output markers")
//    processor.close()
//    // then
//    assert(defaultOutput.toString == "a template without file output markers")
//  }


  "A template with a file output marker with single quote" should "write the inner contents to the specified output" in new Fixture {
    // given
    val specifiedOutput = new StringWriter()
    when(templateWriter.getWriterFor("default file", Some("specified name"), true)).thenReturn(specifiedOutput)
    // when
    processor.println("<&output file='specified name'>inner contents</&output>")
    processor.close()
    // then
    assert(specifiedOutput.toString == "inner contents")
  }


  "A template with a file output marker with double quote" should "write the inner contents to the specified output" in new Fixture {
    // given
    val specifiedOutput = new StringWriter()
    when(templateWriter.getWriterFor("default file", Some("another specified name"), true)).thenReturn(specifiedOutput)
    // when
    processor.println("<&output file=\"another specified name\">other inner contents</&output>")
    processor.close()
    // then
    assert(specifiedOutput.toString == "other inner contents")
  }

  "The writer" should "be able to check if a file already exists" in new Fixture {
    when(templateWriter.getWriterFor(any[String], any[Option[String]], anyBoolean)).thenReturn(NullWriter.INSTANCE)
    // when
    processor.println("<&output file='specified name' overwrite='false'>inner contents</&output>")
    processor.close()
    // then
    verify(templateWriter).getWriterFor("default file", Some("specified name"), false)
  }

}
