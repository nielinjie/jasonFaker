package xyz.nietongxue.jsonFaker.swagger

import java.text.{DateFormat, SimpleDateFormat}
import java.util.concurrent.TimeUnit

import io.swagger.models.properties.{DateProperty, DateTimeProperty}
import org.json4s.JsonAST.{JString, JValue}
import xyz.nietongxue.jsonFaker.general.{AFaker, Condition, ConditionFinder, Hints}

/**
  * Created by nielinjie on 9/12/16.
  */
object DateCo extends ConditionFinder[DateProperty] {
  override def findCondition(s: DateProperty): Condition = {
    new Condition with AFaker {
      override def fake(hints: Hints): JValue = {
        val format = new SimpleDateFormat("yyyy-MM-dd")
        JString(format.format(faker.date().future(hints.dateDelta, TimeUnit.DAYS)))
      }
    }
  }
}

