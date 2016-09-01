package xyz.nietongxue.jsonFaker

import com.fasterxml.jackson.databind.JsonNode
import com.github.javafaker.Faker
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

}
object Fakers extends NumberFaker
  with StringFaker with ObjectFaker
  with ArrayFaker with CombinedFaker with EnumFaker{
  val faker = new Faker()
  def fakeEmpty :JValue={
    ???
  }
  def fakeBoolean: JValue= {
    JBool(faker.bool().bool())
  }

}
