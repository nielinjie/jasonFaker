package xyz.nietongxue.jsonFaker.schema

import org.everit.json.schema.NumberSchema
import xyz.nietongxue.jsonFaker.general.{Condition, ConditionFinder, NumberCondition, NumberType}

/**
  * Created by nielinjie on 9/11/16.
  */
object NumberCo extends ConditionFinder[NumberSchema] {
  override def findCondition(s: NumberSchema): Condition = {
    NumberCondition(
      Option[Number](s.getMaximum).map(_.doubleValue()),
      Option[Number](s.getMinimum).map(_.doubleValue()),
      Option(s.isExclusiveMaximum),
      Option(s.isExclusiveMinimum),
      s.requiresInteger() match {
        case true => NumberType.IntegerType
        case false => NumberType.DoubleType
      }
    )
  }
}
