package com.github.mvollebregt.flame.compiler.domain

import scala.collection.JavaConversions._

/**
 * Abstract representation for a domain object.
 */
class DomainClass(name : String, properties: java.lang.Iterable[Property]) {

  def getName = name

  def getProperties = properties

  // TODO register custom String converter for FreeMarker for type String -> NSString etc
  // setObjectWrapper?!?


}

object DomainClass {

  def apply(name: String, properties : Seq[Property] = Nil) = new DomainClass(name, properties)

}