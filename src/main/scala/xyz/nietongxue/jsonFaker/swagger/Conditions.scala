package xyz.nietongxue.jsonFaker.swagger


/**
  * Created by nielinjie on 9/12/16.
  */
object Conditions {
  implicit val scf = StringCo
  implicit val ncf = NumberCo
  implicit val ocf = ObjectCo
  implicit val acf = ArrayCo
}
