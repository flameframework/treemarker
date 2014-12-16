package com.github.mvollebregt.flame.compiler.domain

import scala.collection.JavaConversions._

/**
 * Abstract representation for a domain object.
 */
class DomainClass(name : String, properties: java.lang.Iterable[Variable]) extends Type {

  def getName = name
  def getProperties = properties

}

object DomainClass {

  def apply(name: String, properties : Seq[Variable] = Nil) = new DomainClass(name, properties)

}