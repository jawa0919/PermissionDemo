package com.wj.permissiondemo

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_rx_permissions.*

class RxPermissionsActivity : AppCompatActivity() {

    val WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_permissions)
        textView.setOnClickListener {
            checkPhoneNumbersPermission()
        }
    }

    private fun checkPhoneNumbersPermission() {
        RxPermissions(this).requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe { permission ->
            when {
                permission.granted -> {
                    Toast.makeText(this, "有权限，为所欲为", Toast.LENGTH_SHORT).show()
                }
                permission.shouldShowRequestPermissionRationale -> {
                    Toast.makeText(this, "您拒绝了授权,", Toast.LENGTH_SHORT).show()
                    AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher)
                            .setTitle(R.string.app_name)
                            .setMessage("您之前拒绝授权，您需要在下个弹窗中同意我们使用权限")
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.ok) { _, _ ->
                                checkPhoneNumbersPermission()
                            }
                            .create().show()
                }
                else -> {
                    Toast.makeText(this, "您拒绝授权,，并勾选了不再询问", Toast.LENGTH_SHORT).show()
                    AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher)
                            .setTitle(R.string.app_name)
                            .setMessage("您之前拒绝授权，并勾选了不再询问，您需要应用设置修改应用程序权限")
                            .setCancelable(false)
                            .setPositiveButton(android.R.string.ok) { _, _ ->
                                val action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                val uri = Uri.parse("package:" + packageName)
                                startActivityForResult(Intent(action, uri), WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
                            }
                            .create().show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            checkPhoneNumbersPermission()
        }
    }
}
