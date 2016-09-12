package xyz.nietongxue.jsonFaker.swagger

import com.github.javafaker.Faker
import io.swagger.models.properties._
import org.json4s.JsonAST.{JBool, JValue}
import xyz.nietongxue.jsonFaker.general.{FakeIt, Hints, JsonFakerImp}

/**
  * Created by nielinjie on 9/12/16.
  */
object SwaggerFakeIt extends FakeIt[Property]{
  import Conditions._
  val faker = new Faker()
  override def fake(prop: Property,hints: Hints): JValue = {

    prop match {
      case n: AbstractNumericProperty => JsonFakerImp.fake(n,hints)
      case s: StringProperty => JsonFakerImp.fake(s,hints)
      case a: ArrayProperty => JsonFakerImp.fake(a,hints)
      case o: ObjectProperty => JsonFakerImp.fake(o,hints)
      case b: BooleanProperty => JBool(faker.bool().bool())
      //      case da: DateProperty =>
      //TODO more properties
    }
  }
}
