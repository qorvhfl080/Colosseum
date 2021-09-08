package com.nepplus.colosseum.datas

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class NotificationData(var id: Int, var title: String) {

    val createdAt = Calendar.getInstance()

    companion object {

        fun getNotificationDataFromJson(json: JSONObject): NotificationData {

            val notificationData = NotificationData()

            notificationData.id = json.getInt("id")
            notificationData.title = json.getString("title")

            val createdAtString = json.getString("created_at")
            val serverFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            notificationData.createdAt.time = serverFormat.parse(createdAtString)

            val localTimeZone = notificationData.createdAt.timeZone
            val timeDiff = localTimeZone.rawOffset / 1000 / 60 / 60

            notificationData.createdAt.add(Calendar.HOUR, timeDiff)

            return notificationData
        }

    }

    constructor() : this(0, "")

}