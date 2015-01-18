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

import java.io.File

import com.github.flameframework.compiler.action.InteractionModel
import com.github.flameframework.treemarker.parser.{TokenizingWriter, TreeMarkerTokenProcessor}
import com.github.flameframework.util.AutoCloseableUtils._
import freemarker.template.{Configuration, Version, ObjectWrapper}

/**
 * Created by michel on 10-01-15.
 */
class TreeMarker(templateSource: TemplateSource, templateTarget: TemplateTarget) {

  def generate(dataModel: Any) = {
    for (templateName <- templateSource.listTemplates) {
      val template = templateSource.getTemplate(templateName)
      val writer = new TokenizingWriter(new TreeMarkerTokenProcessor(templateName, templateTarget))
      tryWithResource(writer) { writer =>
        template.process(dataModel, writer)
      }
    }
  }

}

object TreeMarker {

  val version: Version = Configuration.VERSION_2_3_21

  def generate(classpathPrefix: String,
               target: String,
               dataModel: InteractionModel,
               objectWrapper: Option[ObjectWrapper] = None): Unit = {
    
    val templateSource = new ClasspathTemplateSource(classpathPrefix, objectWrapper)
    val templateTarget = new FileSystemTemplateTarget(new File(target))
    new TreeMarker(templateSource, templateTarget).generate(dataModel)

  }

}