package com.github.mvollebregt.flame.compiler.domain

/**
 * Created by michel on 13-12-14.
 */

case class ListTypeIdentifier(itemType : Type)

class ListType(itemType: Type) extends Type {

  def getName = ListTypeIdentifier(itemType)

}

object ListType {

  def apply(itemType: Type) = new ListType(itemType)

}