package com.github.mvollebregt.treemarker.parser

import java.io.PrintWriter

import org.mockito.Mockito._
import org.scalatest.FlatSpec
import org.scalatest.mock.MockitoSugar

/**
 * Created by michel on 09-01-15.
 */
class TokenizingWriterTest extends FlatSpec with MockitoSugar {

  val START = "<&[^>]*>"
  val END = "</&[^>]*>"

  trait Fixture {
    val tokenProcessor = mock[TokenProcessor]
    when(tokenProcessor.tagPatterns).thenReturn(Seq(START, END))
    val tokenizingWriter = new PrintWriter(new TokenizingWriter(tokenProcessor))
  }

  "A tokenizing writer" should "send the separate tokens to the token processor" in new Fixture {
    // when
    tokenizingWriter.print("before <&start> middle </& end > after")
    tokenizingWriter.close()
    // then
    verify(tokenProcessor).writeLiteral("before ")
    verify(tokenProcessor).writeTag(START, "<&start>")
    verify(tokenProcessor).writeLiteral(" middle ")
    verify(tokenProcessor).writeTag(END, "</& end >")
    verify(tokenProcessor).writeLiteral(" after")
    verify(tokenProcessor).onClose()
  }

}
