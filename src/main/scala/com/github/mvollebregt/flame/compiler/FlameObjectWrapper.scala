package com.github.mvollebregt.flame.compiler

import com.github.mvollebregt.flame.compiler.domain._
import freemarker.ext.beans.BeanModel
import freemarker.template._

/**
 * Created by michel on 30-11-14.
 */
// TODO : this is just a proof of concept for a custom object wrapper
class FlameObjectWrapper(version: Version) extends DefaultObjectWrapper(version) {

  override def wrap(obj: scala.Any): TemplateModel =
    obj match {
      case (typeObj: Type) => wrap(typeObj, asString(typeObj))
      case (value: Value) => wrap(value, asString(value))
      case _ => super.wrap(obj)
    }

  private def asString(typeObj : Type) : String = typeObj match {
    case (IntegerType) => "NSInteger"
    case (StringType) => "NSString"
    case (domainClass: DomainClass) => s"${domainClass.getName}"
    case (listType: ListType) => "NSMutableArray"
  }

  private def asString(value: Value) : String = value match {
    case (variable: Variable) => variable.getName
    case (property : PropertyValue) => s"${asString(property.getObject)}.${property.getProperty.getName}"
  }

  private def wrap(obj: Object, valueAsString: String) = new BeanModel(obj, this) with TemplateScalarModel {
    override def getAsString: String = valueAsString
  }

}
