package com.micropole.inventorysystem.ui.shoppingmall.mvp.presenter

import com.micropole.inventorysystem.R
import com.micropole.inventorysystem.ui.shoppingmall.mvp.contract.EvalationListContract
import com.xx.baseutilslibrary.extensions.ui

/**
 * @ClassName       EvaluationListPresent
 * @Description     todo
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/12/13 17:18
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
class EvaluationListPresent : EvalationListContract.Present() {
    override fun evalutionList(id: String, page: Int) {
        getView()?.showLoadingDialog(getView()?.getResString(R.string.loading))
        getModel().evalutionList(id, page).ui({
            getView()?.dismissLoadingDialog()
            getView()?.evaluationList(it.data!!)
        },{
            getView()?.dismissLoadingDialog()
            getView()?.showToast(it)
        })
    }
}