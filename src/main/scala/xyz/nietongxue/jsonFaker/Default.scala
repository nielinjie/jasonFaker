package xyz.nietongxue.jsonFaker

/**
  * Created by nielinjie on 9/3/16.
  */
//TODO 怎么处理default？有default就不用fake？
//default 和example的区别是啥？
//default - 当没有提供时server使用这个 - request -不需要， response -
//example - response可以使用，但跟fake重复，采用hints控制？
trait Default[T] {
  val default:Option[T]
}
trait Example[T]{
  val example:Option[T]
}
