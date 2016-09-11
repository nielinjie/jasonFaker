package xyz.nietongxue.jsonFaker

/**
  * Created by nielinjie on 9/1/16.
  */
case class Hints(maxInt: Int = 100, minInt: Int = -100,
                 maxItems: Integer = 10, minItems: Integer = 1,
                 maxStringLength: Int = 100, minStringLength: Int = 10,
                 maxStringLengthWithPattern: Int = Integer.MAX_VALUE, minStringLengthWithPattern: Int = 1,
                 randomChar: String ="""\w*"""
                ) {

}
