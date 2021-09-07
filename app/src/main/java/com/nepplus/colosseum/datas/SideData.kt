package com.nepplus.colosseum.datas

import org.json.JSONObject
import java.io.Serializable

class SideData(var id: Int, var topicId: Int, var title: String, var voteCount: Int) : Serializable {

    companion object {

        fun getSideDataFromJson(json: JSONObject): SideData {

            val sideData = SideData()

            sideData.id = json.getInt("id")
            sideData.topicId = json.getInt("topic_id")
            sideData.title = json.getString("title")
            sideData.voteCount = json.getInt("vote_count")

            return sideData
        }

    }

    constructor() : this(0, 0, "", 0)

}