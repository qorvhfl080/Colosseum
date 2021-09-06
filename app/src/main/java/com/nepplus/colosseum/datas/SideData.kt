package com.nepplus.colosseum.datas

import java.io.Serializable

class SideData(var id: Int, var topicId: Int, var title: String, var voteCount: Int) : Serializable {



    constructor() : this(0, 0, "", 0)

}