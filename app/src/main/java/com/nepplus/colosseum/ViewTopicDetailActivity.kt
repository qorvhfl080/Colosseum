package com.nepplus.colosseum

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.nepplus.colosseum.adapters.ReplyAdapter
import com.nepplus.colosseum.datas.ReplyData
import com.nepplus.colosseum.datas.TopicData
import com.nepplus.colosseum.utils.GlobalData
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

    override fun onResume() {
        super.onResume()

        getTopicDetailDataFromServer()
    }

    override fun setupEvents() {

        replyListView.setOnItemLongClickListener { adapterView, view, position, l ->

            val clickedReply = mReplyList[position]

            if (GlobalData.loginUser!!.id != clickedReply.writer.id) {
                Toast.makeText(mContext, "자신이 적은 답글만 삭제할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnItemLongClickListener true
            }

            val alert = AlertDialog.Builder(mContext)
            alert.setMessage("해당 댓글을 삭제하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, position ->

                ServerUtil.deleteRequestReply(mContext, clickedReply.id, object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(jsonObj: JSONObject) {
                        runOnUiThread {
                            Toast.makeText(mContext, "답글을 삭제했습니다.", Toast.LENGTH_SHORT).show()
                        }

                        getTopicDetailDataFromServer()

                    }
                })


            })
            alert.setNegativeButton("취소", null)
            alert.show()

            return@setOnItemLongClickListener true

        }

        addReplyBtn.setOnClickListener {

            if (mTopicData.mySelectedSide == null) {
                Toast.makeText(mContext, "투표를 진행해야 의견 등록이 가능합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //ServerUtil.postRequestTopicReply(mContext, , )

            val myIntent = Intent(mContext, EditReplyActivity::class.java)
            myIntent.putExtra("selectedSide", mTopicData.mySelectedSide)
            startActivity(myIntent)

        }
        
        val ocl = object : View.OnClickListener {
            override fun onClick(view: View?) {

                val clickedSideId = view!!.tag.toString().toInt()

                ServerUtil.postRequestTopicVote(mContext, clickedSideId, object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(jsonObj: JSONObject) {

                        getTopicDetailDataFromServer()

                    }
                })
            }
        }
        
        voteToFirstSideBtn.setOnClickListener(ocl)
        voteToSecondSideBtn.setOnClickListener(ocl)
        
    }

    override fun setValues() {

        mTopicData = intent.getSerializableExtra("topic") as TopicData

        voteToFirstSideBtn.tag = mTopicData.sideList[0].id
        voteToSecondSideBtn.tag = mTopicData.sideList[1].id

        Glide.with(mContext)
            .load(mTopicData.imageURL)
            .into(topicImg)
        titleTxt.text = mTopicData.title

        //getTopicDetailDataFromServer()

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
                mReplyList.clear()

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

            if (mTopicData.mySideId == -1) {
                voteToFirstSideBtn.text = "투표하기"
                voteToSecondSideBtn.text = "투표하기"
            } else {
                if (mTopicData.mySideId == mTopicData.sideList[0].id) {
                    voteToFirstSideBtn.text = "취소하기"
                    voteToSecondSideBtn.text = "선택변경"
                } else {
                    voteToFirstSideBtn.text = "선택변경"
                    voteToSecondSideBtn.text = "취소하기"
                }
            }

            mReplyAdapter.notifyDataSetChanged()
        }
    }

}