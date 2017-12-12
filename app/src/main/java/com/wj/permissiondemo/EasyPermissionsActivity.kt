package com.wj.permissiondemo

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_easypermission.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class EasyPermissionsActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    val CAMERA_REQUEST_CODE = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easypermission)
        textView.setOnClickListener {
            checkCameraPermission()
        }
    }

    private fun checkCameraPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            Toast.makeText(this, "有权限，为所欲为", Toast.LENGTH_SHORT).show()
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "应用程序需要访问您的相机,您需要在下个弹窗中允许我们使用照相机",
                    CAMERA_REQUEST_CODE,
                    Manifest.permission.CAMERA)
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            Toast.makeText(this, "您拒绝授权,并勾选了不在提醒" + CAMERA_REQUEST_CODE, Toast.LENGTH_SHORT).show()
            AppSettingsDialog.Builder(this).setTitle("打开应用程序设置修改应用程序权限").build().show()
        } else {
            Toast.makeText(this, "您拒绝授权" + CAMERA_REQUEST_CODE, Toast.LENGTH_SHORT).show()
            checkCameraPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(this, "您同意了授权" + CAMERA_REQUEST_CODE, Toast.LENGTH_SHORT).show()
        checkCameraPermission()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            checkCameraPermission()
        }
    }
}
