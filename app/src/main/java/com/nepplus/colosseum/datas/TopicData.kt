package com.nepplus.colosseum.datas

import java.io.Serializable

class TopicData(var id: Int, var title: String, var imageURL: String) : Serializable {

    constructor() : this(0, "제목없음", "")

    constructor(id: Int) : this(id, "제목없음", "")

}