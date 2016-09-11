package xyz.nietongxue.jsonFaker.general
import org.json4s._

/**
  * Created by nielinjie on 9/11/16.
  */
case class NumberCondition (max: Option[Double], min: Option[Double],
                           isExclusiveMaximum: Option[Boolean],
                           isExclusiveMinimum: Option[Boolean], typ: NumberType.NumberType) extends Condition with AFaker{
  override def fake( hints: Hints): JValue = {
    val MaxNumberOfDecimals: Int = 10
    val max: Double = this.max.getOrElse(hints.maxInt)
    val min: Double = this.min.getOrElse(hints.minInt)
    val maxD: Int = if (isExclusiveMaximum.getOrElse(false)) max.intValue() else max.intValue() + 1
    val minD: Int = if (isExclusiveMinimum.getOrElse(false)) min.intValue() + 1 else min.intValue()
    typ match {
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
  
}

object NumberType extends Enumeration {
  type NumberType = Value
  val IntegerType, LongType, DoubleType, FloatType = Value
}