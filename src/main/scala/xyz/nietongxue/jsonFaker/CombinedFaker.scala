package xyz.nietongxue.jsonFaker

import java.util

import org.everit.json.schema.{CombinedSchema, Schema}
import org.json4s.JsonAST.JValue

import scala.collection.JavaConversions._

/**
  * Created by nielinjie on 8/31/16.
  */
trait CombinedFaker {
  self:AFaker=>

  implicit class CombinedFaker(c: CombinedSchema) {

    def fake(): JValue = {
      val subSchemas: util.Collection[Schema] = c.getSubschemas
      val random: Int = faker.number().numberBetween(0, subSchemas.size())

      c.getCriterion match {
        //TODO All的情况非常少见吧?
        case CombinedSchema.ALL_CRITERION => ???
        case CombinedSchema.ANY_CRITERION =>
          JsonFaker.fakeJValue(subSchemas.toList.get(random))
        //TODO One的情况比较少见吧?
        case CombinedSchema.ONE_CRITERION => ???
      }
    }
  }

}
