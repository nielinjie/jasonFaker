package xyz.nietongxue.jsonFaker.swagger

import io.swagger.models.properties.{ObjectProperty, Property}
import xyz.nietongxue.jsonFaker.general.{Condition, ConditionFinder, ObjectCondition}

import scala.collection.JavaConversions._

/**
  * Created by nielinjie on 9/12/16.
  */
object ObjectCo extends ConditionFinder[ObjectProperty]{
  implicit val fi = SwaggerFakeIt
  override def findCondition(s: ObjectProperty): Condition = {
    ObjectCondition[Property](s.getProperties.toMap)
  }
}
