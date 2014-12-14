package com.github.mvollebregt.flame.compiler.domain

import scala.collection.JavaConversions._

case class DomainClassIdentifier(name: String)

/**
 * Abstract representation for a domain object.
 */
class DomainClass(name : String, properties: java.lang.Iterable[Variable]) extends Type {

  def getName = DomainClassIdentifier(name)
  def getProperties = properties

}

object DomainClass {

  def apply(name: String, properties : Seq[Variable] = Nil) = new DomainClass(name, properties)

}