package com.github.mvollebregt.flame.compiler.domain

/**
 * Created by michel on 30-11-14.
 */
class Variable(name: String, variableType: Type) {

  def getName = name
  def getType = variableType

}

object Variable {

  def apply(name: String, propertyType: Type) = new Variable(name, propertyType)

}
