package com.github.mvollebregt.flame.compiler

import java.io.File

import com.github.mvollebregt.flame.compiler.action.{ComposedAction, Action, InteractionModel}
import com.github.mvollebregt.util.AutoCloseableUtils._
import com.github.mvollebregt.flame.compiler.domain._

/**
 * Created by michel on 21-11-14.
 */
object Generator {

  def generate(platform: String, target: String, interactionModel: InteractionModel): Unit = {

    val templateReader = new ClasspathTemplateReader(platform)
    val templateWriter = new FileSystemTemplateWriter(new File(target))

    for (template <- templateReader.listTemplates) {
      val temp = templateReader.getTemplate(template)
      tryWithResource(new FreemarkerWriterWrapper(template, templateWriter)) { writer =>
        temp.process(interactionModel, writer)
      }
    }
  }

  def main(args: Array[String]) = {

    val mail = DomainClass("Mail", Seq(Variable("from", StringType), Variable("to", StringType), Variable("body", StringType)))
    val inbox = DomainClass("Inbox", Seq(Variable("mails", ListType(mail))))

    val refresh = Action("RefreshInbox", inputs = Seq(Variable("inbox", inbox)))

    generate("swift", "output", InteractionModel(
      Seq(inbox, mail),
      Seq(refresh),
      Seq()
    ))
  }
}
