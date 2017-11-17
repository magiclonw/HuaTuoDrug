package com.magiclon.huatuodrug.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jaeger.library.StatusBarUtil
import com.magiclon.huatuodrug.R
import com.magiclon.huatuodrug.adapter.TermsDetailAdapter
import com.magiclon.huatuodrug.db.DBManager
import com.magiclon.huatuodrug.model.TermsBean
import kotlinx.android.synthetic.main.activity_termsdetail.*

class TermsDetailActivity : BaseActivity(), View.OnClickListener {
    private var dbmanager: DBManager? = null
    private var adapter: TermsDetailAdapter? = null
    private var pid=""
    private var pname=""
    var type=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setColorForSwipeBack(this, resources.getColor(R.color.white), 60)
    }

    override fun initView() {
        setContentView(R.layout.activity_termsdetail)
        type=intent.extras.getString("type")
    }

    override fun initEvents() {
        iv_termsdetail_back.setOnClickListener(this)
    }

    override fun initData() {
        pid=intent.extras.getString("pid")
        pname=intent.extras.getString("pname")
        dbmanager = DBManager.getInstance(this)
        val list: MutableList<TermsBean> = dbmanager?.getSomeTermsDetail(pid,type)!!
        adapter = TermsDetailAdapter(list, this)
        rv_termsdetail.layoutManager = LinearLayoutManager(this)
        rv_termsdetail.adapter = adapter
        adapter?.setOnItemClickListener { _, _ ->

        }
        tv_termsdetail_title.text=pname
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.iv_termsdetail_back->finish()
        }
    }
}