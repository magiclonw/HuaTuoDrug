package com.magiclon.huatuodrug.activity

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import com.magiclon.huatuodrug.R
import com.magiclon.huatuodrug.adapter.MainAdapter
import com.magiclon.huatuodrug.db.DBManager
import com.magiclon.huatuodrug.model.CommonDrugBean
import com.magiclon.huatuodrug.util.StatusBarUtil
import com.magiclon.individuationtoast.ToastUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawlayout_left.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var dbmanager: DBManager? = null
    private var adapter: MainAdapter? = null
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarUtil.setColor(this, resources.getColor(R.color.colorPrimary), 0)
        initView()
        initEvents()
        initData()
    }

    @Suppress("DEPRECATION")
    private fun initView() {
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        var arraw = DrawerArrowDrawable(this)
        arraw.color = resources.getColor(R.color.white)
        toggle.drawerArrowDrawable = arraw
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun initEvents() {
        ll_main_search.setOnClickListener(this)
        ll_main_terms.setOnClickListener(this)
        ll_main_drugalis.setOnClickListener(this)
        ll_main_subject.setOnClickListener(this)
    }

    private fun initData() {
        dbmanager = DBManager.getInstance(this)
        dbmanager?.copyDBFile()
        val list: MutableList<CommonDrugBean> = dbmanager?.allCommonDrug!!
        adapter = MainAdapter(list, this)
        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = adapter
        adapter?.setOnItemClickListener { view1, view2, position ->
            var first = android.support.v4.util.Pair<View, String>(view1, ViewCompat.getTransitionName(view1))
            var second = android.support.v4.util.Pair<View, String>(view2, ViewCompat.getTransitionName(view2))
            val transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity, first, second)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(Intent(this@MainActivity, DiseaseDetailActivity::class.java).putExtra("id", list[position].id), transitionActivityOptions.toBundle())
            } else {
                startActivity(Intent(this@MainActivity, DiseaseDetailActivity::class.java).putExtra("id", list[position].id))
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ll_main_search -> startActivity(Intent(this@MainActivity, SearchActivity::class.java))
            R.id.ll_main_terms -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(Intent(this@MainActivity, TermsActivity::class.java).putExtra("type", "1"), ActivityOptions.makeSceneTransitionAnimation(this, tv_main_terms, "sharedviewterms").toBundle())
                } else {
                    startActivity(Intent(this@MainActivity, TermsActivity::class.java).putExtra("type", "1"))
                }
            }
            R.id.ll_main_drugalis -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(Intent(this@MainActivity, TermsActivity::class.java).putExtra("type", "2"), ActivityOptions.makeSceneTransitionAnimation(this, tv_main_drugalis, "sharedviewterms").toBundle())
                } else {
                    startActivity(Intent(this@MainActivity, TermsActivity::class.java).putExtra("type", "2"))
                }
            }
            R.id.ll_main_subject->{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(Intent(this@MainActivity, TermsActivity::class.java).putExtra("type", "3"), ActivityOptions.makeSceneTransitionAnimation(this, tv_main_subject, "sharedviewterms").toBundle())
                } else {
                    startActivity(Intent(this@MainActivity, TermsActivity::class.java).putExtra("type", "3"))
                }
            }
        }
    }

    private var mPressedTime: Long = 0
    override fun onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.START)) {
            drawer.closeDrawer(Gravity.START)
            return
        }
        val mNowTime: Long = System.currentTimeMillis()//获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            ToastUtil.showinfo(this@MainActivity, "再按一次退出程序")
            mPressedTime = mNowTime
        } else {
            super.onBackPressed()
        }
    }

    @Suppress("DEPRECATION")
    override fun getResources(): Resources {//保障字体在系统字体改变时，app的字体不会改变
        val res = super.getResources()
        val config = Configuration()
        config.fontScale = 1.0f
        res.updateConfiguration(config, res.displayMetrics)
        return res
    }

}
