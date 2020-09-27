Android 权限申请框架
PermissionR 
=================  
  项目配置环境  
  *gradle-6.6.1-bin.zip  
  *Kotlin 1.4.0  
  *AndroidX  
  *compileSdkVersion 29  
  *buildToolsVersion "29.0.3"  
  
UI
------------
*1、PermissionResultDialog

Core
------------


Exp
------------

```
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
                .useDialog(true)
                .must(true)
                .build()


