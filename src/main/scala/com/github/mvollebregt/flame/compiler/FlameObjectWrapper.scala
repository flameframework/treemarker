package com.github.mvollebregt.flame.compiler

import com.github.mvollebregt.flame.compiler.domain._

import freemarker.template._

/**
 * Created by michel on 30-11-14.
 */
// TODO : this is just a proof of concept for a custom object wrapper
class FlameObjectWrapper(version: Version) extends DefaultObjectWrapper(version) {

  override def wrap(obj: scala.Any): TemplateModel =
    obj match {
      case (objectType: NativeType) => wrap(objectType)
      case _ => super.wrap(obj)
    }

  private def wrap(objectType: NativeType) = new SimpleScalar(s"NS${objectType}".dropRight(4))

}
