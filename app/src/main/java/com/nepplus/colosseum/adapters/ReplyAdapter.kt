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
import com.nepplus.colosseum.datas.ReplyData
import com.nepplus.colosseum.datas.TopicData

class ReplyAdapter(val mContext: Context, resId: Int, val mList: List<ReplyData>) : ArrayAdapter<ReplyData>(mContext, resId, mList) {

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if (row == null) {
            row = mInflater.inflate(R.layout.reply_list_ltem, null)
        }
        row!!

        val data = mList[position]



        return row
    }

}