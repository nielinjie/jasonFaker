package xyz.nietongxue.jsonFaker

import org.everit.json.schema.loader.SchemaLoader
import org.everit.json.schema.{NumberSchema, Schema}
import org.json.{JSONArray, JSONObject}
import org.json4s.JsonDSL._
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.specs2.mutable._
import xyz.nietongxue.jsonFaker.JsonFaker


/**
  * Sample specification.
  *
  * This specification can be executed with: scala -cp <your classpath=""> ${package}.SpecsTest
  * Or using maven: mvn test
  *
  * For more information on how to write or run specifications, please visit:
  * http://etorreborre.github.com/specs2/guide/org.specs2.guide.Runners.html
  *
  */
class SchemaFakerSpec extends Specification {


  "schema" should {

    "integer" in {
      val jsonString: String = compact(render("type" -> "integer"))
      val schema = SchemaLoader.load(new JSONObject(jsonString))
      schema must haveClass[NumberSchema]
      val n = schema.asInstanceOf[NumberSchema]
      n.requiresInteger() must beTrue
      n.getMaximum must beNull
      n.getMinimum must beNull
    }
  }
  "fake" should {
    val faker = JsonFaker
    "number fake" should {
      "integer fake" should {
        val s1: List[JValue] = List(
          "type" -> "integer",
          ("type" -> "integer") ~ ("minimum" -> -10),
          ("type" -> "integer") ~ ("minimum" -> 0),
          ("type" -> "integer") ~ ("minimum" -> 0) ~ ("maximum" -> 100),
          ("type" -> "integer") ~ ("maximum" -> 100)
        )
        val s2: List[JValue] = s1.map(s => s.merge("exclusiveMinimum" -> true: JValue))
        val s3: List[JValue] = s1.map(s => s.merge("exclusiveMaximum" -> true: JValue))

        validate(s1 ::: s2 ::: s3, _.values.asInstanceOf[BigInt].toInt,"integer")

      }
      "double fake" should {
        val s1: List[JValue] = List(
          "type" -> "number",
          ("type" -> "number") ~ ("minimum" -> -10),
          ("type" -> "number") ~ ("minimum" -> 0),
          ("type" -> "number") ~ ("minimum" -> 0) ~ ("maximum" -> 100),
          ("type" -> "number") ~ ("maximum" -> 100)
        )
        val s2: List[JValue] = s1.map(s => s.merge("exclusiveMinimum" -> true: JValue))
        val s3: List[JValue] = s1.map(s => s.merge("exclusiveMaximum" -> true: JValue))

        validate(s1 ::: s2 ::: s3, _.values.asInstanceOf[Double],"double")
      }
      "multipleOf" in {
        val s = jValueToSchema(("type" -> "integer") ~ ("multipleOf" -> 10))
        faker.fakeJValue(s) must throwA[NotImplementedError]
      }
    }

    "array fake" should {
      val array: (String, String) = "type" -> "array"
      val items = "items" -> ("type" -> "integer")
      val nestItems = "items" -> (array ~ items)
      val s1: List[JValue] = List(
        array ~ items,
        array ~ items ~ ("minItems" -> 2),
        array ~ items ~ ("minItems" -> 2) ~ ("maxItems" -> 5),
        array ~ items ~ ("minItems" -> 2) ~ ("maxItems" -> 2)
      )
      validate(s1, jvToJSONArray,"all items")
      val s2: List[JValue] = List(
        array ~ ("items" -> JArray(List("type" -> "integer", "type" -> "number"))),
        array ~ ("items" -> JArray(List("type" -> "integer", array ~ items)))
      )
      validate(s2, jvToJSONArray,"fix items")
      val s3: List[JValue] = List(
        array ~ nestItems
      )
      validate(s3, jvToJSONArray,"nested items")
    }

    "string fake" should {
      val string: (String, String) = "type" -> "string"

      val s1: List[JValue] = List(
        string ,
        string  ~ ("minLength" -> 2),
        string  ~ ("minLength" -> 2) ~ ("maxLength" -> 5),
        string  ~ ("minLength" -> 2) ~ ("maxLength" -> 2)
      )
      validate(s1, _.values.asInstanceOf[String],"no pattern")
      val s2: List[JValue] = List(
        string  ~ ("pattern" -> """(\([0-9]{3}\))?[0-9]{3}-[0-9]{4}"""),
        string  ~ ("pattern" -> """^(\([0-9]{3}\))?[0-9]{3}-[0-9]{4}$""")
      )
      validate(s2, _.values.asInstanceOf[String],"pattern")
      //TODO max/min length 如何覆盖或者不覆盖pattern
//      val s3: List[JValue] = List(
//        string  ~ ("pattern" -> """^(\\([0-9]{3}\\))?[0-9]{3}-[0-9]{4}$""")~ ("maxLength" -> 5)
//      )
//      validate(s3, _.values.asInstanceOf[String],"pattern and max")

    }

    "object fake" should {
      val ob = "type"->"object"
      val stringProperty = "type"->"string"
      val intProperty="type"->"integer"
      val nestedP = ob ~ ("properties"->JObject("first"->stringProperty,"last"->intProperty))
      val s1:List[JValue]=List(
        ob ~ ("properties"->JObject("name"->stringProperty,"age"->intProperty))
      )
      validate(s1, jvToJSONObject,"object")
      val s2:List[JValue]=List(
        ob ~ ("properties"->JObject("name"->stringProperty,"age"->intProperty,"addr"-> nestedP))
      )
      validate(s2,jvToJSONObject,"nested object")
    }
  }
  "enum fake" should {
    val s1:List[JValue]=List(
      ("type"->"string") ~ ("enum" -> JArray(List(JString("A"),JString("B"))))
    )
    validate(s1, _.values.asInstanceOf[String],"enum string")
    val s2:List[JValue]=List(
      ("type"->"integer") ~ ("enum" -> JArray(List(JInt(1),JInt(2))))
    )
    validate(s2, _.values.asInstanceOf[BigInt].intValue(),"enum integer")
  }

  def validate(schemas: List[JValue], validated: JValue => Any,message:String): Unit = {
    val l: Int = Integer.valueOf(System.getProperty("loop", "1")).intValue()
    val faker = JsonFaker
    schemas.map(jValueToSchema).foreach({
      schema =>
        Range(0, l).foreach {
          x =>
            val jv = faker.fakeJValue(schema)
            s"validate - $message - ${jv.toString}"  in {
              schema.validate(validated(jv))
              success
            }
        }
    })
  }

  def jvToJSONArray(ja: JValue): JSONArray = {
    val jsonString: String = compact(render(ja))
    new JSONArray(jsonString)
  }

  def jvToJSONObject(ja: JValue): JSONObject = {
    val jsonString: String = compact(render(ja))
    new JSONObject(jsonString)
  }

  def jValueToSchema(sh: JValue): Schema = {
    SchemaLoader.load(jvToJSONObject(sh))
  }
}
