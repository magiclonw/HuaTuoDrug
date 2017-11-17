package com.magiclon.huatuodrug.model

/**
 * 作者：MagicLon
 * 时间：2017/10/30 030
 * 邮箱：1348149485@qq.com
 * 描述：
 */
class TermsBean {
    var id = ""
    var pid = ""
    var pname = ""
    var content = ""

    constructor(pid: String, pname: String) {
        this.pid = pid
        this.pname = pname
    }

    constructor(content: String) {
        this.content = content
    }

    override fun toString(): String {
        return "TermsBean(id='$id', pid='$pid', pname='$pname', content='$content')"
    }
}