package com.nepplus.colosseum.datas

import org.json.JSONObject

class UserData(var id: Int, var email: String, var nickname: String) {

    companion object {

        fun getUserDataFromJson(json: JSONObject): UserData {

            val userData = UserData()

            userData.id = json.getInt("id")
            userData.email = json.getString("email")
            userData.nickname = json.getString("nick_name")

            return userData

        }

    }

    constructor() : this(0, "", "")

}