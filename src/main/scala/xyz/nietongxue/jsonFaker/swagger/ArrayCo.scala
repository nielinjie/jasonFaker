package xyz.nietongxue.jsonFaker.swagger

import io.swagger.models.properties.{ArrayProperty, Property}
import xyz.nietongxue.jsonFaker.general.{ArrayCondition, Condition, ConditionFinder}

/**
  * Created by nielinjie on 9/12/16.
  */
object ArrayCo extends ConditionFinder[ArrayProperty]{
  implicit val fi = SwaggerFakeIt
  override def findCondition(s: ArrayProperty): Condition = {
    ArrayCondition[Property](
      None, Option(s.getItems),
      Option(s.getMaxItems), Option(s.getMinItems)
    )
  }
}
