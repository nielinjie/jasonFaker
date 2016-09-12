package xyz.nietongxue.jsonFaker.general



import org.json4s.JsonAST.JValue

/**
  * Created by nielinjie on 9/11/16.
  */
object JsonFake {

  def fake[SCHEMA:FakeIt](t:SCHEMA,hints: Hints):JValue = {
    implicitly[FakeIt[SCHEMA]].fake(t,hints)
  }

}

object JsonFakerImp {
  def fake[SCHEMA](s: SCHEMA,hints: Hints)(implicit conditionFinder: ConditionFinder[SCHEMA]): JValue = {
    conditionFinder.findCondition(s).fake(hints)
  }
}

trait ConditionFinder[SCHEMA] {
  def findCondition(s: SCHEMA): Condition
}

trait Condition {
  def fake( hints: Hints): JValue
}

trait FakeIt[SCHEMA]{
  def fake(s:SCHEMA,hints: Hints):JValue
}



