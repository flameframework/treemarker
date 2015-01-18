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
