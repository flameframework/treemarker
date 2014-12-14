package com.github.mvollebregt.flame.compiler.domain

/**
 * Created by michel on 30-11-14.
 */

case class NativeTypeIdentifier(name : String)

abstract class NativeType(name : String) extends Type {
  def getName = NativeTypeIdentifier(name)
}

case object StringType extends NativeType("String")
case object IntegerType extends NativeType("Integer")
