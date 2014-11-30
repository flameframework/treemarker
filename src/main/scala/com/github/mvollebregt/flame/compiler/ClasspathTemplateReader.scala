package com.github.mvollebregt.flame.compiler

import com.github.mvollebregt.util.PathScanner._
import com.github.mvollebregt.util.StringUtils._
import freemarker.template.{Version, Template, TemplateExceptionHandler, Configuration}

/**
 * Created by michel on 21-11-14.
 */
class ClasspathTemplateReader(platform : String) {

  private val classpathPrefix = s"flame/$platform/"

  private val version: Version = Configuration.VERSION_2_3_21

  private val cfg = new Configuration(version)
  cfg.setClassForTemplateLoading(getClass, "/" + classpathPrefix)
  cfg.setDefaultEncoding("UTF-8")
  cfg.setObjectWrapper(new FlameObjectWrapper(version))
  cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER)

  def listTemplates : Iterable[String] =
    classpathEntries.filter(_.startsWith(classpathPrefix)).map(_.substring(classpathPrefix.size))

  def getTemplate(template: String) : Template = cfg.getTemplate(template)

}
