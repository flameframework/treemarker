package com.github.mvollebregt.flame.compiler

import java.io.File

import com.github.mvollebregt.flame.compiler.action.{ActionCall, ComposedAction, Action, InteractionModel}
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

    val from = Variable("from", StringType)
    val to = Variable("to", StringType)
    val body = Variable("body", StringType)
    val Mail = DomainClass("Mail", Seq(from, to, body))
    val mail = Variable("mail", Mail)

    val mails = Variable("mails", ListType(Mail))
    val Inbox = DomainClass("Inbox", Seq(mails))
    val inbox = Variable("inbox", Inbox)

    val obj = Variable("object", ListType(Mail))

    val refresh = Action("RefreshInbox", inputVariables = Seq(inbox), outputType = Some(IntegerType))
    val view = Action("View", inputVariables = Seq(obj), outputType = Some(IntegerType))

    val open = ComposedAction("OpenInbox", inputVariables = Seq(inbox),
      outputVariable = Some(Variable("newItemCount", IntegerType)),
      actionCalls = Seq(
      ActionCall(refresh, Seq(inbox)),
      ActionCall(view, Seq(PropertyValue(inbox, mails)), Some(Variable("newItemCount", IntegerType)))
    ))

    generate("swift", "output/flameswift/flameswift", InteractionModel(
      Seq(Inbox, Mail),
      Seq(refresh, view),
      Seq(open)
    ))
  }
}
