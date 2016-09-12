package xyz.nietongxue.jsonFaker.swagger

import io.swagger.models.properties.{AbstractNumericProperty, DoubleProperty, IntegerProperty, LongProperty}
import xyz.nietongxue.jsonFaker.general.{Condition, ConditionFinder, NumberCondition, NumberType}

/**
  * Created by nielinjie on 9/12/16.
  */
object NumberCo extends ConditionFinder[AbstractNumericProperty] {
  override def findCondition(n: AbstractNumericProperty): Condition = {
    NumberCondition(
      Option[Number](n.getMaximum).map(_.doubleValue()),
      Option[Number](n.getMinimum).map(_.doubleValue()),
      Option(n.getExclusiveMaximum),
      Option(n.getExclusiveMinimum),
      n match {
        case _: IntegerProperty => NumberType.IntegerType
        case _: LongProperty => NumberType.LongType
        case _: DoubleProperty => NumberType.DoubleType
        case _ => NumberType.FloatType
      }
    )
  }
}
