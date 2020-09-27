package com.redcattomato.permissionr

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.redcattomato.permissionr.core.PermissionRBuilder
import com.redcattomato.permissionr.core.PermissionRInfo
import com.redcattomato.permissionr.core.imp.PermissionRListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PermissionRBuilder(this)
            .permission(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
            ).setListener(object : PermissionRListener {
                    override fun onReady(permissionInfoList: List<PermissionRInfo>) {
                        //Do your thing
                    }

                    override fun onFailed(permissionList: List<String>) {
                        //Do your thing
                    }

                    override fun allSuccess() {
                        //Do your thing
                    }

                })
//                .useDialog(false)
                .must(true)
                .build()
    }


}