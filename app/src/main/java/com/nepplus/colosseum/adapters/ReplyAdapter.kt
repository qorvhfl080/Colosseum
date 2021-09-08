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
import com.nepplus.colosseum.ViewTopicDetailActivity
import com.nepplus.colosseum.datas.ReplyData
import com.nepplus.colosseum.datas.TopicData
import com.nepplus.colosseum.utils.ServerUtil
import org.json.JSONObject
import java.text.SimpleDateFormat

class ReplyAdapter(val mContext: Context, resId: Int, val mList: List<ReplyData>) : ArrayAdapter<ReplyData>(mContext, resId, mList) {

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if (row == null) {
            row = mInflater.inflate(R.layout.reply_list_ltem, null)
        }
        row!!

        val data = mList[position]

        val selectedSideTxt = row.findViewById<TextView>(R.id.selectedSideTxt)
        val writerNickNameTxt = row.findViewById<TextView>(R.id.writerNicknameTxt)
        val createdAtTxt = row.findViewById<TextView>(R.id.createdAtTxt)
        val contentTxt = row.findViewById<TextView>(R.id.contentTxt)
        val replyCountTxt = row.findViewById<TextView>(R.id.replyCountTxt)
        val likeCountTxt = row.findViewById<TextView>(R.id.likeCountTxt)
        val hateCountTxt = row.findViewById<TextView>(R.id.hateCountTxt)

        contentTxt.text = data.content
        replyCountTxt.text = "답글 ${data.replyCount}개"
        likeCountTxt.text = "좋아요 ${data.likeCount}개"
        hateCountTxt.text = "싫어요 ${data.hateCount}개"

        selectedSideTxt.text = "(${data.selectedSide.title})"
        writerNickNameTxt.text = data.writer.nickname
        val sdf = SimpleDateFormat("yyyy년 M월 d일")
        createdAtTxt.text = data.getFormattedTimeAgo()

        if (data.myLike) {
            likeCountTxt.setBackgroundResource(R.drawable.red_border_rect)
        } else {
            likeCountTxt.setBackgroundResource(R.drawable.black_border_rect)
        }

        if (data.myHate) {
            hateCountTxt.setBackgroundResource(R.drawable.blue_border_rect)
        } else {
            hateCountTxt.setBackgroundResource(R.drawable.black_border_rect)
        }

        likeCountTxt.tag = true
        hateCountTxt.tag = false

        val ocl = object : View.OnClickListener {
            override fun onClick(view: View?) {

                val isLike = view!!.tag.toString().toBoolean()

                ServerUtil.postRequestReplyLikeOrHate(mContext, data.id, isLike, object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(jsonObj: JSONObject) {

                        (mContext as ViewTopicDetailActivity).getTopicDetailDataFromServer()

                    }
                })
            }
        }
        
        likeCountTxt.setOnClickListener(ocl)
        hateCountTxt.setOnClickListener(ocl)

        replyCountTxt.setOnClickListener {

//            val myIntent = Intent()
//            mContext.startActivity(myIntent)

        }
        
        return row
    }

}