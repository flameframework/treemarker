package com.github.mvollebregt.flame.compiler.action

import com.github.mvollebregt.flame.compiler.domain.{Type, Variable}

import scala.collection.JavaConversions._

/**
 * Created by michel on 13-12-14.
 */
class NativeAction(name: String, input: java.lang.Iterable[Variable], output: Type, provided: Boolean) {

  def getName = name
  def getInput = input
  def getOutput = output
  def isProvided = provided


}

object NativeAction {

  def apply(name: String, input: Seq[Variable] = Nil, output: Option[Type] = None, provided: Boolean = false) =
    new NativeAction(name, input, output.getOrElse(null), provided)

}