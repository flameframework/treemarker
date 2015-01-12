package com.github.flameframework.treemarker

import java.io.Writer

/**
 * Created by michel on 22-11-14.
 */
trait TemplateTarget {

  def getWriterFor(template: String, output: Option[String] = None, overwrite : Boolean = true) : Writer

}
