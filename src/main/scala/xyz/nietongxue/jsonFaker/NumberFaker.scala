package xyz.nietongxue.jsonFaker

import com.github.javafaker.Faker
import org.everit.json.schema.NumberSchema
import org.json4s.JsonAST.JValue
import org.json4s._

/**
  * Created by nielinjie on 8/30/16.
  */
trait NumberFaker {
  implicit class NumberFaker(n:NumberSchema){
    val faker =new Faker()

    val MaxNumberOfDecimals: Int = 10
    def fake(hints:Hints):JValue={
      if(n.getMultipleOf!=null) throw new NotImplementedError()
      val max:Number = Option[Number](n.getMaximum).getOrElse(hints.maxInt)
      val min:Number = Option[Number](n.getMinimum).getOrElse(hints.minInt)
      val maxD:Int = if (n.isExclusiveMaximum) max.intValue() else max.intValue()+1
      val minD:Int = if (n.isExclusiveMinimum) min.intValue()+1 else min.intValue()
      n.requiresInteger() match {
          //TODO 侧重生成边界情况?
        case true=> {
          JInt(faker.number().numberBetween(minD, maxD))
        }
        case false=>
          JDouble(faker.number().randomDouble(MaxNumberOfDecimals,min.intValue(),max.intValue()))
      }
    }
  }
}
