package com.nepplus.colosseum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.nepplus.colosseum.adapters.ReplyAdapter
import com.nepplus.colosseum.datas.ReplyData
import com.nepplus.colosseum.datas.TopicData
import com.nepplus.colosseum.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_view_topic_detail.*
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {

    lateinit var mTopicData: TopicData

    val mReplyList = ArrayList<ReplyData>()

    lateinit var mReplyAdapter: ReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_topic_detail)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mTopicData = intent.getSerializableExtra("topic") as TopicData

        Glide.with(mContext)
            .load(mTopicData.imageURL)
            .into(topicImg)
        titleTxt.text = mTopicData.title

        getTopicDetailDataFromServer()

        mReplyAdapter = ReplyAdapter(mContext, R.layout.reply_list_ltem, mReplyList)
        replyListView.adapter = mReplyAdapter

    }

//    토론 상세 데이터 서버에서 불러오기
    fun getTopicDetailDataFromServer() {

        ServerUtil.getRequestTopicDetail(mContext, mTopicData.id, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {

                val objData = jsonObj.getJSONObject("data")
                val topicObj = objData.getJSONObject("topic")

                mTopicData = TopicData.getTopicDataFromJson(topicObj)

                val repliesArr = topicObj.getJSONArray("replies")
                for (i in 0 until repliesArr.length()) {

                    val replyObj = repliesArr.getJSONObject(i)
                    val replyData = ReplyData.getReplyDataFromJson(replyObj)

                    mReplyList.add(replyData)

                }

                refreshTopicDataToUI()

            }
        })

    }

    fun refreshTopicDataToUI() {
        runOnUiThread {

            firstSideTitleTxt.text = mTopicData.sideList[0].title
            firstSideVoteCountTxt.text = "${mTopicData.sideList[0].voteCount}표"

            secondSideTitleTxt.text = mTopicData.sideList[1].title
            secondSideVoteCountTxt.text = "${mTopicData.sideList[1].voteCount}표"

            mReplyAdapter.notifyDataSetChanged()
        }
    }

}