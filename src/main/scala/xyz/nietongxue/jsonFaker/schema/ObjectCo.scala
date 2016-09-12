package xyz.nietongxue.jsonFaker.schema

import org.everit.json.schema.{ObjectSchema, Schema}
import xyz.nietongxue.jsonFaker.general.{Condition, ConditionFinder, ObjectCondition}

import scala.collection.JavaConversions._

/**
  * Created by nielinjie on 9/11/16.
  */
object ObjectCo extends ConditionFinder[ObjectSchema]{
  implicit val sf = SchemaFakeIt
  override def findCondition(s: ObjectSchema): Condition = {
    ObjectCondition[Schema](s.getPropertySchemas.toMap)
  }
}
