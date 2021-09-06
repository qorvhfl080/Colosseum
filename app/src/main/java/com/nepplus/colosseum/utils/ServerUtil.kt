package com.nepplus.colosseum.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
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

//        회원가입 실행 함수
        fun putRequestSingUp(email: String, password: String, nickname: String,handler: JsonResponseHandler?) {

            val urlString = "${HOST_URL}/user"
            val formData = FormBody.Builder()
                    .add("email", email)
                    .add("password", password)
                    .add("nickname", nickname)
                    .build()

            val request = Request.Builder()
                    .url(urlString)
                    .put(formData)
                    .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("server", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }
            })

        }

//        이메일 / 닉네임 중복 확인 함수
        fun getRequestDuplCheck(type: String, value: String, handler: JsonResponseHandler?) {

            val url = "${HOST_URL}/user_check".toHttpUrlOrNull()!!.newBuilder()
            url.addEncodedQueryParameter("type", type)
            url.addEncodedQueryParameter("value", type)
            val urlString = url.toString()

            Log.d("server", urlString)

            val request = Request.Builder()
                .url(urlString)
                .get()
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("server", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }
            })

        }

//        메인화면 데이터 가져오기
        fun getRequestMainData(context: Context, handler: JsonResponseHandler?) {

            val url = "${HOST_URL}/v2/main_info".toHttpUrlOrNull()!!.newBuilder()
            val urlString = url.toString()

            Log.d("server", urlString)

            val request = Request.Builder()
                    .url(urlString)
                    .get()
                    .header("X-Http-Token", ContextUtil.getToken(context))
                    .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("server", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }
            })

        }

    }

}