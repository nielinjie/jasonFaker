package xyz.nietongxue.jsonFaker

import com.github.javafaker.Faker
import org.everit.json.schema.StringSchema
import org.json4s.JsonAST.{JString, JValue}

/**
  * Created by nielinjie on 8/31/16.
  */
trait StringFaker {

  implicit class StringFaker(s: StringSchema) {
    //TODO reuse faker?
    val faker = new Faker()

    def fake(hints: Hints): JValue = {
      //TODO max/min length 如何覆盖或者不覆盖pattern
      //TODO 支持format
      val pattern:Option[String] = Option(s.getPattern).map({ p =>
        val ps = p.toString
        if(ps.startsWith("^") && ps.endsWith("$"))
          ps.substring(1,ps.length-1)
        else
          ps
      }
      )
      val min :Option[Int]= Option(s.getMinLength).map(_.intValue())
      val max:Option[Int] = Option(s.getMaxLength).map(_.intValue())
      pattern match {
        case Some(p) =>
          JString(faker.regexify(p,min.getOrElse(hints.minStringLengthWithPattern),max.getOrElse(hints.maxStringLengthWithPattern)))
        case None =>
          JString(faker.regexify(hints.randomChar,min.getOrElse(hints.minStringLength),max.getOrElse(hints.maxStringLength)))
      }
    }
  }

}
