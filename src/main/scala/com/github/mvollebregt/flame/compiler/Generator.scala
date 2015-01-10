package com.github.mvollebregt.flame.compiler

import com.github.mvollebregt.flame.compiler.action.{Action, ActionCall, ComposedAction, InteractionModel}
import com.github.mvollebregt.flame.compiler.domain._
import com.github.mvollebregt.treemarker.TreeMarker

/**
 * Created by michel on 21-11-14.
 */
object Generator {

  def generate(platform: String, target: String, interactionModel: InteractionModel): Unit = {
    val objectWrapper = new FlameObjectWrapper(TreeMarker.version)
    TreeMarker.generate(s"flame/$platform/", target, interactionModel, Some(objectWrapper))
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

    generate("swift", "../flame-ios/flame-ios", InteractionModel(
      Seq(Inbox, Mail),
      Seq(refresh, view),
      Seq(open)
    ))
  }
}
