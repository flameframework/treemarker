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
      case (nativeTypeIdentifier: NativeTypeIdentifier) => wrap(nativeTypeIdentifier)
      case (domainClassIdentifier: DomainClassIdentifier) => wrap(domainClassIdentifier)
      case (ListTypeIdentifier(listType : DomainClass)) => wrap(listType)
      case _ => super.wrap(obj)
    }

  private def wrap(objectTypeIdentifier: NativeTypeIdentifier) = new SimpleScalar(s"NS${objectTypeIdentifier.name}")

  private def wrap(domainClassIdentifier: DomainClassIdentifier) = new SimpleScalar(domainClassIdentifier.name)

  private def wrap(listType: DomainClass) = new SimpleScalar(s"[NS${listType.getName.name}]")

}
