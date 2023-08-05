package com.ss.agora

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.view.SurfaceView
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import io.agora.base.internal.ContextUtils.getApplicationContext
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig
import io.agora.rtc2.video.VideoCanvas


class AgoraManager(val context: Context, private val appId: String) {
    private val channelName = "Your channel name"

    // Fill the temp token generated on Agora Console.
    private val token = "<your access token>"

    // An integer that identifies the local user.
    private val uid = 0
    private var isJoined = false
    //SurfaceView to render local video in a Container.
    private var localSurfaceView: SurfaceView? = null

    //SurfaceView to render Remote video in a Container.
    private var remoteSurfaceView: SurfaceView? = null

    private var agoraEngine: RtcEngine? = null

    private val PERMISSION_REQ_ID = 22
    private val REQUESTED_PERMISSIONS = arrayOf<String>(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA
    )

    private fun setupSDKEngine() {
        try {
            val config = RtcEngineConfig()
            config.mContext = context
            config.mAppId = appId
            config.mEventHandler = mRtcEventHandler
            agoraEngine = RtcEngine.create(config)
            agoraEngine?.enableVideo()
        } catch (e: Exception) {

        }
    }


    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        // Listen for the remote host joining the channel to get the uid of the host.
        override fun onUserJoined(uid: Int, elapsed: Int) {
            /*// Set the remote video view
            runOnUiThread { setupRemoteVideo(uid) }*/
        }

        override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {

        }

        override fun onUserOffline(uid: Int, reason: Int) {
           /* showMessage("Remote user offline $uid $reason")
            runOnUiThread { remoteSurfaceView.setVisibility(View.GONE) }*/
        }
    }

    private fun setupRemoteVideo(uid: Int,container: FrameLayout, context: Context) {
        remoteSurfaceView = SurfaceView(context)
        remoteSurfaceView?.setZOrderMediaOverlay(true)
        container.addView(remoteSurfaceView)
        agoraEngine!!.setupRemoteVideo(
            VideoCanvas(
                remoteSurfaceView,
                VideoCanvas.RENDER_MODE_FIT,
                uid
            )
        )
        remoteSurfaceView?.isVisible = true
    }


    private fun setupLocalVideo(uid: Int,container: FrameLayout, context: Context) {
        localSurfaceView = SurfaceView(context)
        container.addView(localSurfaceView)
        // Call setupLocalVideo with a VideoCanvas having uid set to 0.
        agoraEngine!!.setupLocalVideo(
            VideoCanvas(
                localSurfaceView,
                VideoCanvas.RENDER_MODE_HIDDEN,
                0
            )
        )
    }

    fun joinChannel(uid: Int,container: FrameLayout, context: Context) {
        if (checkSelfPermission()) {
            val options = ChannelMediaOptions()

            // For a Video call, set the channel profile as COMMUNICATION.
            options.channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION
            // Set the client role as BROADCASTER or AUDIENCE according to the scenario.
            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
            // Display LocalSurfaceView.
            setupLocalVideo(uid,container,context)
            localSurfaceView?.visibility = View.VISIBLE
            // Start local preview.
            agoraEngine?.startPreview()
            // Join the channel with a temp token.
            // You need to specify the user ID yourself, and ensure that it is unique in the channel.
            agoraEngine?.joinChannel(token, channelName, uid, options)
        } else {
            Toast.makeText(
                getApplicationContext(),
                "Permissions was not granted",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun leaveChannel(view: View?) {
        if (!isJoined) {
            /*showMessage("Join a channel first")*/
        } else {
            agoraEngine!!.leaveChannel()
            /* showMessage("You left the channel")*/
            // Stop remote video rendering.
            if (remoteSurfaceView != null) remoteSurfaceView?.visibility = View.GONE
            // Stop local video rendering.
            if (localSurfaceView != null) localSurfaceView?.visibility = View.GONE
            isJoined = false
        }
    }

    private fun checkSelfPermission(): Boolean {
        return !(ContextCompat.checkSelfPermission(
            context,
            REQUESTED_PERMISSIONS[0]
        ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    REQUESTED_PERMISSIONS[1]
                ) != PackageManager.PERMISSION_GRANTED)
    }

    private fun destroyAgoraEngine(){
            agoraEngine?.stopPreview();
            agoraEngine?.leaveChannel();
        Thread {
            RtcEngine.destroy()
            agoraEngine = null
        }.start()


    }


}


