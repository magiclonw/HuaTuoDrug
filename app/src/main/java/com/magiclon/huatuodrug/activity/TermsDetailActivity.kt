package com.magiclon.huatuodrug.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.magiclon.huatuodrug.R
import com.magiclon.huatuodrug.adapter.TermsDetailAdapter
import com.magiclon.huatuodrug.db.DBManager
import com.magiclon.huatuodrug.model.TermsBean
import com.magiclon.huatuodrug.util.AmapTTSController
import com.magiclon.huatuodrug.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_termsdetail.*

class TermsDetailActivity : BaseActivity(), View.OnClickListener {
    private var dbmanager: DBManager? = null
    private var adapter: TermsDetailAdapter? = null
    private var pid=""
    private var pname=""
    var type=""
    private var amapTTSController: AmapTTSController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.darkMode(this)
    }

    override fun initView() {
        setContentView(R.layout.activity_termsdetail)
        type=intent.extras.getString("type")
        amapTTSController = AmapTTSController.getInstance(applicationContext)
        amapTTSController?.init()
    }

    override fun initEvents() {
        iv_termsdetail_back.setOnClickListener(this)
    }

    override fun initData() {
        pid=intent.extras.getString("pid")
        pname=intent.extras.getString("pname")
        dbmanager = DBManager.getInstance(this)
        var list: MutableList<TermsBean> = ArrayList()

        if(type=="3"){
            list=dbmanager?.getSomeTermsSecondDetail(pid,type)!!
        }else{
            list= dbmanager?.getSomeTermsDetail(pid,type)!!
        }

        adapter = TermsDetailAdapter(list, this)
        rv_termsdetail.layoutManager = LinearLayoutManager(this)
        rv_termsdetail.adapter = adapter
        adapter?.setOnItemClickListener { _, postion ->
            amapTTSController?.stopSpeaking()
            amapTTSController?.onGetNavigationText(list[postion].content)
        }
        tv_termsdetail_title.text=pname
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.iv_termsdetail_back->super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        amapTTSController?.destroy()
    }
}