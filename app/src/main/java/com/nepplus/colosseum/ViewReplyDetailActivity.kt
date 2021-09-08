package com.nepplus.colosseum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nepplus.colosseum.datas.ReplyData
import kotlinx.android.synthetic.main.activity_view_reply_detail.*

class ViewReplyDetailActivity : BaseActivity() {

    lateinit var mReplyData: ReplyData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reply_detail)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mReplyData = intent.getSerializableExtra("replyData") as ReplyData

        sideAndNicknameTxt.text = "(${mReplyData.selectedSide.title}) ${mReplyData.writer.nickname}"
        replyContentTxt.text = mReplyData.content

    }
}