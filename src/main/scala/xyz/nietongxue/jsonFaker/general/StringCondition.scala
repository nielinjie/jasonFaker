package xyz.nietongxue.jsonFaker.general

import org.json4s.JsonAST.{JString, JValue}

/**
  * Created by nielinjie on 9/11/16.
  */
case class StringCondition (pattern: Option[String],
                       maxLength: Option[Int],
                       minLength: Option[Int]) extends Condition with AFaker{
  override def fake(hints: Hints): JValue = {
    //TODO max/min length 如何覆盖或者不覆盖pattern
    //TODO 支持format
    val pattern: Option[String] = this.pattern.map({ p =>
      if (p.startsWith("^") && p.endsWith("$"))
        p.substring(1, p.length - 1)
      else
        p
    }
    )
    val min: Option[Int] = this.minLength
    val max: Option[Int] = this.maxLength
    pattern match {
      case Some(p) =>
        JString(faker.regexify(p, min.getOrElse(hints.minStringLengthWithPattern), max.getOrElse(hints.maxStringLengthWithPattern)))
      case None =>
        JString(faker.regexify(hints.randomChar, min.getOrElse(hints.minStringLength), max.getOrElse(hints.maxStringLength)))
    }
  }
}
