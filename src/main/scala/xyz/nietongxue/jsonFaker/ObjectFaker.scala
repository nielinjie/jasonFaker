package xyz.nietongxue.jsonFaker

import io.swagger.models.properties.{ObjectProperty, Property}
import org.everit.json.schema.{ObjectSchema, Schema}
import org.json4s.JsonAST.{JObject, JValue}

import scala.collection.JavaConversions._


/**
  * Created by nielinjie on 8/31/16.
  */
trait ObjectFaker {
  //TODO 所有property全部生成,不模拟可选
  //TODO additionalProperties 已忽略
  //TODO 没有properties的情况不支持
  //TODO dependencies不支持
  //TODO Pattern Properties不支持
  implicit class ObjectF(o: ObjectSchema) {
    def fake(): JValue = {
      JObject(o.getPropertySchemas.map {
        case (name: String, s: Schema) =>
          (name, JsonFaker.fakeJValue(s))
      }.toList)

    }
  }

  implicit class OF2(o: ObjectProperty) {
    def fake(): JValue = {
      JObject(o.getProperties.map {
        case (name: String, p: Property) =>
          (name, JsonFaker.fakeJValue(p))
      }.toList)
    }
  }

}
