package com.github.mvollebregt.treemarker

import freemarker.template.Template

/**
 * Created by michel on 10-01-15.
 */
trait TemplateSource {

  def listTemplates : Iterable[String]

  def getTemplate(template: String) : Template

}
