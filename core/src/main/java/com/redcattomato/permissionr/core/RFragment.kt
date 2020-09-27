package com.redcattomato.permissionr.core

import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment

/**
 * Create on 2020/9/18
 *
 * @author redcattomato
 * @version 1.0.0
 **/
class RFragment(permissionR: PermissionR) : Fragment() {

    companion object {
        const val PERMISSION_CODE = 100
    }

    private val mPermissionR = permissionR

    fun startPermission(){
        val permissionList = mPermissionR.mBuilder.permissionList
        requestPermissions(permissionList.toTypedArray(), PERMISSION_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {
        val grantedList = mutableListOf<String>()
        val deniedList = mutableListOf<String>()
        val deniedAppOpList = mutableListOf<String>()
        when (requestCode) {
            PERMISSION_CODE -> {
                for ((index, result) in grantResults.withIndex()) {
                    when (result) {
                        PermissionChecker.PERMISSION_GRANTED -> {
                            //TODO FINISH THIS STATE
                            //        shouldShowRequestPermissionRationale()
                            grantedList.add(permissions[index])

                        }
                        PermissionChecker.PERMISSION_DENIED -> {
                            deniedList.add(permissions[index])
                        }
                        else -> {//This PERMISSION_DENIED_APP_OP
                            deniedAppOpList.add(permissions[index])
                        }
                    }
                }
            }
        }
        when (grantedList.size == permissions.size) {
            true -> {
                mPermissionR.mBuilder.listener!!.allSuccess()
                mPermissionR.onFinish()
            }
            else -> {
                deniedList.addAll(deniedAppOpList).apply {
                    mPermissionR.mBuilder.deniedList = deniedList
                }
                mPermissionR.mBuilder.permissionList.removeAll(grantedList)
                if (mPermissionR.mBuilder.must) {
                    startPermission()
                } else {
                    mPermissionR.mBuilder.listener!!.onFailed(mPermissionR.mBuilder.permissionList)
                }
            }
        }


    }


}