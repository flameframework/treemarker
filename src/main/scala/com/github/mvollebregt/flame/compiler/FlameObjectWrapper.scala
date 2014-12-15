package com.github.mvollebregt.flame.compiler

import com.github.mvollebregt.flame.compiler.domain._
import freemarker.ext.beans.{BeanModel, BeansWrapper}

import freemarker.template._

/**
 * Created by michel on 30-11-14.
 */
// TODO : this is just a proof of concept for a custom object wrapper
class FlameObjectWrapper(version: Version) extends DefaultObjectWrapper(version) {

  override def wrap(obj: scala.Any): TemplateModel =
    obj match {
      case (typeObj: Type) => wrap(typeObj, asString(typeObj))
      case _ => super.wrap(obj)
    }

  private def asString(typeObj : Type) : String = typeObj match {
    case (nativeType: NativeType) => s"NS$nativeType".dropRight(4)
    case (domainClass: DomainClass) => domainClass.getName
    case (listType: ListType) => s"[${asString(listType.itemType)}]"
  }

  private def wrap(obj: Object, valueAsString: String) = new BeanModel(obj, this) with TemplateScalarModel {
    override def getAsString: String = valueAsString
  }

}
