package com.github.mvollebregt.flame.compiler.action

import com.github.mvollebregt.flame.compiler.domain.{Type, Variable}

import scala.collection.JavaConversions._

/**
 * Created by michel on 13-12-14.
 */
class ComposedAction(name: String,
                      inputVariables: java.lang.Iterable[Variable],
                      outputVariable: Variable,
                      actionCalls: java.lang.Iterable[ActionCall]) extends Action(name, inputVariables, outputVariable.getType) {

  val getActionCalls = actionCalls
  val getOutputVariable = outputVariable

}

object ComposedAction {

  def apply(name: String, inputVariables: Seq[Variable] = Nil, outputVariable: Option[Variable] = None, actionCalls: Seq[ActionCall]) =
    new ComposedAction(name, inputVariables, outputVariable.orNull, actionCalls)

}