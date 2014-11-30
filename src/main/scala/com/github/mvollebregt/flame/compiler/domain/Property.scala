package com.github.mvollebregt.flame.compiler.domain

/**
 * Created by michel on 30-11-14.
 */
class Property(name: String, propertyType: Type.Type) {

  def getName = name

  def getType = propertyType

}

object Property {

  def apply(name: String, propertyType: Type.Type) = new Property(name, propertyType)
}
