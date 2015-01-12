package com.github.flameframework.util

import scala.util.matching.Regex

/**
 * Created by michel on 21-11-14.
 */
object StringUtils {

  implicit class StringImprovements(s: String) {

    def matches(pattern: Regex) : Boolean =  pattern.pattern.matcher(s).matches

  }

}
