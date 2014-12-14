package com.github.mvollebregt.flame.compiler.action

import com.github.mvollebregt.flame.compiler.domain.{Variable, Type}

import scala.collection.JavaConversions._

/**
 * Created by michel on 13-12-14.
 */
class Action(name: String,
                      inputs: java.lang.Iterable[Variable] = Nil,
                      output: Type = null) {

  def getName = name
  def getInputs = inputs
  def getOutput = output

}

object Action {

  def apply(name: String, inputs: Seq[Variable] = Nil, output: Option[Type] = None) =
    new Action(name, inputs, output.getOrElse(null))

}