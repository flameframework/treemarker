package com.github.mvollebregt.flame.compiler.action

import com.github.mvollebregt.flame.compiler.domain.DomainClass

import scala.collection.JavaConversions._

/**
 * Created by michel on 16-11-14.
 */
class InteractionModel (domainClasses : java.lang.Iterable[DomainClass] ,
                        nativeActions: java.lang.Iterable[NativeAction],
                        composedActions : java.lang.Iterable[ComposedAction]) {

  def getDomainClasses = domainClasses
  def getNativeActions = nativeActions
  def getComposedActions = composedActions

}

object InteractionModel {

  def apply(domainClasses: Seq[DomainClass], nativeActions : Seq[NativeAction], composedActions: Seq[ComposedAction]) =
    new InteractionModel(domainClasses, nativeActions, composedActions)

}