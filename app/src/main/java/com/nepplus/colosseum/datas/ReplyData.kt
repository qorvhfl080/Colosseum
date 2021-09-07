package com.nepplus.colosseum.datas

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ReplyData(var id: Int, var content: String,
                var likeCount: Int, var hateCount: Int,
                var myLike: Boolean, var myHate: Boolean, var replyCount: Int) {

    lateinit var selectedSide: SideData

    lateinit var writer: UserData

    val createdAt = Calendar.getInstance()

    companion object {

        val serverFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        fun getReplyDataFromJson(json: JSONObject): ReplyData {

            val replyData = ReplyData()

            replyData.id = json.getInt("id")
            replyData.content = json.getString("content")
            replyData.likeCount = json.getInt("like_count")
            replyData.hateCount = json.getInt("dislike_count")
            replyData.myLike = json.getBoolean("my_like")
            replyData.myHate = json.getBoolean("my_dislike")
            replyData.replyCount = json.getInt("reply_count")

            val selectedSideObj = json.getJSONObject("selected_side")
            replyData.selectedSide = SideData.getSideDataFromJson(selectedSideObj)

            val userObj = json.getJSONObject("user")
            replyData.writer = UserData.getUserDataFromJson(userObj)

            val createdAtString = json.getString("created_at")
            replyData.createdAt.time = serverFormat.parse(createdAtString)

            return replyData

        }

    }

    constructor() : this(0, "", 0, 0, false, false, 0)

    fun getFormattedTimeAgo(): String {

        val now = Calendar.getInstance()
        val interval = now.timeInMillis - this.createdAt.timeInMillis

        if (interval < 1000) {
//            1초 이내
            return "방금 전"
        } else if (interval < 1000 * 60) {
//            1분 이내
            return "${interval / 1000}초 전"
        } else if (interval < 3600000) {
//            1시간 이내
            return "${interval / 1000 / 60}분 전"
        } else if (interval < 3600000 * 24) {
//            1일 이내
            return "${interval / 1000 / 60 / 60}시간 전"
        } else if (interval < 5 * 24 * 60 * 60 * 1000) {
//            5일 이내
            return "${interval / 1000 / 60 / 60 / 24}일 전"
        } else {
            val replyDisplayFormat = SimpleDateFormat("yyyy년 M월 d일")
            return replyDisplayFormat.format(this.createdAt.timeInMillis)
        }

    }

}