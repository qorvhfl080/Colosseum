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

class NotificationAdapter(val mContext: Context, resId: Int, val mList: List<NotificationData>) : ArrayAdapter<NotificationData>(mContext, resId, mList) {

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if (row == null) {
            row = mInflater.inflate(R.layout.notification_list_item, null)
        }
        row!!

        val data = mList[position]

        val notificationTxt = row.findViewById<TextView>(R.id.notificationTitleTxt)

        notificationTxt.text = data.title


        return row
    }

}