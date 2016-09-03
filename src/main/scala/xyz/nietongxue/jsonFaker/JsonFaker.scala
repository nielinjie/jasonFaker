package xyz.nietongxue.jsonFaker

import com.fasterxml.jackson.databind.JsonNode
import com.github.javafaker.Faker
import io.swagger.models.properties.{AbstractNumericProperty, Property}
import org.everit.json.schema._
import org.json4s.JsonAST.{JBool, JNull, JValue}
import org.json4s.jackson.JsonMethods._
/**
  * Created by nielinjie on 8/30/16.
  */
object JsonFaker {
  def fake(schema:Schema):JsonNode ={
    asJsonNode(fakeJValue(schema))
  }
  import Fakers._
  def fakeJValue(schema: Schema):JValue={
    val hints=Hints()
    schema match {
      case n:NumberSchema => n.fake(hints)
      case s:StringSchema=> s.fake(hints)
      case o:ObjectSchema=>o.fake()
      case a:ArraySchema => a.fake(hints)
      case e:EmptySchema =>  fakeEmpty
      case nl:NullSchema=> JNull
      case c:CombinedSchema => c.fake()
      case b:BooleanSchema => fakeBoolean
      case em:EnumSchema=> em.fake(hints)
    }
  }

  def fakeJValue(prop:Property):JValue= {
    val hints=Hints()
    prop match{
      case n:AbstractNumericProperty => n.fake(hints)
      case _ => ???
    }
  }
}
object Fakers extends NumberFaker
  with StringFaker with ObjectFaker
  with ArrayFaker with CombinedFaker with EnumFaker with AFaker{
  def fakeEmpty :JValue={
    ???
  }
  def fakeBoolean: JValue= {
    JBool(faker.bool().bool())
  }

}
