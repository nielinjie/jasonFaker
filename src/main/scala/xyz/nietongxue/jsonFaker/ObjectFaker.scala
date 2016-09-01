package xyz.nietongxue.jsonFaker

import org.everit.json.schema.{ObjectSchema, Schema}
import org.json4s.JsonAST.{JObject, JValue}

import scala.collection.JavaConversions._


/**
  * Created by nielinjie on 8/31/16.
  */
trait ObjectFaker {
  implicit class ObjectFaker(o:ObjectSchema){
    //TODO 所有property全部生成,不模拟可选
    //TODO additionalProperties 已忽略
    //TODO 没有properties的情况不支持
    //TODO dependencies不支持
    //TODO Pattern Properties不支持
    def fake():JValue={

      JObject(o.getPropertySchemas.map{
        case (name:String,s:Schema)=>
          (name,JsonFaker.fakeJValue(s))
      }.toList)

    }
  }

}
