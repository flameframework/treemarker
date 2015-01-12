package com.github.flameframework.treemarker.parser

/**
 * Created by michel on 09-01-15.
 */
trait TokenProcessor {

  def tagPatterns : Seq[String]

  def writeTag(tagPattern: String, contents: String) : Unit

  def writeLiteral(literal: String) : Unit

  def onFlush(): Unit

  def onClose(): Unit

}
