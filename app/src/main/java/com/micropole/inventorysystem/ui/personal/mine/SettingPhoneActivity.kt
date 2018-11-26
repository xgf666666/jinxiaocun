package com.micropole.inventorysystem.ui.personal.mine

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.micropole.inventorysystem.R
import com.xx.baseuilibrary.mvp.BaseMvpViewActivity
import kotlinx.android.synthetic.main.fragment_personal.*
/**
* @ClassName       SettingPhoneActivity
* @Description     设计手机号码
* @Author          xiaoguangfei
* @Sign            。。。
* @Date            2018/11/22 10:23
* @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
*/
class SettingPhoneActivity : BaseMvpViewActivity() {
    /**
     * 获取布局资源文件id
     *
     * @return 布局资源文件id
     */
    override fun getActivityLayoutId(): Int =R.layout.activity_setting_phone

    /**
     * 初始化数据状态
     */
    override fun initData() {
        tv_title.text=getString(R.string.safety_phone_setting)
    }

    /**
     * 初始化事件
     */
    override fun initEvent() {
    }
    companion object {
        fun startSettingPhoneActivity(context:Context){
            var intent=Intent(context,SettingPhoneActivity::class.java)
            context.startActivity(intent)
        }
    }

}
