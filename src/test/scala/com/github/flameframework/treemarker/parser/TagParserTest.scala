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

import com.github.flameframework.treemarker.parser.TagParser._
import org.scalatest.FlatSpec

/**
 * Created by michel on 09-01-15.
 */
class TagParserTest extends FlatSpec {

  "The parser" should "parse a tag name" in {
    // when
    val value = parse("tagname")
    // then
    assert(value == ("tagname", Map()))
  }

  it should "parse a single attribute without quotes" in {
    // when
    val value = parse("other-tagname attribute=value")
    // then
    assert(value == ("other-tagname", Map("attribute" -> Some("value"))))
  }

  it should "trim spaces from attribute names and values" in {
    // when
    val value = parse("tagname attribute = value")
    // then
    assert(value == ("tagname", Map("attribute" -> Some("value"))))
  }

  it should "parse multiple attributes" in {
    val value = parse("tagname first='1' second='2'")
    assert(value == ("tagname", Map("first" -> Some("1"), "second" -> Some("2"))))
  }

  it should "parse attributes with different quotes" in {
    // when
    val value = parse("tagname first=\"1\" second='2'")
    // then
    assert(value == ("tagname", Map("first" -> Some("1"), "second" -> Some("2"))))
  }


}
