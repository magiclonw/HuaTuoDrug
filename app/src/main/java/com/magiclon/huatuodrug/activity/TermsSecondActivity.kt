package com.magiclon.huatuodrug.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.magiclon.huatuodrug.R
import com.magiclon.huatuodrug.adapter.TermsAdapter
import com.magiclon.huatuodrug.db.DBManager
import com.magiclon.huatuodrug.model.TermsBean
import com.magiclon.huatuodrug.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_terms.*

class TermsSecondActivity : BaseActivity(), View.OnClickListener {
    private var dbmanager: DBManager? = null
    private var adapter: TermsAdapter? = null
    var pid = ""
    var pname = ""
    var type=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.darkMode(this)
    }

    override fun initView() {
        setContentView(R.layout.activity_terms)
        pid = intent.extras.getString("pid")
        type = intent.extras.getString("type")
        pname = intent.extras.getString("pname")
    }

    override fun initEvents() {
        iv_terms_back.setOnClickListener(this)
        iv_terms_search.setOnClickListener(this)
    }

    override fun initData() {
        tv_terms_title.text = pname
        dbmanager = DBManager.getInstance(this)
        val list: MutableList<TermsBean> = dbmanager?.getAllSecondTerms(type,pid)!!
        adapter = TermsAdapter(list, this, type)
        rv_terms.layoutManager = LinearLayoutManager(this)
        rv_terms.adapter = adapter
        adapter?.setOnItemClickListener { view, position ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(Intent(this@TermsSecondActivity, TermsDetailActivity::class.java).putExtra("pid", list[position].pid).putExtra("pname", list[position].pname).putExtra("type", type), ActivityOptions.makeSceneTransitionAnimation(this, view, "sharedviewtitle").toBundle())
            } else {
                startActivity(Intent(this@TermsSecondActivity, TermsDetailActivity::class.java).putExtra("pid", list[position].pid).putExtra("pname", list[position].pname).putExtra("type", type))
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            iv_terms_back -> super.onBackPressed()
            iv_terms_search -> startActivity(Intent(this@TermsSecondActivity, TermsSearchActivity::class.java).putExtra("pid",pid).putExtra("type", "second"+type))
        }
    }
}
