package xyz.nietongxue.jsonFaker.swagger

import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

import io.swagger.models.properties.DateTimeProperty
import org.json4s.JValue
import org.json4s.JsonAST.JString
import xyz.nietongxue.jsonFaker.general.{AFaker, Condition, ConditionFinder, Hints}

/**
  * Created by nielinjie on 9/12/16.
  */
object DateTimeCo extends ConditionFinder[DateTimeProperty]{
  override def findCondition(s: DateTimeProperty): Condition = {
    new Condition with AFaker {
      override def fake(hints: Hints): JValue = {
        val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        JString(format.format(faker.date().future(hints.dateDelta, TimeUnit.DAYS)))
      }
    }
  }
}
