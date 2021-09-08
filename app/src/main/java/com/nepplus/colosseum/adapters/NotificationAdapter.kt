package com.nepplus.colosseum.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nepplus.colosseum.R
import com.nepplus.colosseum.datas.NotificationData
import java.text.SimpleDateFormat

class NotificationAdapter(val mContext: Context, resId: Int, val mList: List<NotificationData>) : ArrayAdapter<NotificationData>(mContext, resId, mList) {

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if (row == null) {
            row = mInflater.inflate(R.layout.notification_list_item, null)
        }
        row!!

        val data = mList[position]

        val notificationTitleTxt = row.findViewById<TextView>(R.id.notificationTitleTxt)
        val notificationDateTxt = row.findViewById<TextView>(R.id.notificationDateTxt)

        notificationTitleTxt.text = data.title
        val sdf = SimpleDateFormat("yyyy년 M월 d일 a h:mm")
        notificationDateTxt.text = sdf.format(data.createdAt.time)

        return row
    }

}