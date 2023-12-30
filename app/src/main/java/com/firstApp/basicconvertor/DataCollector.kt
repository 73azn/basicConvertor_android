import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.net.URI
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Call
import okhttp3.Callback
import okio.IOException

class DataCollector {
    var rateCode : Map<*,*>? = null

    suspend fun build(){
    withContext(Dispatchers.IO){

        val client = OkHttpClient()
        val request = Request.Builder().url("https://open.er-api.com/v6/latest").build()


        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val responseBody = response.body?.string() ?: throw IOException("Null Response Body")
                val jsonObject = JsonParser.parseString(responseBody).asJsonObject
                rateCode = jsonObjectToMap(jsonObject.getAsJsonObject("rates"))
            }
        } catch (e: Exception) {

            Log.d("test the keys","${e.message}")// You might want to handle different exceptions differently
        }
    }
    }



    fun convert(amount:Double, from:String , to:String): Double{
        return (amount/rateCode?.get(from).toString().toDouble())*rateCode?.get(to).toString().toDouble()
                //amount                   divided by from          multiply by to
    }


    fun jsonObjectToMap(jsonObject: JsonObject): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()

        for ((key, value) in jsonObject.entrySet()) {
            when {
                value.isJsonObject -> map[key] = jsonObjectToMap(value.asJsonObject)
                value.isJsonArray -> map[key] = value.asJsonArray.toList() // or further processing if needed
                value.isJsonPrimitive -> map[key] = value.asJsonPrimitive
                value.isJsonNull -> map[key] = null
            }
        }

        return map
    }

}