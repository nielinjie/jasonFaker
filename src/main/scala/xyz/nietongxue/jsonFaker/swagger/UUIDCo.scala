package xyz.nietongxue.jsonFaker.swagger

import java.util.UUID

import io.swagger.models.properties.UUIDProperty
import org.json4s.JsonAST.{JString, JValue}
import xyz.nietongxue.jsonFaker.general.{Condition, ConditionFinder, Hints}

/**
  * Created by nielinjie on 9/12/16.
  */
object UUIDCo extends ConditionFinder[UUIDProperty] {
  override def findCondition(s: UUIDProperty): Condition = {
    new Condition {
      override def fake(hints: Hints): JValue = JString(UUID.randomUUID().toString)
    }
  }
}
