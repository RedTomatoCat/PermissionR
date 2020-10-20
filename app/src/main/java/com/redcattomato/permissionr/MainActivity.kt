package com.redcattomato.permissionr

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SimpleAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginBottom
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.redcattomato.permissionr.core.PermissionRBuilder
import com.redcattomato.permissionr.core.PermissionRInfo
import com.redcattomato.permissionr.core.imp.PermissionRListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
    }

    private fun initData() {
        findViewById<RecyclerView>(R.id.content_rc).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MListAdapter(context)
        }
    }

    fun requestPermission(){
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
                    Toast.makeText(this@MainActivity, "授权成功。", Toast.LENGTH_SHORT).show()
                }

            })
            .useDialog(true)
            .must(true)
            .build()
    }

    class MViewHolder(view: Button) : RecyclerView.ViewHolder(view) {
        internal val title  = view
    }

    inner class MListAdapter(context: Context) : RecyclerView.Adapter<MViewHolder>() {
        var dataList : MutableList<String> ?= null

        val mcontext = context

        init {
            dataList = context.resources.getStringArray(R.array.permissionr_action_list).toMutableList()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MViewHolder(Button(mcontext).apply {
            textSize = 16f
            setTextColor(Color.parseColor("#4c4c4c"))
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                height = 120
            }
        })

        override fun getItemCount() = dataList!!.size

        override fun onBindViewHolder(holder: MViewHolder, position: Int) {
            holder.title.text = dataList!![position]
            holder.itemView.setOnClickListener {
                when (position) {
                    0 -> {
                        requestPermission()
                    }
                    1 -> {
                        mcontext.startActivity(Intent(mcontext, TestFragment::class.java))
                    }
                    2 -> {
                        TestDialogFragment().show(supportFragmentManager, "")
                    }
                }
            }
        }

    }

    class TestDialogFragment : DialogFragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = LayoutInflater.from(context).inflate(R.layout.frag_test_permission_request, container, false)
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