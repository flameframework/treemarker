package com.github.mvollebregt.flame.compiler

import com.github.mvollebregt.util.AutoCloseableUtils._
import com.github.mvollebregt.flame.compiler.domain.{DomainClass, InteractionModel}
import com.github.mvollebregt.util.PathScanner
import freemarker.template.{TemplateExceptionHandler, Configuration}

/**
 * Created by michel on 21-11-14.
 */
object Generator {

  val templateReader = new ClasspathTemplateReader
  val templateWriter = new FileSystemTemplateWriter

  def generate(platform: String, interactionModel: InteractionModel): Unit =
    for (template <- templateReader.listTemplates(platform)) {
        process(template, interactionModel)
    }

  def process(template: String, model: InteractionModel) = {
    val temp = templateReader.getTemplate(template)
    val writer = new FreemarkerWriterWrapper(template, templateWriter)
    temp.process(model, writer)
    writer.close
  }

  def main(args: Array[String]) = {
    generate("swift", InteractionModel(java.util.Arrays.asList(DomainClass("foo"), DomainClass("bar"))))
  }
}
