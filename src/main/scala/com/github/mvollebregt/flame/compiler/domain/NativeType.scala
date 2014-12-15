package com.github.mvollebregt.flame.compiler.domain

/**
 * Created by michel on 30-11-14.
 */
trait NativeType extends Type

case object StringType extends NativeType
case object IntegerType extends NativeType
