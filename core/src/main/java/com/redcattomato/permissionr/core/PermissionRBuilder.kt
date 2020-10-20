package com.redcattomato.permissionr.core

import android.app.Dialog
import android.content.Context
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.redcattomato.permissionr.core.imp.PermissionRListener

/**
 * Create on 2020/9/18
 *
 * @author redcattomato
 * @version 1.0.0
 **/
class PermissionRBuilder {

    internal var permissionList = mutableListOf<String>()    //所有请求权限
    internal val grantedList = mutableListOf<String>()       //已授权权限
    internal var deniedList = mutableListOf<String>()        //未授权权限
    internal var listener: PermissionRListener? = null
    internal var must = false                                //true 必须同意权限
    internal var useDialog = true                            //是否显示权限列表提示弹窗

    internal var mcontext: Context ?= null
    internal var mfragment: Fragment ?= null
    internal var isFragmentContext = false
    internal var permissionInfoList = mutableListOf<PermissionRInfo>()          //权限信息列表

    constructor(@NonNull context: AppCompatActivity){
        mcontext = context
    }

    constructor(@NonNull fragment: DialogFragment){
        mcontext = fragment.context
        mfragment = fragment
        isFragmentContext = true
    }

    constructor(@NonNull fragment: Fragment){
        mcontext = fragment.context
        mfragment = fragment
        isFragmentContext = true
    }

    constructor(@NonNull dialog: Dialog){
        mcontext = dialog.context
        isFragmentContext = true
    }

    /**
     * 添加申请权限
     */
    fun permission(@NonNull vararg permission: String): PermissionRBuilder {
        permissionList = permission.toMutableList()
        return this@PermissionRBuilder
    }

    private fun putGrantedList(@NonNull vararg permission: String): PermissionRBuilder {
        grantedList.addAll(permission)
        return this@PermissionRBuilder
    }

    fun setListener(@Nullable listener0: PermissionRListener): PermissionRBuilder {
        listener = listener0
        return this@PermissionRBuilder
    }

    fun must(var0: Boolean): PermissionRBuilder {
        must = var0
        return this@PermissionRBuilder
    }

    fun useDialog(use: Boolean): PermissionRBuilder {
        useDialog = use
        return this@PermissionRBuilder
    }

    fun build() = PermissionR(this)

}