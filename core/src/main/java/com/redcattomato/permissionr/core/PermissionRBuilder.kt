package com.redcattomato.permissionr.core

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.redcattomato.permissionr.core.imp.PermissionRListener

/**
 * Create on 2020/9/18
 *
 * @author redcattomato
 * @version 1.0.0
 **/
class PermissionRBuilder(@NonNull context: AppCompatActivity) {

    var permissionList = mutableListOf<String>()    //所有请求权限
    val grantedList = mutableListOf<String>()       //已授权权限
    var deniedList = mutableListOf<String>()        //未授权权限
    var listener: PermissionRListener? = null
    var must = false                                //true 必须同意权限
    var useDialog = true                            //是否显示权限列表提示弹窗

    val mcontext = context
    var permissionInfoList = mutableListOf<PermissionRInfo>()          //权限信息列表

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