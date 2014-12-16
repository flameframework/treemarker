package com.github.mvollebregt.flame.compiler.domain

/**
 * Created by michel on 16-12-14.
 */
class PropertyValue(obj: Value, property: Variable) extends Value {

  val getObject = obj
  val getProperty = property

}

object PropertyValue {

  def apply(obj: Value, property: Variable) = new PropertyValue(obj, property)

}