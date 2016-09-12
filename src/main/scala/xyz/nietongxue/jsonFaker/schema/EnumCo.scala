package xyz.nietongxue.jsonFaker.schema

import java.util

import org.everit.json.schema.EnumSchema
import org.json4s.{DefaultFormats, Extraction, Formats}
import org.json4s.JsonAST.JValue
import xyz.nietongxue.jsonFaker.general.{AFaker, Condition, ConditionFinder, Hints}

import scala.collection.JavaConversions._


/**
  * Created by nielinjie on 9/12/16.
  */
object EnumCo extends ConditionFinder[EnumSchema]{
  override def findCondition(s: EnumSchema): Condition = {
    new Condition with AFaker{
      override def fake(hints: Hints): JValue = {
        implicit val formats: Formats = DefaultFormats
        val values: util.Set[AnyRef] = s.getPossibleValues
        val data = values.toList.get(faker.number().numberBetween(0,values.size))
        Extraction.decompose(data)
      }
    }
  }
}
