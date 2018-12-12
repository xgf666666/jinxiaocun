package com.micropole.inventorysystem.ui.shoppingmall.mvp.presenter

import com.micropole.inventorysystem.R
import com.micropole.inventorysystem.ui.shoppingmall.mvp.contract.CarContract
import com.xx.baseutilslibrary.extensions.ui

/**
 * @ClassName       NewsPresent
 * @Description     todo
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/21 11:48
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
class CarPresent : CarContract.Presenter() {
    override fun updateCar(sh_id: String, pro_num: String,position:Int,isAdd:Boolean) {
        getModel().updateCar(sh_id,pro_num).ui({
            getView()?.updateCar(it.data!!,position,isAdd)
        },{
            getView()?.showToast(it)
        })
    }

    override fun deleteCar(sh_id: String,position:Int) {
        getModel().deleteCar(sh_id).ui({
            getView()?.deleteSucceeful(it.msg!!,position)
        },{
            getView()?.showToast(it)

        })
    }

    override fun car() {
        getView()?.showLoadingDialog(getView()?.getResString(R.string.loading))
        getModel().car().ui({
            getView()?.dismissLoadingDialog()
            getView()?.carSucceeful(it.data!!)

        },{
            getView()?.dismissLoadingDialog()
            getView()?.showToast(it)
        })
    }

    override fun createModel()=CarContract.Model()

}