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

class TermsActivity : BaseActivity(), View.OnClickListener {
    private var dbmanager: DBManager? = null
    private var adapter: TermsAdapter? = null
    var type = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.darkMode(this)
    }

    override fun initView() {
        setContentView(R.layout.activity_terms)
        type = intent.extras.getString("type")
    }

    override fun initEvents() {
        iv_terms_back.setOnClickListener(this)
        iv_terms_search.setOnClickListener(this)
    }

    override fun initData() {
        if (type == "2") {
            tv_terms_title.text = "常用药品别名汇总"
        } else if (type == "3") {
            tv_terms_title.text = "专题培训"
        }
        dbmanager = DBManager.getInstance(this)
        val list: MutableList<TermsBean> = dbmanager?.getAllTerms(type)!!
        adapter = TermsAdapter(list, this, type)
        rv_terms.layoutManager = LinearLayoutManager(this)
        rv_terms.adapter = adapter
        adapter?.setOnItemClickListener { view, position ->
            if (type == "3") {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(Intent(this@TermsActivity, TermsSecondActivity::class.java).putExtra("pid", list[position].pid).putExtra("pname", list[position].pname).putExtra("type", type), ActivityOptions.makeSceneTransitionAnimation(this, view, "sharedviewterms").toBundle())
                } else {
                    startActivity(Intent(this@TermsActivity, TermsSecondActivity::class.java).putExtra("pid", list[position].pid).putExtra("pname", list[position].pname).putExtra("type", type))
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(Intent(this@TermsActivity, TermsDetailActivity::class.java).putExtra("pid", list[position].pid).putExtra("pname", list[position].pname).putExtra("type", type), ActivityOptions.makeSceneTransitionAnimation(this, view, "sharedviewtitle").toBundle())
                } else {
                    startActivity(Intent(this@TermsActivity, TermsDetailActivity::class.java).putExtra("pid", list[position].pid).putExtra("pname", list[position].pname).putExtra("type", type))
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            iv_terms_back -> super.onBackPressed()
            iv_terms_search -> startActivity(Intent(this@TermsActivity, TermsSearchActivity::class.java).putExtra("pid","").putExtra("type", type))
        }
    }
}
