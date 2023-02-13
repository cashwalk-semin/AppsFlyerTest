package com.cashwalklabs.appsflyertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.cashwalklabs.appsflyertest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
    }

    private fun initView() {
        binding.run {
            btnInApp.setOnClickListener {

            }

            btnAdRevenue.setOnClickListener {

            }

            btnElse.setOnClickListener {

            }
        }
    }
}