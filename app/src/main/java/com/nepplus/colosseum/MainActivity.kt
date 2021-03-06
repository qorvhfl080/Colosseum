package com.nepplus.colosseum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.iid.FirebaseInstanceId
import com.nepplus.colosseum.adapters.TopicAdapter
import com.nepplus.colosseum.datas.TopicData
import com.nepplus.colosseum.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.my_custom_action_bar.*
import org.json.JSONObject

class MainActivity : BaseActivity() {

    val mTopicList = ArrayList<TopicData>()

    lateinit var mTopicAdapter: TopicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()

        Log.d("push", FirebaseInstanceId.getInstance().token!!)

    }

    override fun onResume() {
        super.onResume()

//        안 읽은 알림 받아오기
        ServerUtil.getRequestNotificationCountOrList(mContext, false, object :  ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
                val dataObj = jsonObj.getJSONObject("data")
                val unreadCount = dataObj.getInt("unread_noty_count")

                runOnUiThread {
                    if (unreadCount == 0) {
                        notificationCountTxt.visibility = View.GONE
                    } else {
                        notificationCountTxt.text = unreadCount.toString()
                        notificationCountTxt.visibility = View.VISIBLE
                    }
                }
            }
        })



    }

    override fun setupEvents() {

        topicListView.setOnItemClickListener { adapterView, view, position, l ->

            val myIntent = Intent(mContext, ViewTopicDetailActivity::class.java)
            myIntent.putExtra("topic", mTopicList[position])
            startActivity(myIntent)

        }

        profileImg.setOnClickListener {

            val myIntent = Intent(mContext, MyProfileActivity::class.java)
            startActivity(myIntent)

        }

    }

    override fun setValues() {

        getMainDataFromServer()

        mTopicAdapter = TopicAdapter(mContext, R.layout.topic_list_item, mTopicList)
        topicListView.adapter = mTopicAdapter

        backBtn.visibility = View.GONE
        notificationLayout.visibility = View.VISIBLE

        profileImg.visibility = View.VISIBLE

    }

    fun getMainDataFromServer() {

        ServerUtil.getRequestMainData(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {

                val dataObj = jsonObj.getJSONObject("data")
                val topicsArr = dataObj.getJSONArray("topics")
                val userObj = dataObj.getJSONObject("user")

                for (i in 0 until topicsArr.length()) {
                    val topicObj = topicsArr.getJSONObject(i)

                    val tempTopicData = TopicData.getTopicDataFromJson(topicObj)

                    mTopicList.add(tempTopicData)

                }

                runOnUiThread {
                    Toast.makeText(mContext, "${userObj.getString("nick_name")}님 환영합니다.", Toast.LENGTH_SHORT).show()
                    mTopicAdapter.notifyDataSetChanged()
                }

            }
        })

    }

}