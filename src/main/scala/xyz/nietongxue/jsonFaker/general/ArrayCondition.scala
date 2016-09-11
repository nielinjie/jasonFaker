package xyz.nietongxue.jsonFaker.general

import org.everit.json.schema.Schema
import org.json4s.JsonAST.{JArray, JValue}
import xyz.nietongxue.jsonFaker.JsonFaker
import xyz.nietongxue.jsonFaker.schema.SchemaFakeIt

/**
  * Created by nielinjie on 9/11/16.
  */
case class ArrayCondition[SCHEMA: FakeIt](items: Option[List[SCHEMA]],
                                          allItems: Option[SCHEMA], maxItems: Option[Integer], minItems: Option[Integer]) extends Condition with AFaker {
  implicit val sf = SchemaFakeIt

  override def fake(hints: Hints): JValue = {
    (this.items, this.allItems) match {
      case (Some(its), None) => {
        //TODO additionalItem support
        JArray(its.map({
          s: SCHEMA => JsonFake.fake(s)
        }))
      }

      case (None, Some(allIts)) => {
        //TODO unique item support
        val max: Integer = this.maxItems.getOrElse(hints.maxItems)
        val min: Integer = this.minItems.getOrElse(hints.minItems)
        val count = faker.number().numberBetween(min.intValue(), max.intValue() + 1)
        JArray(Range(1, count + 1).map(r =>
          JsonFake.fake(allIts)
        ).toList)
      }
      case _ => throw new IllegalArgumentException("ItemSchema or AllItemSchema should be supplied, not both")
    }
  }
}
