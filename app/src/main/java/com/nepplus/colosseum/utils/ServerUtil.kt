package com.nepplus.colosseum.utils

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class ServerUtil {




    companion object {

//        호스트 주소를 변수로 저장
        private val HOST_URL = "http://54.180.52.26"

//        로그인 기능 함수
        fun postRequestSignIn(id: String, pw: String) {

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
            client.newCall(request)

        }

    }

}