package com.ss.ably

import io.ably.lib.realtime.AblyRealtime
import io.ably.lib.realtime.Channel
import io.ably.lib.realtime.ChannelEvent
import io.ably.lib.realtime.ChannelState
import io.ably.lib.realtime.ChannelStateListener
import io.ably.lib.realtime.ConnectionState
import io.ably.lib.types.ChannelOptions
import io.ably.lib.types.ClientOptions
import io.ably.lib.types.Message


class AblyManager {

    private var ably: AblyRealtime? = null
    private var channel: Channel? = null

    private fun initAbly(){
        val options = ClientOptions("xVLyHw.Nmv1uw:i32EdrWDhmF7rIBRPLwmXzpi-w8YoYcsWvfVcrYsYz0")
        ably = AblyRealtime(options)
        ably?.connection?.on(
            ConnectionState.connected
        ) { state ->
            println("New state is " + state.current.name)
            when (state.current) {
                ConnectionState.connected -> {
                    // Successful connection
                    println("Connected to Ably!")
                }

                ConnectionState.failed -> {
                    initAbly()
                }
                else -> {

                }
            }
        }
    }

    private fun initChannel(channelName: String){
        channel = ably?.channels?.get(channelName)
        val params: MutableMap<String, String> = HashMap()
        params["rewind"] ="3"
        val options = ChannelOptions()
        options.params = params
    }

    private fun publishToChannel(channelName: String, messageData: String){
        channel?.publish(channelName,messageData);
    }

    // both server and client subscribed
    private fun attachToChannel(channelName: String){
        channel?.on(ChannelStateListener { state ->
            when (state.current) {
                ChannelState.attached -> {
                    println("'chatroom' exists and is now available globally")
                }

                else -> {

                }
            }
        })
    }

    //detach and unsubscribed
    private fun detachFromChannel(){
        channel?.detach()
        channel?.on(ChannelEvent.detached, object : ChannelStateListener {
            override fun onChannelStateChanged(stateChange: ChannelStateListener.ChannelStateChange?) {

            }
        })
    }

    // client subscribed
    private fun subscribeToChannel(channelName: String, filter: String){
        channel?.subscribe(filter, object : Channel.MessageListener {
            override fun onMessage(message: Message) {
                System.out.println("Received a greeting message in realtime: " + message.data)
            }
        })
    }

    private fun closeConnection(){
        ably?.connection?.close()
        ably?.connection?.on(
            ConnectionState.closed
        ) { state ->
            println("New state is " + state.current.name)
            when (state.current) {
                ConnectionState.closed -> {
                     // Connection closed
                    println("Closed the connection to Ably.")
                }
                ConnectionState.failed -> {
                    closeConnection()
                }
                else -> {

                }
            }
        }
    }
}