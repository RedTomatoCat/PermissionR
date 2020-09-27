package com.redcattomato.permissionr.core.imp

import com.redcattomato.permissionr.core.PermissionRInfo

/**
 * Create on 2020/9/18
 *
 * @author redcattomato
 * @version 1.0.0
 **/
interface PermissionRListener {

    fun onReady(permissionInfoList: List<PermissionRInfo>)
    fun onFailed(permissionList: List<String>)                 //No PERMISSION_GRANTED
    fun allSuccess()                                           //All PERMISSION_GRANTED

}