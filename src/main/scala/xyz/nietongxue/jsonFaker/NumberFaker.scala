package xyz.nietongxue.jsonFaker



import io.swagger.models.properties.{AbstractNumericProperty, DoubleProperty, IntegerProperty, LongProperty}
import org.everit.json.schema.NumberSchema
import org.json4s.JsonAST.JValue
import org.json4s._
import xyz.nietongxue.jsonFaker.general.{AFaker, Hints}

import scala.language.implicitConversions

/**
  * Created by nielinjie on 8/30/16.
  */
trait NumberFaker {
  self: AFaker =>

  object NumberType extends Enumeration {
    type NumberType = Value
    val IntegerType, LongType, DoubleType, FloatType = Value
  }

  case class NumberCondition(max: Option[Double], min: Option[Double],
                             isExclusiveMaximum: Option[Boolean],
                             isExclusiveMinimum: Option[Boolean], typ: NumberType.NumberType) {
  }

  implicit def Schema2C(n: NumberSchema): NumberCondition= {
    NumberCondition(
      Option[Number](n.getMaximum).map(_.doubleValue()),
      Option[Number](n.getMinimum).map(_.doubleValue()),
      Option(n.isExclusiveMaximum),
      Option(n.isExclusiveMinimum),
      n.requiresInteger() match {
        case true => NumberType.IntegerType
        case false => NumberType.DoubleType
      }
    )
  }

  implicit def Prop2C(n: AbstractNumericProperty): NumberCondition = {
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


  def fake(hints: Hints, c: NumberCondition) = {
    val MaxNumberOfDecimals: Int = 10
    val max: Double = c.max.getOrElse(hints.maxInt)
    val min: Double = c.min.getOrElse(hints.minInt)
    val maxD: Int = if (c.isExclusiveMaximum.getOrElse(false)) max.intValue() else max.intValue() + 1
    val minD: Int = if (c.isExclusiveMinimum.getOrElse(false)) min.intValue() + 1 else min.intValue()
    c.typ match {
      //TODO 侧重生成边界情况?
      case NumberType.IntegerType => {
        JInt(faker.number().numberBetween(minD, maxD))
      }
      case NumberType.LongType => {
        JLong(faker.number().numberBetween(minD, maxD))
      }
      case NumberType.DoubleType =>
        JDouble(faker.number().randomDouble(MaxNumberOfDecimals, min.intValue(), max.intValue()))
      case NumberType.FloatType =>
        JDouble(faker.number().randomDouble(MaxNumberOfDecimals, min.intValue(), max.intValue()))
    }
  }

  implicit class NumberF(n: NumberSchema) {

    def fake(hints: Hints): JValue = {
      if (n.getMultipleOf != null) throw new NotImplementedError()
      NumberFaker.this.fake(hints, n)
    }
  }

  implicit class NumberFakerP(p: AbstractNumericProperty) {
    def fake(hints: Hints): JValue = {
      NumberFaker.this.fake(hints, p)
    }
  }

}
