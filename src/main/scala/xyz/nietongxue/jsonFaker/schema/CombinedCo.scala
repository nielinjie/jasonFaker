package xyz.nietongxue.jsonFaker.schema

import org.everit.json.schema.CombinedSchema
import xyz.nietongxue.jsonFaker.general.{CombinedCondition, Condition, ConditionFinder}

import scala.collection.JavaConverters._

/**
  * Created by nielinjie on 9/11/16.
  */
object CombinedCo extends ConditionFinder[CombinedSchema] {
  implicit val sf = SchemaFakeIt

  override def findCondition(s: CombinedSchema): Condition = {
    CombinedCondition(s.getSubschemas.asScala.toList, s.getCriterion match {
      case CombinedSchema.ALL_CRITERION => "ALL"
      case CombinedSchema.ANY_CRITERION => "ANY"
      case CombinedSchema.ONE_CRITERION => "ONE"
    })
  }
}
