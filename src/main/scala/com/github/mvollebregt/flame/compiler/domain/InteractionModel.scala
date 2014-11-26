package com.github.mvollebregt.flame.compiler.domain

import scala.collection.JavaConversions._

/**
 * Created by michel on 16-11-14.
 */
class InteractionModel (domainClasses : java.lang.Iterable[DomainClass]) {

  def getDomainClasses = domainClasses

}

object InteractionModel {

  def apply(domainClasses: java.lang.Iterable[DomainClass]) = new InteractionModel(domainClasses)

  def apply(domainClasses: Seq[DomainClass]) = new InteractionModel(domainClasses)

}