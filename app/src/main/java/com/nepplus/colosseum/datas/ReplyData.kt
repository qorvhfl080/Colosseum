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

}