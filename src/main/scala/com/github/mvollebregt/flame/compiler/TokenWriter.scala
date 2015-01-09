package com.github.mvollebregt.flame.compiler

/**
 * Created by michel on 09-01-15.
 */
trait TokenWriter {

  def writeTag(tagPattern: String, contents: String) : Unit

  def writeLiteral(literal: String) : Unit

  def flush(): Unit

  def close(): Unit

}
