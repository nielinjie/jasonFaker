package xyz.nietongxue.jsonFaker

import io.swagger.models.properties.StringProperty
import org.everit.json.schema.StringSchema
import org.json4s.JsonAST.{JString, JValue}
import xyz.nietongxue.jsonFaker.general.{AFaker, Hints}

/**
  * Created by nielinjie on 8/31/16.
  */
trait StringFaker {
  self: AFaker =>

  case class StringCondition(pattern: Option[String],
                             maxLength: Option[Int],
                             minLength: Option[Int])

  implicit def s2c(s: StringSchema): StringCondition = {
    StringCondition(
      Option(s.getPattern).map(_.toString),
      Option(s.getMaxLength).map(_.intValue()),
      Option(s.getMinLength).map(_.intValue())
    )
  }
  implicit def sp2c(s: StringProperty): StringCondition = {
    StringCondition(
      Option(s.getPattern),
      Option(s.getMaxLength).map(_.intValue()),
      Option(s.getMinLength).map(_.intValue())
    )
  }

  def fake(hints: Hints, c: StringCondition): JValue = {
    //TODO max/min length 如何覆盖或者不覆盖pattern
    //TODO 支持format
    val pattern: Option[String] = c.pattern.map({ p =>
      if (p.startsWith("^") && p.endsWith("$"))
        p.substring(1, p.length - 1)
      else
        p
    }
    )
    val min: Option[Int] = c.minLength
    val max: Option[Int] = c.maxLength
    pattern match {
      case Some(p) =>
        JString(faker.regexify(p, min.getOrElse(hints.minStringLengthWithPattern), max.getOrElse(hints.maxStringLengthWithPattern)))
      case None =>
        JString(faker.regexify(hints.randomChar, min.getOrElse(hints.minStringLength), max.getOrElse(hints.maxStringLength)))
    }
  }

  implicit class StringF(s: StringSchema) {
    def fake(hints: Hints) = {
      StringFaker.this.fake(hints, s)
    }
  }
  implicit class SF(s:StringProperty){
    def fake(hints: Hints)={
      StringFaker.this.fake(hints,s)
    }
  }

}
