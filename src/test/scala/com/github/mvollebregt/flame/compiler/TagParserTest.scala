package com.github.mvollebregt.flame.compiler

import org.scalatest.FlatSpec

import com.github.mvollebregt.flame.compiler.TagParser._

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
