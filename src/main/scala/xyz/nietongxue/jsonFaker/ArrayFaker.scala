package xyz.nietongxue.jsonFaker

import com.github.javafaker.Faker
import org.everit.json.schema.{ArraySchema, Schema}
import org.json4s.JsonAST.{JArray, JValue}

import scala.collection.JavaConversions._


/**
  * Created by nielinjie on 8/31/16.
  */
trait ArrayFaker {

  implicit class ArrayFaker(a: ArraySchema) {
    val faker = new Faker()
    val itemsS: Option[List[Schema]] = Option(a.getItemSchemas).map(_.toList)
    val allItems: Option[Schema] = Option(a.getAllItemSchema)

    def fake(hits:Hints): JValue = {
      (itemsS, allItems) match {
        case (Some(its), _) => {
          //TODO additionalItem support
          JArray(its.map(JsonFaker.fakeJValue))
        }
        case (None, Some(allIts)) => {
          //TODO unique item support
          val max: Integer = Option(a.getMaxItems).getOrElse(hits.maxItems)
          val min: Integer = Option(a.getMinItems).getOrElse(hits.minItems)
          val count = faker.number().numberBetween(min.intValue(), max.intValue()+1)
          JArray(Range(1, count+1).map(r => JsonFaker.fakeJValue(allIts)).toList)
        }
        case _ => throw new IllegalArgumentException("ItemSchema or AllItemSchema should be supplied")
      }
    }
  }

}
