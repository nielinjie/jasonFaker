package xyz.nietongxue.jsonFaker.general

import org.json4s.JsonAST.JValue

/**
  * Created by nielinjie on 9/11/16.
  */
case class CombinedCondition[SCHEMA: FakeIt](subSchemas: List[SCHEMA], criterion: String) extends Condition with AFaker {
  override def fake(hints: Hints): JValue = {

    val random: Int = faker.number().numberBetween(0, subSchemas.size)
    this.criterion match {
      case "ALL" => ??? //TODO All的情况非常少见吧?
      case "ANY" =>
        JsonFake.fake(subSchemas.apply(random),hints)(implicitly[FakeIt[SCHEMA]]) //TODO Any的情况比较少见吧?
      case "ONE" =>
        JsonFake.fake(subSchemas.apply(random),hints)(implicitly[FakeIt[SCHEMA]])
    }
  }
}
