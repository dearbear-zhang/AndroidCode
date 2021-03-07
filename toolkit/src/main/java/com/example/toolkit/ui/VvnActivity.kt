package com.example.toolkit.ui

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.VpnService
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.toolkit.R
import com.example.toolkit.databinding.ActivityVvnBinding
import com.example.toolkit.localvpn.LocalVPNService


/**
 * Create by dearbear
 * on 2021/3/7
 */
class VvnActivity : AppCompatActivity() {
    private val VPN_REQUEST_CODE = 0x0F
    private var waitingForVPNStart = false

    lateinit var mBinding: ActivityVvnBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityVvnBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initView()
        initData()
    }

    override fun onResume() {
        super.onResume()
        enableButton(!waitingForVPNStart && !LocalVPNService.isRunning())
    }

    private fun initView() {
        mBinding.vvnOpen.setOnClickListener {
            startVPN()
        }
    }

    private fun initData() {
        waitingForVPNStart = false
        LocalBroadcastManager.getInstance(this).registerReceiver(vpnStateReceiver,
                IntentFilter(LocalVPNService.BROADCAST_VPN_STATE));
    }

    private fun startVPN() {
        val vpnIntent = VpnService.prepare(this)
        if (vpnIntent != null)
            startActivityForResult(vpnIntent, VPN_REQUEST_CODE)
        else onActivityResult(VPN_REQUEST_CODE, Activity.RESULT_OK, null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == VPN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            waitingForVPNStart = true
            startService(Intent(this, LocalVPNService::class.java))
            enableButton(false)
        }
    }

    private fun enableButton(enable: Boolean) {
        if (enable) {
            mBinding.vvnOpen.setEnabled(true)
            mBinding.vvnOpen.setText(R.string.start_vpn)
        } else {
            mBinding.vvnOpen.setEnabled(false)
            mBinding.vvnOpen.setText(R.string.stop_vpn)
        }
    }

    private val vpnStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (LocalVPNService.BROADCAST_VPN_STATE.equals(intent.action)) {
                if (intent.getBooleanExtra("running", false)) waitingForVPNStart = false
            }
        }
    }
}