package com.redcattomato.permissionr.core

import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable

/**
 * Create on 2020/9/18
 *
 * @author redcattomato
 * @version 1.0.0
 **/
class PermissionRInfo() : Parcelable {
    var permissionGroup = ""     //权限组
    var permissionName = ""      //权限名称
    var description    = ""      //权限描述
    var icon : Drawable ?= null  //权限图标

    constructor(parcel: Parcel) : this() {
        permissionGroup = parcel.readString().toString()
        permissionName = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(permissionGroup)
        parcel.writeString(permissionName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PermissionRInfo> {
        override fun createFromParcel(parcel: Parcel): PermissionRInfo {
            return PermissionRInfo(parcel)
        }

        override fun newArray(size: Int): Array<PermissionRInfo?> {
            return arrayOfNulls(size)
        }
    }

}