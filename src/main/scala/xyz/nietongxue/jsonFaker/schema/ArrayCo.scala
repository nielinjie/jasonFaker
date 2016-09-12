package xyz.nietongxue.jsonFaker.schema

import org.everit.json.schema.{ArraySchema, Schema}
import xyz.nietongxue.jsonFaker.general.{ArrayCondition, Condition, ConditionFinder}

import scala.collection.JavaConversions._

/**
  * Created by nielinjie on 9/11/16.
  */
object ArrayCo extends ConditionFinder[ArraySchema] {
  implicit val sf = SchemaFakeIt

  override def findCondition(s: ArraySchema): Condition = {
    ArrayCondition[Schema](
      Option(s.getItemSchemas).map(_.toList), Option(s.getAllItemSchema),
      Option(s.getMaxItems), Option(s.getMinItems)
    )
  }
}
