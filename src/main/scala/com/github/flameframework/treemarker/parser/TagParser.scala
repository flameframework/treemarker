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

package com.github.flameframework.treemarker.parser

/**
 * Created by michel on 09-01-15.
 */
object TagParser {

  private val splitEx = "(?:[^\\s=]+)(?:\\s*=\\s*(?:(?:\"[^\"]*\")|(?:'[^\']*')|(?:[^\\s]*)))?".r

  private def withoutQuotes(s: String) : String = {
    if ((s.startsWith("\'") && s.endsWith("\'")) || (s.startsWith("\"") && s.endsWith("\""))) {
      s.substring(1, s.length - 1)
    } else s
  }

  def parse(content: String) : (String, Map[String, Option[String]]) = {

    val elements : Seq[String] = (splitEx.findAllMatchIn(content).map(_.matched)).toSeq

    (elements.head, elements.tail.map(attribute => {
      attribute.indexOf("=") match {
        case -1 => (attribute.trim, None)
        case x => (attribute.substring(0, x).trim, (Some(withoutQuotes(attribute.substring(x + 1).trim))))
      }}).toMap)

  }

}
