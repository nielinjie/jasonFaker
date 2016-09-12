package xyz.nietongxue.jsonFaker.swagger

import io.swagger.models.properties.StringProperty
import xyz.nietongxue.jsonFaker.general.{Condition, ConditionFinder, StringCondition}

/**
  * Created by nielinjie on 9/12/16.
  */
object StringCo extends ConditionFinder[StringProperty]{
  override def findCondition(s: StringProperty): Condition = {
    StringCondition(
      Option(s.getPattern),
      Option(s.getMaxLength).map(_.intValue()),
      Option(s.getMinLength).map(_.intValue())
    )
  }
}
