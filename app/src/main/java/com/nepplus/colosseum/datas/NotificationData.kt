package com.nepplus.colosseum.datas

import org.json.JSONObject

class NotificationData(var id: Int, var title: String) {

    companion object {

        fun getNotificationDataFromJson(json: JSONObject): NotificationData {

            val notificationData = NotificationData()

            notificationData.id = json.getInt("id")
            notificationData.title = json.getString("title")

            return notificationData
        }

    }

    constructor() : this(0, "")

}