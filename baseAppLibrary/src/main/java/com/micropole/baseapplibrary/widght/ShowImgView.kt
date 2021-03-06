package com.micropole.baseapplibrary.widght

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.ToastUtils
import com.micropole.baseapplibrary.R
import com.micropole.baseapplibrary.util.ImageChooseHelper
import com.xx.baseutilslibrary.extensions.loadImag
import java.io.File

/**
 * @ClassName       ShowImgView
 * @Description     todo 多行 add img
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/16 15:55
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
class ShowImgView(context: Context,attributeSet: AttributeSet) : LinearLayout(context,attributeSet) {

    var maxCount = 4  //最大图片数
        set(value) {
            if (value != field) {
                removeAllViews()
                createAddView()
            }
            field = value
        }
    var maxLine = 1   //最大行数
        set(value) {
            if (value != field) {
                removeAllViews()
                createAddView()
            }
            field = value
        }

    var isAdd = true   //是否可在 add img
    var addDrawable : Int = 0
    set(value) {
        field = value
        removeAllViews()
        createAddView()
    }
    var deleteDrawable : Int = 0
    var mImgList = arrayListOf<String>()
    private set
    /*get() {
        for (i in mImgList.iterator()){
            Log.i("img_list",i)
        }
        return field
    }*/

    var imageChooseHelper : ImageChooseHelper? = null
    var addAction : (view : View) -> Unit = {}
    set(value) {  //设置了回调后，不再调用默认对话框
        imageChooseHelper = null
        field = value
    }

    var mListener : CLICKListener? = null

    init {
        if (childCount == 0) createAddView()
    }

    override fun addView(child: View?) {
        post {
            if (childCount > maxCount){
                return@post
            }
            if (childCount > 0 && child?.tag != true){
                mImgList.add("")
                removeViewAt(childCount - 1)
            }
            if (!isAdd && child is FrameLayout && child.tag == true){
                return@post
            }
            val i = maxCount / maxLine  //每行显示的图片数
            val i1 = measuredWidth / i  //图片 width
            val i2 = measuredHeight / maxLine //图片 height
            val params = LinearLayout.LayoutParams(i1,i2)
            params.rightMargin = 10
            child?.layoutParams = params
            super.addView(child)
        }
    }

    override fun removeViewAt(index: Int) {
        if (mImgList.size > index) mImgList.removeAt(index)
        super.removeViewAt(index)
        if (!isAdd){
            createAddView()
        }
    }

    override fun removeView(view: View?) {
        val i = indexOfChild(view)
        mImgList.removeAt(i)
        super.removeView(view)
        if (!isAdd){
            createAddView()
        }
    }

    @SuppressLint("NewApi")
    private fun createAddView(){
        isAdd = true
        val frameLayout = FrameLayout(context)
        frameLayout.setBackgroundDrawable(this.context.resources.getDrawable(R.drawable.shape_gray_r3))
        val imageView = TextView(this.context)
        if (addDrawable != 0) imageView.setCompoundDrawablesRelativeWithIntrinsicBounds(null,context.resources.getDrawable(addDrawable),null,null)
        else imageView.setCompoundDrawablesRelativeWithIntrinsicBounds(null,context.resources.getDrawable(android.R.drawable.ic_input_add),null,null)
        frameLayout.tag = true
        imageView.textSize = 11f
        imageView.setTextColor(Color.parseColor("#999999"))
        imageView.setText(R.string.add_photo)
        imageView.gravity = Gravity.CENTER
        imageView.setOnClickListener {
            if (imageChooseHelper == null){
                addAction.invoke(it)
            }else{
                ImageChooseDialog(this.context){
                    if (it == 0){  //拍照
                        imageChooseHelper?.takePhoto()
                    }else if (it == 1){
                        imageChooseHelper?.selectPicker()
                    }
                }.show()
            }
            if (mListener != null){
                mListener?.clickListener()
            }
        }
        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER
        frameLayout.addView(imageView,layoutParams)
        addView(frameLayout)
    }

    fun addShowImgView(url:String,id:Int = 0){
        isAdd = false
        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        if (id != 0) imageView.setImageResource(id)
        else imageView.loadImag(url)
        imageView.tag = true
        this.addView(imageView)
    }

    fun addImgView(path : String){
        val s = EncodeUtils.base64Encode2String(File(path).readBytes())
        mImgList.add(s)
        createImgView(this.context,path)
    }

    private fun createImgView(context: Context,path : String){
        val frameLayout = FrameLayout(context)

        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.FIT_XY
        imageView.setImageURI(Uri.fromFile(File(path)))
        frameLayout.addView(imageView,FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT))

        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.RIGHT or Gravity.TOP
        params.topMargin = -5
        params.rightMargin = -5
        frameLayout.addView(createDeleteView(context,frameLayout),params)

        this.addView(frameLayout)
        if (childCount < maxCount){
            createAddView()
        }else{
            isAdd = false
        }
    }

    private fun  createDeleteView(context: Context,tag : View) : View{
        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        imageView.tag = tag
        if (deleteDrawable != 0) imageView.setImageResource(deleteDrawable)
        else imageView.setImageResource(android.R.drawable.ic_delete)
        imageView.setOnClickListener {
            this@ShowImgView.removeView(it.tag as View)
        }
        return imageView
    }

    interface CLICKListener{
        fun clickListener()
    }

}