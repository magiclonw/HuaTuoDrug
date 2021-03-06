package com.magiclon.huatuodrug.activity

import android.os.Bundle
import android.view.View
import com.magiclon.huatuodrug.R
import com.magiclon.huatuodrug.db.DBManager
import com.magiclon.huatuodrug.model.CommonDrugBean
import com.magiclon.huatuodrug.util.AmapTTSController
import com.magiclon.huatuodrug.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_diseadetail.*


class DiseaseDetailActivity : BaseActivity(), View.OnClickListener {

    private var dbmanager: DBManager? = null
    var id = ""
    private var commondrugbean: CommonDrugBean? = null
    private var amapTTSController: AmapTTSController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        StatusBarUtil.setColorForSwipeBack(this, resources.getColor(R.color.white), 60)
    StatusBarUtil.darkMode(this)
    }

    override fun initView() {
        id = intent.extras.getString("id")
        setContentView(R.layout.activity_diseadetail)
        amapTTSController = AmapTTSController.getInstance(applicationContext)
        amapTTSController?.init()
    }

    override fun initEvents() {
        iv_disedetail_back.setOnClickListener(this)
        tv_disedetail_content.setOnClickListener {
            amapTTSController?.stopSpeaking()
            amapTTSController?.onGetNavigationText(commondrugbean?.content)
        }
        tv_disedetail_miandrug.setOnClickListener {
            amapTTSController?.stopSpeaking()
            amapTTSController?.onGetNavigationText(commondrugbean?.maindrug)
        }
        tv_disedetail_assistdrug.setOnClickListener {
            amapTTSController?.stopSpeaking()
            amapTTSController?.onGetNavigationText(commondrugbean?.assistdrug)
        }
        tv_disedetail_nutrition.setOnClickListener {
            amapTTSController?.stopSpeaking()
            amapTTSController?.onGetNavigationText(commondrugbean?.nutrition)
        }
        tv_disedetail_tea.setOnClickListener {
            amapTTSController?.stopSpeaking()
            amapTTSController?.onGetNavigationText(commondrugbean?.tea)
        }
        tv_disedetail_mathine.setOnClickListener {
            amapTTSController?.stopSpeaking()
            amapTTSController?.onGetNavigationText(commondrugbean?.mathine)
        }

    }

    override fun initData() {
        dbmanager = DBManager.getInstance(this)
        commondrugbean = dbmanager?.getDiseaseDetail(id)
        tv_disedetail_content.text = commondrugbean?.content
        tv_disedetail_title.text = commondrugbean?.title
        tv_disedetail_miandrug.text = commondrugbean?.maindrug
        tv_disedetail_assistdrug.text = commondrugbean?.assistdrug
        tv_disedetail_nutrition.text = commondrugbean?.nutrition
        tv_disedetail_tea.text = commondrugbean?.tea
        tv_disedetail_mathine.text = commondrugbean?.mathine
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_disedetail_back -> super.onBackPressed()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        amapTTSController?.destroy()
    }
}

