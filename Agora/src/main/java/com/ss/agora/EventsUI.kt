package com.ss.agora

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import io.agora.agorauikit_android.*
import io.agora.rtc2.Constants


class EventsUI : AppCompatActivity() {

    // Object of AgoraVideoVIewer class
    private lateinit var agView: AgoraVideoViewer



    // Fill the App ID of your project generated on Agora Console.
    private val appId = "d3618a45d55f4b6d98658c7dc7142779"

    // Fill the channel name.
    private val channelName = "Test"

    // Fill the temp token generated on Agora Console.
    private val token = "<Your access token>"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_ui)
        initializeAndJoinChannel()
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    private fun initializeAndJoinChannel() {
        // Create AgoraVideoViewer instance
        agView = try {
            AgoraVideoViewer(
                this,
                AgoraConnectionData(appId),
                AgoraVideoViewer.Style.FLOATING,
                AgoraSettings(),
                null
            )
        } catch (e: Exception) {
            Log.e(
                "AgoraVideoViewer",
                "Could not initialize AgoraVideoViewer. Check that your app Id is valid."
            )
            Log.e("Exception", e.toString())
            return
        }

        // Add the AgoraVideoViewer to the Activity layout
        addContentView(
            agView, FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )

        // Check permission and join a channel
        if (true) {
            joinChannel()
        } else {
            val joinButton = Button(this)
            joinButton.text = "Allow camera and microphone access, then click here"
            joinButton.setOnClickListener {
               /* if (DevicePermissionsKt.requestPermissions(
                        AgoraVideoViewer,
                        applicationContext
                    )
                ) {*/
                    (joinButton.parent as ViewGroup).removeView(joinButton)
                    joinChannel()
              //  }
            }
            addContentView(
                joinButton,
                FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 200)
            )
        }
    }



    fun joinChannel() {
        agView!!.join(channelName, token, Constants.CLIENT_ROLE_BROADCASTER, 0)
    }
}