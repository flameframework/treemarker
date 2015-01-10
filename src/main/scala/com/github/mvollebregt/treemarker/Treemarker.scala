package com.github.mvollebregt.treemarker

import java.io.File

import com.github.mvollebregt.flame.compiler.action.InteractionModel
import com.github.mvollebregt.treemarker.parser.{TokenizingWriter, TreeMarkerTokenProcessor}
import com.github.mvollebregt.util.AutoCloseableUtils._
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