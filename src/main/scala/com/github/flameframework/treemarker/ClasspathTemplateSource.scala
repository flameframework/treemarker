package com.github.flameframework.treemarker

import com.github.flameframework.util.PathScanner._
import freemarker.template._

/**
 * Created by michel on 21-11-14.
 */
class ClasspathTemplateSource(classpathPrefix : String, objectWrapper : Option[ObjectWrapper] = None) extends TemplateSource {

  private val cfg = new Configuration(TreeMarker.version)
  cfg.setClassForTemplateLoading(getClass, "/" + classpathPrefix)
  cfg.setDefaultEncoding("UTF-8")
  cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER)
  objectWrapper.foreach ( cfg.setObjectWrapper(_))

  def listTemplates : Iterable[String] =
    classpathEntries.filter(_.startsWith(classpathPrefix)).map(_.substring(classpathPrefix.size))

  def getTemplate(template: String) : Template = cfg.getTemplate(template)

}
