package com.github.mvollebregt.flame.compiler

import com.github.mvollebregt.util.PathScanner._
import com.github.mvollebregt.util.StringUtils._
import freemarker.template.{Template, TemplateExceptionHandler, Configuration}

/**
 * Created by michel on 21-11-14.
 */
class ClasspathTemplateReader {

  val cfg = new Configuration(Configuration.VERSION_2_3_21)
  cfg.setClassForTemplateLoading(getClass, "/")
  cfg.setDefaultEncoding("UTF-8")
  cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER)

  private def classpathPattern(platform : String) = s"flame/$platform/.*".r

  def listTemplates(platform: String) : Iterable[String] = {
    val pattern = classpathPattern(platform)
    classpathEntries.filter(_.matches(pattern))
  }

  def getTemplate(template: String) : Template = cfg.getTemplate(template)

}
