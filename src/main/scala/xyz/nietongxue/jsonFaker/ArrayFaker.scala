package xyz.nietongxue.jsonFaker

import com.github.javafaker.Faker
import io.swagger.models.properties.{ArrayProperty, Property}
import org.everit.json.schema.{ArraySchema, Schema}
import org.json4s.JsonAST.{JArray, JValue}
import xyz.nietongxue.jsonFaker.general.{AFaker, Hints}

import scala.collection.JavaConversions._


/**
  * Created by nielinjie on 8/31/16.
  */
trait ArrayFaker {
  self: AFaker =>

  case class ArrayCondition[T](items: Option[List[T]],
                               allItems: Option[T], maxItems: Option[Integer], minItems: Option[Integer])

  implicit def s2c(s: ArraySchema): ArrayCondition[Schema] = {
    ArrayCondition[Schema](
      Option(s.getItemSchemas.toList), Option(s.getAllItemSchema),
      Option(s.getMaxItems), Option(s.getMinItems)
    )
  }

  implicit def s2c2(s: ArrayProperty): ArrayCondition[Property] = {
    ArrayCondition[Property](
      None, Option(s.getItems),
      Option(s.getMaxItems), Option(s.getMinItems)
    )
  }

  def fake(hits: Hints, c: ArrayCondition[_]): JValue = {
    (c.items, c.allItems) match {
      case (Some(its), None) => {
        //TODO additionalItem support
           JArray(its.map({
             case s: Schema => JsonFaker.fakeJValue(s)
             case p: Property => JsonFaker.fakeJValue(p)
           }))
        }

      case (None, Some(allIts)) => {
        //TODO unique item support
        val max: Integer = c.maxItems.getOrElse(hits.maxItems)
        val min: Integer = c.minItems.getOrElse(hits.minItems)
        val count = faker.number().numberBetween(min.intValue(), max.intValue() + 1)
        JArray(Range(1, count + 1).map(r => {
          allIts match {
            case i: Schema => JsonFaker.fakeJValue(i)
            case i2: Property => JsonFaker.fakeJValue(i2)
          }
        }).toList)
      }
      case _ => throw new IllegalArgumentException("ItemSchema or AllItemSchema should be supplied, not both")
    }
  }


  implicit class ArrayF(a: ArraySchema) {
    def fake(hints: Hints) = {
      ArrayFaker.this.fake(hints, a)
    }


  }

  implicit class ArrayFP(a: ArrayProperty) {
    def fake(hints: Hints) = {
      ArrayFaker.this.fake(hints, a)
    }


  }

}
