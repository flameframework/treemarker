package com.github.mvollebregt.flame.compiler.domain

/**
 * Created by michel on 16-11-14.
 */
class InteractionModel (domainClasses : java.util.List[DomainClass]) {

  def getDomainClasses = domainClasses

}

object InteractionModel {

  def apply(domainClasses: java.util.List[DomainClass]) = new InteractionModel(domainClasses)

}