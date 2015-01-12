package com.github.mvollebregt.flame.compiler.action

import com.github.mvollebregt.flame.compiler.domain.{Value, Variable}

import scala.collection.JavaConversions._

/**
 * Created by michel on 16-12-14.
 */
class ActionCall(action: Action, inputValues: java.util.List[Value], outputVariable: Variable) {

  val getAction = action
  val getInputValues = inputValues
  val getOutputVariable = outputVariable

}

object ActionCall {

  def apply(action: Action, inputValues: Seq[Value] = Nil, outputVariable : Option[Variable] = None) =
    new ActionCall(action, inputValues, outputVariable.orNull)

}