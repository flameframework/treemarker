package com.github.flameframework.util

/**
 * Created by michel on 21-11-14.
 */
object AutoCloseableUtils {

  def tryWithResource[A <: AutoCloseable, B](closable : A)(f : A => B) : B = {
    try {
      f(closable)
    } finally {
      closable.close()
    }
  }

//  def tryWithResources[A1 <: AutoCloseable, A2 <: AutoCloseable, B](closable1 : A1, closable2 : A2)(f : (A1, A2) => B) : B = {
//    try {
//      try {
//        f(closable1, closable2)
//      }
//      finally {
//        closable1.close()
//      }
//    }
//    finally {
//      closable2.close()
//    }
//  }
}
