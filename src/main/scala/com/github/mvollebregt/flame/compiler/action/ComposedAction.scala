package com.github.mvollebregt.flame.compiler.action

import com.github.mvollebregt.flame.compiler.domain.{Type, Variable}

import scala.collection.JavaConversions._

/**
 * Created by michel on 13-12-14.
 */
class ComposedAction(name: String,
                      inputs: java.lang.Iterable[Variable],
                      output: Type,
                      actions: java.lang.Iterable[Action]) extends Action(name, inputs, output) {

  def getActions = actions

}

object ComposedAction {

  def apply(name: String, inputs: Seq[Variable] = Nil, output: Option[Type] = None, actions: Seq[Action]) =
    new ComposedAction(name, inputs, output.orNull, actions)

}