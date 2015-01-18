/*
 * Copyright 2015 Michel Vollebregt
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
