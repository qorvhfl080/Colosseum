package com.nepplus.colosseum.datas

import java.io.Serializable

class TopicData(var id: Int, var title: String, var imageURL: String) : Serializable {

    //    선택 진영 목록을 담아줄 ArrayList
    val sideList = ArrayList<SideData>()

    constructor() : this(0, "제목없음", "")

    constructor(id: Int) : this(id, "제목없음", "")

}