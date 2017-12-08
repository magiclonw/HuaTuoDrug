package com.magiclon.huatuodrug.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jude.swipbackhelper.SwipeBackHelper


/**
 * 作者：MagicLon
 * 时间：2017/10/27 027
 * 邮箱：1348149485@qq.com
 * 描述：
 */
 abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SwipeBackHelper.onCreate(this)
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true)
                .setSwipeSensitivity(0.5f)
        initView()
        initEvents()
        initData()
    }

    abstract fun initView()

    abstract fun initEvents()

    abstract fun initData()

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        SwipeBackHelper.onPostCreate(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        SwipeBackHelper.onDestroy(this)
    }


}