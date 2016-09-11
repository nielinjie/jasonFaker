package xyz.nietongxue.jsonFaker.schema

import com.github.javafaker.Faker
import org.everit.json.schema._
import org.json4s.JsonAST.{JBool, JNull, JValue}
import xyz.nietongxue.jsonFaker.general.{FakeIt, Hints, JsonFakerImp}

/**
  * Created by nielinjie on 9/11/16.
  */
object SchemaFakeIt extends FakeIt[Schema] {

  import xyz.nietongxue.jsonFaker.schema.Conditions._

  val faker = new Faker()

  override def fake(schema: Schema): JValue = {
    val hints = Hints()

    schema match {
      case n: NumberSchema => JsonFakerImp.fake(n, hints)
      case s: StringSchema => JsonFakerImp.fake(s, hints)
      case o: ObjectSchema => JsonFakerImp.fake(o, hints)
      case a: ArraySchema => JsonFakerImp.fake(a, hints)
      case nl: NullSchema => JNull
      case c: CombinedSchema => JsonFakerImp.fake(c, hints)
      case b: BooleanSchema => JBool(faker.bool().bool())
      //      case em: EnumSchema => em.fake(hints)
      case _ => ???

    }
  }
}
