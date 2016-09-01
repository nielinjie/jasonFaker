package xyz.nietongxue.jsonFaker

import java.util

import com.github.javafaker.Faker
import org.everit.json.schema.EnumSchema
import org.json4s.{DefaultFormats, Extraction, Formats}
import org.json4s.JsonAST.JValue

import scala.collection.JavaConversions._


/**
  * Created by nielinjie on 9/1/16.
  */
trait EnumFaker {
  implicit class EnumFaker(es:EnumSchema){
    implicit val formats: Formats =
      DefaultFormats
    val faker = new Faker()
    def fake(hits:Hints): JValue = {
      val values: util.Set[AnyRef] = es.getPossibleValues
      val data = values.toList.get(faker.number().numberBetween(0,values.size))
      Extraction.decompose(data)
    }
  }
}
