package com.nepplus.colosseum.datas

import org.json.JSONObject
import java.io.Serializable

class TopicData(var id: Int, var title: String, var imageURL: String) : Serializable {

    companion object {

        fun getTopicDataFromJson(json: JSONObject): TopicData {

            val topicData = TopicData()
            topicData.id = json.getInt("id")
            topicData.title = json.getString("title")
            topicData.imageURL = json.getString("img_url")

            val sideArr = json.getJSONArray("sides")
            for (i in 0 until sideArr.length()) {

                val sideObj = sideArr.getJSONObject(i)
                val sideData = SideData.getSideDataFromJson(sideObj)

                topicData.sideList.add(sideData)

            }

            return topicData
        }

    }

    //    선택 진영 목록을 담아줄 ArrayList
    val sideList = ArrayList<SideData>()

    constructor() : this(0, "제목없음", "")

    constructor(id: Int) : this(id, "제목없음", "")

}