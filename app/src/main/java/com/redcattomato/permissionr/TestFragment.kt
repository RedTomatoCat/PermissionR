package com.redcattomato.permissionr

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.redcattomato.permissionr.core.PermissionRBuilder
import com.redcattomato.permissionr.core.PermissionRInfo
import com.redcattomato.permissionr.core.imp.PermissionRListener

/**
 * Create on 2020/10/19
 *
 * @author redcattomato
 * @version 1.0.0
 **/
class TestFragment : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_fragment)
        supportFragmentManager.beginTransaction().replace(R.id.content_view_v, TestFra()).commitAllowingStateLoss()
    }

    class TestFra : Fragment() {

        override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = LayoutInflater.from(container!!.context).inflate(R.layout.frag_test_permission_request, container, false)
            view.findViewById<View>(R.id.request_permission_bt).setOnClickListener {
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
                            Toast.makeText(context, "授权成功。", Toast.LENGTH_SHORT).show()
                        }

                    })
                    .useDialog(true)
                    .must(true)
                    .build()
            }
            return view
        }

    }
}