package com.github.mvollebregt.flame.compiler.action

import com.github.mvollebregt.flame.compiler.domain.{Variable, Type}

import scala.collection.JavaConversions._

/**
 * Created by michel on 13-12-14.
 */
class Action(name: String,
                      inputVariables: java.lang.Iterable[Variable] = Nil,
                      outputType: Type = null) {

  def getName = name
  def getInputVariables = inputVariables
  def getOutputType = outputType

}

object Action {

  def apply(name: String, inputVariables: Seq[Variable] = Nil, outputType: Option[Type] = None) =
    new Action(name, inputVariables, outputType.orNull)

}