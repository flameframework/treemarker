package com.github.mvollebregt.flame.compiler

import java.io.{PrintWriter, StringReader}

import org.mockito.Mockito._
import org.scalatest.FlatSpec
import org.scalatest.mock.MockitoSugar

/**
 * Created by michel on 09-01-15.
 */
class TokenizerTest extends FlatSpec with MockitoSugar {

  val START = "<&[^>]*>"
  val END = "</&[^>]*>"

  trait Fixture {
    val tokenWriter = mock[TokenWriter]
    val tokenizer = new PrintWriter(new Tokenizer(tokenWriter, START, END))
  }

  "A tokenizer" should "write the separate tokens to the underlying token writer" in new Fixture {
    // when
    tokenizer.print("before <&start> middle </& end > after")
    tokenizer.close()
    // then
    verify(tokenWriter).writeLiteral("before ")
    verify(tokenWriter).writeTag(START, "<&start>")
    verify(tokenWriter).writeLiteral(" middle ")
    verify(tokenWriter).writeTag(END, "</& end >")
    verify(tokenWriter).writeLiteral(" after")
    verify(tokenWriter).close()
  }

}
