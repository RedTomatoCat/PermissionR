package com.redcattomato.permissionr.core

import android.content.pm.PackageManager
import android.text.TextUtils
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.FragmentManager
import com.redcattomato.permissionr.core.imp.PermissionDialogListener
import com.redcattomato.permissionr.core.ui.PermissionResultDialog

/**
 * Create on 2020/9/18
 *
 * @author redcattomato
 * @version 1.0.0
 **/
class PermissionR(builder: PermissionRBuilder) {

    val mBuilder = builder
    private var mPermissionResultDialog: PermissionResultDialog?=null

    init {
        val permissionGroupList = mutableListOf<String>()
        val permissionInfoList = mutableListOf<PermissionRInfo>()
        val removeList = mutableListOf<String>()
        for (permission in builder.permissionList) {
            val permissionInfo = builder.mcontext.packageManager.getPermissionInfo(permission, 0)
            var perGroup = permissionInfo.group
            if (perGroup != null && perGroup.isNotEmpty()) {
                if(ContextCompat.checkSelfPermission(builder.mcontext, permission) == PermissionChecker.PERMISSION_GRANTED){
                    removeList.add(permission)
                } else {
                    if (!permissionGroupList.contains(perGroup)) {
                        if (TextUtils.equals(UNKNOWN_PERMISSION, perGroup)) perGroup = permission
                        permissionGroupList.add(perGroup)
                        permissionInfoList.add(PermissionRInfo().apply {
                            permissionGroup = perGroup
                            try {
                                permissionName = mBuilder.mcontext.packageManager.getPermissionGroupInfo(permissionGroup, 0).loadLabel(mBuilder.mcontext.packageManager).toString()
                                description = mBuilder.mcontext.packageManager.getPermissionGroupInfo(permissionGroup, 0).loadDescription(mBuilder.mcontext.packageManager)!!.toString()
                                icon = mBuilder.mcontext.packageManager.getPermissionGroupInfo(permissionGroup, 0).loadIcon(mBuilder.mcontext.packageManager)
                            } catch (e: PackageManager.NameNotFoundException) {
                                permissionName = mBuilder.mcontext.packageManager.getPermissionInfo(permissionGroup, 0).loadLabel(mBuilder.mcontext.packageManager).toString()
                                description = mBuilder.mcontext.packageManager.getPermissionInfo(permissionGroup, 0).loadDescription(mBuilder.mcontext.packageManager)!!.toString()
                                icon = mBuilder.mcontext.packageManager.getPermissionInfo(permissionGroup, 0).loadIcon(mBuilder.mcontext.packageManager)
                            }
                        })
                    } else {
                        removeList.add(permission)
                    }
                }
            }
        }
        builder.permissionList.removeAll(removeList)
        builder.permissionInfoList = permissionInfoList
        if (builder.listener != null)
            if (permissionGroupList.isEmpty()) {
                //unknow ERR
                builder.listener!!.onFailed(mutableListOf())
            } else {
                val life =
                    mBuilder.mcontext.supportFragmentManager.findFragmentByTag("observer_life")
                if (life != null) mBuilder.mcontext.supportFragmentManager.beginTransaction()
                    .remove(life)
                mBuilder.mcontext.supportFragmentManager.beginTransaction()
                    .add(RFragment(this), "observer_life").commitNowAllowingStateLoss()
                Log.e(TAG, "onReady")
                builder.listener!!.onReady(mBuilder.permissionInfoList)
                if (mBuilder.useDialog) {
                    show(mBuilder.mcontext.supportFragmentManager, mBuilder.permissionInfoList, mBuilder.must)
                } else {
                    request()
                }
            }
    }

    fun show(manager: FragmentManager, permissionInfoList: List<PermissionRInfo>, must: Boolean) {
        onFinish()
        mPermissionResultDialog = PermissionResultDialog.show(manager, permissionInfoList, must).setPermissionDialogListener(object :PermissionDialogListener{
            override fun onCancel() = Unit
            override fun onOk() = request()
        })
    }

    private fun request() {
        Log.e(TAG, "request")
        val rFragment = mBuilder.mcontext.supportFragmentManager.findFragmentByTag("observer_life") as RFragment
        rFragment.startPermission()
    }


    fun onFinish(){
        if(mPermissionResultDialog != null && mPermissionResultDialog!!.isVisible) mPermissionResultDialog!!.dismiss()
    }

    companion object{
        const val TAG = "PermissionR"
        const val UNKNOWN_PERMISSION = "android.permission-group.UNDEFINED"
    }


}