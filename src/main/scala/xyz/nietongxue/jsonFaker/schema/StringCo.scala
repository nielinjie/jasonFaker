package xyz.nietongxue.jsonFaker.schema

import org.everit.json.schema.StringSchema
import xyz.nietongxue.jsonFaker.general.{Condition, ConditionFinder, StringCondition}

/**
  * Created by nielinjie on 9/11/16.
  */
object StringCo extends ConditionFinder[StringSchema]{
  override def findCondition(s: StringSchema): Condition = {
    StringCondition(
      Option(s.getPattern).map(_.toString),
      Option(s.getMaxLength).map(_.intValue()),
      Option(s.getMinLength).map(_.intValue())
    )
  }
}
