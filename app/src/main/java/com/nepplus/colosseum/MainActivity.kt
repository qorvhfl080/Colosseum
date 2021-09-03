package com.nepplus.colosseum

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.nepplus.colosseum.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.txt.text = "바인딩ok"
    }

    override fun setupEvents() {

    }

    override fun setValues() {

    }
}