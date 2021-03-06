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

//        내 사용자 정보 가져오기
        fun getRequestUserData(context: Context, handler: JsonResponseHandler?) {

            val url = "${HOST_URL}/user_info".toHttpUrlOrNull()!!.newBuilder()
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


//       토론 상세 정보 가져오기
        fun getRequestTopicDetail(context: Context, topicId: Int, handler: JsonResponseHandler?) {

            val url = "${HOST_URL}/topic".toHttpUrlOrNull()!!.newBuilder()

            url.addPathSegment(topicId.toString())
            url.addEncodedQueryParameter("order_type", "NEW")

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

//      진영 선택 투표하기
        fun postRequestTopicVote(context: Context, sideId: Int, handler: JsonResponseHandler?) {

            val urlString = "${HOST_URL}/topic_vote";
            val formData = FormBody.Builder()
                    .add("side_id", sideId.toString())
                    .build()

            val request = Request.Builder()
                    .url(urlString)
                    .post(formData)
                    .header("X-Http-Token", ContextUtil.getToken(context))
                    .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {

                    val bodyStr = response.body!!.string()
                    val jsonObj = JSONObject(bodyStr)
                    Log.d("server", jsonObj.toString())

                    handler?.onResponse(jsonObj)
                }
            })

        }

//        댓글 상세 정보 가져오기
        fun getRequestReplyDetail(context: Context, replyId: Int, handler: JsonResponseHandler?) {

            val urlString = "${HOST_URL}/topic_reply/${replyId}";

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

                    val bodyStr = response.body!!.string()
                    val jsonObj = JSONObject(bodyStr)
                    Log.d("server", jsonObj.toString())

                    handler?.onResponse(jsonObj)
                }
            })

        }

//        토론 주제에 의견 등록하기
        fun postRequestTopicReply(context: Context, topicId: Int, content: String, handler: JsonResponseHandler?) {

            val urlString = "${HOST_URL}/topic_reply";
            val formData = FormBody.Builder()
                .add("topic_id", topicId.toString())
                .add("content", content.toString())
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {

                    val bodyStr = response.body!!.string()
                    val jsonObj = JSONObject(bodyStr)
                    Log.d("server", jsonObj.toString())

                    handler?.onResponse(jsonObj)
                }
            })

        }

//        좋아요 / 싫어요 찍기
        fun postRequestReplyLikeOrHate(context: Context, replyId: Int, isLike: Boolean, handler: JsonResponseHandler?) {
            val urlString = "${HOST_URL}/topic_reply_like";
            val formData = FormBody.Builder()
                .add("reply_id", replyId.toString())
                .add("is_like", isLike.toString())
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {

                    val bodyStr = response.body!!.string()
                    val jsonObj = JSONObject(bodyStr)
                    Log.d("server", jsonObj.toString())

                    handler?.onResponse(jsonObj)
                }
            })
        }

//      알림 갯수 or 목록 가져오기
        fun getRequestNotificationCountOrList(context: Context, needList: Boolean, handler: JsonResponseHandler?) {

            val url = "${HOST_URL}/notification".toHttpUrlOrNull()!!.newBuilder()
            url.addEncodedQueryParameter("need_all_notis", needList.toString())
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

//        댓글 삭제하기
        fun deleteRequestReply(context: Context, replyId: Int, handler: JsonResponseHandler?) {

            val url = "${HOST_URL}/topic_reply".toHttpUrlOrNull()!!.newBuilder()
            url.addEncodedQueryParameter("reply_id", replyId.toString())
            val urlString = url.toString()

            Log.d("server", urlString)

            val request = Request.Builder()
                    .url(urlString)
                    .delete()
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

//        어디까지 읽은 알람인지 서버에게 알려주기
        fun postRequestNotificationRead(context: Context, notificationId: Int, handler: JsonResponseHandler?) {
            val urlString = "${HOST_URL}/notification";
            val formData = FormBody.Builder()
                    .add("noti_id", notificationId.toString())
                    .build()

            val request = Request.Builder()
                    .url(urlString)
                    .post(formData)
                    .header("X-Http-Token", ContextUtil.getToken(context))
                    .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {

                    val bodyStr = response.body!!.string()
                    val jsonObj = JSONObject(bodyStr)
                    Log.d("server", jsonObj.toString())

                    handler?.onResponse(jsonObj)
                }
            })
        }

//        댓글에 답글달기
        fun postRequestChildReply(context: Context, content: String, parentReplyId: Int, handler: JsonResponseHandler?) {

            val urlString = "${HOST_URL}/topic_reply";
            val formData = FormBody.Builder()
                .add("content", content.toString())
                .add("parent_reply_id", parentReplyId.toString())
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getToken(context))
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {

                    val bodyStr = response.body!!.string()
                    val jsonObj = JSONObject(bodyStr)
                    Log.d("server", jsonObj.toString())

                    handler?.onResponse(jsonObj)
                }
            })

        }



    }

}