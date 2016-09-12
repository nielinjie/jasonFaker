package xyz.nietongxue.jsonFaker.swagger

import io.swagger.models.properties.PasswordProperty
import xyz.nietongxue.jsonFaker.general.{Condition, ConditionFinder, StringCondition}

/**
  * Created by nielinjie on 9/12/16.
  */
object PasswordCo extends ConditionFinder[PasswordProperty]{
  override def findCondition(s: PasswordProperty): Condition = {
    StringCondition(None,Some(8),Some(10))
  }
}
