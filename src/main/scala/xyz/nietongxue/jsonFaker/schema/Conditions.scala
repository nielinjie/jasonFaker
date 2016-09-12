package xyz.nietongxue.jsonFaker.schema

import org.everit.json.schema._
import xyz.nietongxue.jsonFaker.general.{CombinedCondition, ConditionFinder}

/**
  * Created by nielinjie on 9/11/16.
  */
object Conditions {
  implicit val ncf: ConditionFinder[NumberSchema] = NumberCo
  implicit val scf:ConditionFinder[StringSchema]= StringCo
  implicit val ocf:ConditionFinder[ObjectSchema]= ObjectCo
  implicit val acf:ConditionFinder[ArraySchema] = ArrayCo
  implicit  val ccf:ConditionFinder[CombinedSchema]=CombinedCo
  implicit val ecf: ConditionFinder[EnumSchema] = EnumCo
}
