package com.nepplus.colosseum.utils

import android.util.Log
import android.widget.Toast
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

    interface JsonResponseHandler {

        fun onResponse(jsonObj: JSONObject)

    }



    companion object {

//        호스트 주소를 변수로 저장
        private val HOST_URL = "http://54.180.52.26"

//        로그인 기능 함수
        fun postRequestSignIn(id: String, pw: String, handler: JsonResponseHandler?) {
//            호출하기
            val urlString = "${HOST_URL}/user";
            val formData = FormBody.Builder()
                .add("email", id)
                .add("password", pw)
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {



                }

                override fun onResponse(call: Call, response: Response) {

                    val bodyStr = response.body!!.string()
                    val jsonObj = JSONObject(bodyStr)
                    Log.d("server", jsonObj.toString())

//                    val code = jsonObj.getInt("code")
//                    Log.d("server", code.toString())
                    handler?.onResponse(jsonObj)
                }
            })
    
        }

    }

}