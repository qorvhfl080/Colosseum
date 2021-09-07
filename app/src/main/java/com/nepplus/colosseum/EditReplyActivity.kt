package com.nepplus.colosseum

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.nepplus.colosseum.datas.SideData
import com.nepplus.colosseum.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_edit_reply.*
import org.json.JSONObject

class EditReplyActivity : BaseActivity() {

    lateinit var mSelectedSide: SideData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_reply)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        postReplyBtn.setOnClickListener {

            val inputContent = contentEdt.text.toString()
            if (inputContent.length < 10) {
                Toast.makeText(mContext, "10자 이상 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val alert = AlertDialog.Builder(mContext)
            alert.setMessage("정말 등록하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
                ServerUtil.postRequestTopicReply(
                    mContext,
                    mSelectedSide.topicId,
                    inputContent,
                    object : ServerUtil.JsonResponseHandler {
                        override fun onResponse(jsonObj: JSONObject) {
                            runOnUiThread {
                                Toast.makeText(mContext, "의견 등록 성공", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }
                    })
            })
            alert.setNegativeButton("취소", null)
            alert.show()

        }

    }

    override fun setValues() {

        mSelectedSide = intent.getSerializableExtra("selectedSide") as SideData

    }
}