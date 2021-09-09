package com.nepplus.colosseum.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.nepplus.colosseum.R
import com.nepplus.colosseum.ViewReplyDetailActivity
import com.nepplus.colosseum.ViewTopicDetailActivity
import com.nepplus.colosseum.datas.ReplyData
import com.nepplus.colosseum.datas.TopicData
import com.nepplus.colosseum.utils.ServerUtil
import org.json.JSONObject
import java.text.SimpleDateFormat

class ChildReplyAdapter(val mContext: Context, resId: Int, val mList: List<ReplyData>) : ArrayAdapter<ReplyData>(mContext, resId, mList) {

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if (row == null) {
            row = mInflater.inflate(R.layout.child_reply_list_item, null)
        }
        row!!

        val data = mList[position]

        val sideAndNicknameTxt = row.findViewById<TextView>(R.id.sideAndNicknameTxt)
        val contentTxt = row.findViewById<TextView>(R.id.contentTxt)

        sideAndNicknameTxt.text=  "(${data.selectedSide.title}) ${data.writer.nickname}"
        contentTxt.text = data.content

        return row
    }

}