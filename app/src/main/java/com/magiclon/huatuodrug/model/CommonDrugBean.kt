package com.magiclon.huatuodrug.model

/**
 * 作者：MagicLon
 * 时间：2017/10/25 025
 * 邮箱：1348149485@qq.com
 * 描述：
 */

class CommonDrugBean {
    var id = ""
    var title = ""
    var content = ""
    var maindrug = ""
    var assistdrug = ""
    var nutrition = ""
    var tea = ""
    var mathine = ""

    constructor(id: String, title: String, content: String, maindrug: String, assistdrug: String, nutrition: String, tea: String, mathine: String) {
        this.id = id
        this.title = title
        this.content = content
        this.maindrug = maindrug
        this.assistdrug = assistdrug
        this.nutrition = nutrition
        this.tea = tea
        this.mathine = mathine
    }

    constructor(id:String,title: String, content: String) {
        this.id=id
        this.title = title
        this.content = content
    }

    override fun toString(): String {
        return "CommonDrugBean(id='$id', title='$title', content='$content', maindrug='$maindrug', assistdrug='$assistdrug', nutrition='$nutrition', tea='$tea', mathine='$mathine')"
    }

}
