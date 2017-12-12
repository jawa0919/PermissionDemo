package com.wj.permissiondemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tevEasyPermissions.setOnClickListener {
            startActivity(Intent(this@MainActivity, EasyPermissionsActivity::class.java))
        }
        tevRxPermissions.setOnClickListener {
            startActivity(Intent(this@MainActivity, RxPermissionsActivity::class.java))
        }
    }
}
