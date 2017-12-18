package com.magiclon.huatuodrug.activity

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.magiclon.huatuodrug.R
import com.magiclon.huatuodrug.adapter.HistoryAdapter
import com.magiclon.huatuodrug.adapter.TermsDetailAdapter
import com.magiclon.huatuodrug.db.DBManager
import com.magiclon.huatuodrug.model.TermsBean
import com.magiclon.huatuodrug.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_termsearch.*
import java.util.*

class TermsSearchActivity : BaseActivity(), View.OnClickListener {
    var hadapter: HistoryAdapter? = null
    var hlist = ArrayList<String>()
    var dbManager: DBManager? = null
    var type = "1"
    private var list = ArrayList<TermsBean>()
    private var adapter: TermsDetailAdapter? = null
    var pid = ""
    override fun initView() {
        setContentView(R.layout.activity_termsearch)
        StatusBarUtil.darkMode(this)
        dbManager = DBManager.getInstance(this)
        type = intent.extras.getString("type")
        pid = intent.extras.getString("pid")
    }

    override fun initEvents() {
        iv_search_back.setOnClickListener(this)
        edt_search.setOnEditorActionListener(TextView.OnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_UNSPECIFIED || i == EditorInfo.IME_ACTION_GO) {
                (this@TermsSearchActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                        .hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                val str_search = edt_search.text.toString().trim()
                if (str_search != "") {
                    dbManager?.insertHistory(edt_search.text.toString().trim(), type)
                    submit(str_search)
                }
                return@OnEditorActionListener true
            }
            false
        })
    }


    override fun initData() {
        if (type == "2") {
            edt_search.hint = "搜索药品、别名"
        } else if (type == "3") {
            edt_search.hint = "搜索药品、症状、专题"
        }
        /*----------------历史纪录----------------*/
        hlist.addAll(dbManager?.getAllHistory(type)!!)
        hadapter = HistoryAdapter(hlist, this)
        rv_history.layoutManager = LinearLayoutManager(this)
        rv_history.adapter = hadapter
        hadapter?.setOnItemClickListener { _, position ->
            (this@TermsSearchActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            submit(hlist[position])
        }
        hadapter?.setOnDeleteLister(object : HistoryAdapter.OnDeleteLister {
            override fun onDeleteOne(name: String) {
                dbManager?.deleteOneHistory(name, type)
                hlist.clear()
                hlist.addAll(dbManager?.getAllHistory(type)!!)
                hadapter?.notifyDataSetChanged()
                if (hlist.size == 0) {
                    ll_search_empty.visibility = View.VISIBLE
                } else {
                    rv_history.visibility = View.VISIBLE
                }
            }

            override fun onDeleteAll() {
                dbManager?.deleteAllHistory(type)
                hlist.clear()
                hadapter?.notifyDataSetChanged()
                ll_search_empty.visibility = View.VISIBLE
            }
        })
        if (hlist.size == 0) {
            ll_search_empty.visibility = View.VISIBLE
        } else {
            rv_history.visibility = View.VISIBLE
        }
        /*----------------查询的数据----------------*/
        adapter = TermsDetailAdapter(list, this, true)
        rv_search.layoutManager = LinearLayoutManager(this)
        rv_search.adapter = adapter
        adapter?.setOnItemClickListener { _, _ ->

        }

    }

    private fun submit(str: String) {
        list.clear()
        if (type == "second3") {
            list.addAll(dbManager?.getSomeTerms(str, type, pid)!!)
        } else {
            list.addAll(dbManager?.getSomeTerms(str, type, "")!!)
        }

        adapter?.notifyDataSetChanged()
        rv_search.visibility = View.VISIBLE
        rv_history.visibility = View.GONE
        if (list.size > 0) {
            ll_search_empty.visibility = View.GONE
        } else {
            ll_search_empty.visibility = View.VISIBLE
        }

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_search_back -> finish()
        }
    }

}