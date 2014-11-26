package com.github.mvollebregt.flame.compiler.domain

/**
 * Abstract representation for a domain object.
 */
class DomainClass(name : String) {

  def getName = name

}

object DomainClass {

  def apply(name: String) = new DomainClass(name)

}