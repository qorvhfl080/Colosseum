package com.nepplus.colosseum

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.nepplus.colosseum.adapters.ChildReplyAdapter
import com.nepplus.colosseum.datas.ReplyData
import com.nepplus.colosseum.utils.GlobalData
import com.nepplus.colosseum.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_view_reply_detail.*
import org.json.JSONObject

class ViewReplyDetailActivity : BaseActivity() {

    lateinit var mReplyData: ReplyData

    val mChildReplyList = ArrayList<ReplyData>()

    lateinit var mChildReplyAdapter: ChildReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reply_detail)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        childReplyListView.setOnItemLongClickListener { adapterView, view, position, l ->

            val clickedReply = mChildReplyList[position]
            
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

                        getChildRepliesFromServer()

                    }
                })


            })
            alert.setNegativeButton("취소", null)
            alert.show()

            return@setOnItemLongClickListener true
        }

        okBtn.setOnClickListener {

            val inputContent = contentEdt.text.toString()

            if (inputContent.length < 5) {
                Toast.makeText(mContext, "5글자 이상 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ServerUtil.postRequestChildReply(mContext, inputContent, mReplyData.id, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {

                    getChildRepliesFromServer()

                    runOnUiThread {
                        contentEdt.setText("")

                        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

                    }

                }
            })

        }

    }

    override fun setValues() {

        mReplyData = intent.getSerializableExtra("replyData") as ReplyData

        sideAndNicknameTxt.text = "(${mReplyData.selectedSide.title}) ${mReplyData.writer.nickname}"
        replyContentTxt.text = mReplyData.content

        getChildRepliesFromServer()

        mChildReplyAdapter = ChildReplyAdapter(mContext, R.layout.child_reply_list_item, mChildReplyList)
        childReplyListView.adapter = mChildReplyAdapter

    }

    fun getChildRepliesFromServer() {

        ServerUtil.getRequestReplyDetail(mContext, mReplyData.id, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {

                val dataObj = jsonObj.getJSONObject("data")
                val replyObj = dataObj.getJSONObject("reply")
                val repliesArr = replyObj.getJSONArray("replies")

                mChildReplyList.clear()

                for (i in 0 until repliesArr.length()) {

                    mChildReplyList.add(ReplyData.getReplyDataFromJson(repliesArr.getJSONObject(i)))
                }

                runOnUiThread {
                    mChildReplyAdapter.notifyDataSetChanged()

                    childReplyListView.smoothScrollToPosition(mChildReplyList.lastIndex)
                }

            }
        })

    }

}