package xyz.nietongxue.jsonFaker.general
import org.json4s.JsonAST.{JObject, JValue}

/**
  * Created by nielinjie on 9/11/16.
  */
case class ObjectCondition[SCHEMA:FakeIt](schemas:Map[String,SCHEMA]) extends Condition{
  //TODO 所有property全部生成,不模拟可选
  //TODO additionalProperties 已忽略
  //TODO 没有properties的情况不支持
  //TODO dependencies不支持
  //TODO Pattern Properties不支持
  override def fake(hints: Hints): JValue = {
      JObject(schemas.map {
        case (name: String, s: SCHEMA) =>
          (name, JsonFake.fake[SCHEMA](s)(implicitly[FakeIt[SCHEMA]]))
      }.toList)

  }
}
