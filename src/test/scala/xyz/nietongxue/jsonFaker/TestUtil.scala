package xyz.nietongxue.jsonFaker

import org.json.{JSONArray, JSONObject}
import org.json4s._
import org.json4s.jackson.JsonMethods._

/**
  * Created by nielinjie on 9/12/16.
  */
object TestUtil {
  def jvToJSONArray(ja: JValue): JSONArray = {
    val jsonString: String = compact(render(ja))
    new JSONArray(jsonString)
  }

  def jvToJSONObject(ja: JValue): JSONObject = {
    val jsonString: String = compact(render(ja))
    new JSONObject(jsonString)
  }
}
