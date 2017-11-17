package com.magiclon.huatuodrug.activity

import android.content.Intent
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.jaeger.library.StatusBarUtil
import com.magiclon.huatuodrug.R
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


class FlashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash)
        Logger.addLogAdapter(AndroidLogAdapter())
        StatusBarUtil.setTransparent(this)
        Handler().postDelayed({
            startActivity(Intent(this@FlashActivity, MainActivity::class.java))
            finish()
        }, 1000)
    }
}
