package com.magiclon.huatuodrug.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.magiclon.huatuodrug.R
import com.magiclon.huatuodrug.adapter.HistoryAdapter
import com.magiclon.huatuodrug.adapter.MainAdapter
import com.magiclon.huatuodrug.db.DBManager
import com.magiclon.huatuodrug.model.CommonDrugBean
import com.magiclon.huatuodrug.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*

class SearchActivity : BaseActivity(), View.OnClickListener {
    var hadapter: HistoryAdapter? = null
    var hlist = ArrayList<String>()
    var dbManager: DBManager? = null
    private var list = ArrayList<CommonDrugBean>()
    private var adapter: MainAdapter? = null

    @Suppress("DEPRECATION")
    override fun initView() {
        setContentView(R.layout.activity_search)
        dbManager = DBManager.getInstance(this)
        StatusBarUtil.setColorForSwipeBack(this, resources.getColor(R.color.colorPrimary), 0)
    }

    override fun initEvents() {
        iv_search_back.setOnClickListener(this)
        edt_search.setOnEditorActionListener(TextView.OnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_UNSPECIFIED || i == EditorInfo.IME_ACTION_GO) {
                (this@SearchActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                        .hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                val str_search = edt_search.text.toString().trim()
                if (str_search != "") {
                    dbManager?.insertHistory(edt_search.text.toString().trim(), "0")
                    submit(str_search)
                }
                return@OnEditorActionListener true
            }
            false
        })
    }


    override fun initData() {
        /*----------------历史纪录----------------*/
        hlist.addAll(dbManager?.getAllHistory("0")!!)
        hadapter = HistoryAdapter(hlist, this)
        rv_history.layoutManager = LinearLayoutManager(this)
        rv_history.adapter = hadapter
        hadapter?.setOnItemClickListener { _, position ->
            (this@SearchActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            submit(hlist[position])
        }
        hadapter?.setOnDeleteLister(object : HistoryAdapter.OnDeleteLister {
            override fun onDeleteOne(name: String) {
                dbManager?.deleteOneHistory(name, "0")
                hlist.clear()
                hlist.addAll(dbManager?.getAllHistory("0")!!)
                hadapter?.notifyDataSetChanged()
                if (hlist.size == 0) {
                    ll_search_empty.visibility = View.VISIBLE
                } else {
                    rv_history.visibility = View.VISIBLE
                }
            }

            override fun onDeleteAll() {
                dbManager?.deleteAllHistory("0")
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
        adapter = MainAdapter(list, this)
        rv_search.layoutManager = LinearLayoutManager(this)
        rv_search.adapter = adapter
        adapter?.setOnItemClickListener { view1, view2, position ->
            var first = android.support.v4.util.Pair<View, String>(view1, ViewCompat.getTransitionName(view1))
            var second = android.support.v4.util.Pair<View, String>(view2, ViewCompat.getTransitionName(view2))
            val transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this@SearchActivity, first, second)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(Intent(this@SearchActivity, DiseaseDetailActivity::class.java).putExtra("id", list[position].id), transitionActivityOptions.toBundle())
            }else{
                startActivity(Intent(this@SearchActivity, DiseaseDetailActivity::class.java).putExtra("id", list[position].id))
            }
        }

    }

    private fun submit(str: String) {
        list.clear()
        list.addAll(dbManager?.getSomeCommonDrug(str)!!)
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
