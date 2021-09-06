package com.nepplus.colosseum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.nepplus.colosseum.datas.TopicData
import kotlinx.android.synthetic.main.activity_view_topic_detail.*

class ViewTopicDetailActivity : BaseActivity() {

    lateinit var mTopicData: TopicData

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

    }
}