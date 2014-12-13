package com.github.mvollebregt.flame.compiler.action

import com.github.mvollebregt.flame.compiler.domain.{Type, Variable}

import scala.collection.JavaConversions._

/**
 * Created by michel on 13-12-14.
 */
class ComposedAction(name: String, input: java.lang.Iterable[Variable], output: Type, actions: java.lang.Iterable[Action]) {

  def getName = name
  def getInput = input
  def getOutput = output
  def getActions = actions

}

object ComposedAction {

  def apply(name: String, input: Seq[Variable] = Nil, output: Option[Type] = None, actions: Seq[Action]) =
    new ComposedAction(name, input, output.getOrElse(null), actions)

}