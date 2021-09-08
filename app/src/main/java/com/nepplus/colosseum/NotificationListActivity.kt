package com.nepplus.colosseum

import android.app.Notification
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nepplus.colosseum.adapters.NotificationAdapter
import com.nepplus.colosseum.datas.NotificationData
import com.nepplus.colosseum.datas.TopicData
import com.nepplus.colosseum.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_notification_list.*
import kotlinx.android.synthetic.main.notification_list_item.*
import org.json.JSONObject

class NotificationListActivity : BaseActivity() {

    var mNotificationList = ArrayList<NotificationData>()

    lateinit var mNotificationAdapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_list)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

    }

    override fun setValues() {

        getNotificationListFromServer()

        mNotificationAdapter = NotificationAdapter(mContext, R.layout.notification_list_item, mNotificationList)
        notificationListView.adapter = mNotificationAdapter

    }

    fun getNotificationListFromServer() {

        ServerUtil.getRequestNotificationCountOrList(mContext, true, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {

                val dataObj = jsonObj.getJSONObject("data")
                val notificationArr = dataObj.getJSONArray("notifications")

                for (i in 0 until notificationArr.length()) {

                    val notificationObj = notificationArr.getJSONObject(i)
                    val notificationData = NotificationData.getNotificationDataFromJson(notificationObj)

                    mNotificationList.add(notificationData)
                }

                runOnUiThread {
                    mNotificationAdapter.notifyDataSetChanged()
                }

                ServerUtil.postRequestNotificationRead(mContext, mNotificationList[0].id, null)

            }
        })

    }

}