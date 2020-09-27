package com.redcattomato.permissionr.core.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.redcattomato.permissionr.core.PermissionRInfo
import com.redcattomato.permissionr.core.imp.PermissionDialogListener
import java.util.ArrayList

import com.redcattomato.permissionr.core.R

/**
 * Create on 2020/9/22
 *
 * @author redcattomato
 * @version 1.0.0
 **/
class PermissionResultDialog : DialogFragment() {

    private var mListener: PermissionDialogListener? = null
    private var mMust = false

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(R.style.PermissionRDialog, theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val contentView = inflater.inflate(R.layout.dialog_permission_r, container, false)
        initView(contentView)
        return contentView
    }

    private fun initView(view: View) {
        val contentRc = view.findViewById<RecyclerView>(R.id.permission_rc_content)
        view.findViewById<View>(R.id.permission_cancel).setOnClickListener {
            if (mListener != null) mListener!!.onCancel()
            if(mMust) return@setOnClickListener
            dismiss()
        }
        view.findViewById<View>(R.id.permission_ok).setOnClickListener {
            if (mListener != null) mListener!!.onOk()
        }

        val dataList: ArrayList<PermissionRInfo> = arguments!!.getParcelableArrayList("pass_data")!!
        mMust = arguments!!.getBoolean("pass_must_state")
        contentRc.layoutManager = LinearLayoutManager(view.context)
        contentRc.adapter = ContentAdapter(context!!, dataList)
    }

    fun setPermissionDialogListener(listener: PermissionDialogListener): PermissionResultDialog {
        mListener = listener
        return this
    }

    class ContentAdapter(context: Context, permissionInfoList: List<PermissionRInfo>) :
            RecyclerView.Adapter<ContentAdapter.MViewHolder>() {

        private val mPermissionInfoList = permissionInfoList
        private val mCtx = context

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            MViewHolder(LayoutInflater.from(mCtx).inflate(R.layout.item_permission_layout, parent, false))

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: MViewHolder, position: Int) {
            val permissionInfo = mPermissionInfoList[position]
            holder.name.text = permissionInfo.permissionName
            holder.icon.setImageDrawable(permissionInfo.icon)
        }

        override fun getItemCount() = mPermissionInfoList.size

        class MViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val icon: ImageView = itemView.findViewById(R.id.permission_icon)
            val name: TextView = itemView.findViewById(R.id.permission_name)

        }

    }


    companion object {
        fun show(@NonNull manager: FragmentManager, permissionInfoList: List<PermissionRInfo>
                                                  , must : Boolean): PermissionResultDialog {
            val dialog = PermissionResultDialog()
            dialog.arguments = Bundle().apply {
                putParcelableArrayList(
                        "pass_data",
                        permissionInfoList as ArrayList<out Parcelable>
                )
                putBoolean("pass_must_state", must)
            }
            dialog.show(manager, "permission_result_dialog")
            return dialog
        }
    }

}