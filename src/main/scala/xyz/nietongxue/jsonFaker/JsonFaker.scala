package xyz.nietongxue.jsonFaker

import com.fasterxml.jackson.databind.JsonNode
import io.swagger.models.properties._
import org.everit.json.schema._
import org.json4s.JsonAST.{JBool, JNull, JValue}
import org.json4s.jackson.JsonMethods._
import xyz.nietongxue.jsonFaker.general.{AFaker, Hints}

/**
  * Created by nielinjie on 8/30/16.
  */
object JsonFaker {
  def fake(schema: Schema): JsonNode = {
    asJsonNode(fakeJValue(schema))
  }
  def fake(property: Property):JsonNode={
    asJsonNode(fakeJValue(property))
  }

  import Fakers._

  def fakeJValue(schema: Schema): JValue = {
    val hints = Hints()
    schema match {
      case n: NumberSchema => n.fake(hints)
      case s: StringSchema => s.fake(hints)
      case o: ObjectSchema => o.fake()
      case a: ArraySchema => a.fake(hints)
      case e: EmptySchema => fakeEmpty
      case nl: NullSchema => JNull
      case c: CombinedSchema => c.fake()
      case b: BooleanSchema => fakeBoolean
      case em: EnumSchema => em.fake(hints)
    }
  }

  def fakeJValue(prop: Property): JValue = {
    val hints = Hints()
    prop match {
      case n: AbstractNumericProperty => n.fake(hints)
      case s: StringProperty => s.fake(hints)
      case a: ArrayProperty => a.fake(hints)
      case o: ObjectProperty => o.fake()
      case b: BooleanProperty => fakeBoolean
//      case da: DateProperty =>
      //TODO more properties
    }
  }
}

object Fakers extends NumberFaker
  with StringFaker with ObjectFaker
  with ArrayFaker with CombinedFaker with EnumFaker with AFaker {
  def fakeEmpty: JValue = {
    ???
  }

  def fakeBoolean: JValue = {
    JBool(faker.bool().bool())
  }

}
