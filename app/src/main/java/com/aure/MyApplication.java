package com.aure;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import com.aure.UiModels.Message;
import com.aure.UiModels.User;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MyApplication extends Application {

    private Socket mSocket;
    private static final String URL = "https://glacial-springs-30545.herokuapp.com";

    public void onCreate() {
        super.onCreate();
        try {
            mSocket = IO.socket("https://glacial-springs-30545.herokuapp.com");
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        mSocket.on("message", new Emitter.Listener() {
                    @Override
                    public void call(final Object... args) {
                        Log.e("received", " call: ");
                        JSONObject data = (JSONObject) args[0];
                        try {
                            //extract data from fired event
                            String receiverId = data.getString("receiverId");
                            String message = data.getString("message");
                            int messageType = data.getInt("messageType");

                            if(!(getCurrentActivity() instanceof ChatActivity)){
                                displayChatNotification(message,receiverId);
                            }
                            else{
                                Log.e("in chat", "call: ");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });



        mSocket.on("onOffline", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {

            }
        });

    }

    private void displayChatNotification(String message,String senderName){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this,"AURATAYYA_CHAT")
                        .setSmallIcon(R.drawable.iconfinder_usa)
                        .setContentTitle("Damilola Akinterinwa")
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setOngoing(true);
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, 0);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, mBuilder.build());
    }

    private Activity mCurrentActivity = null;

    public Activity getCurrentActivity(){
        return mCurrentActivity;
    }

    public void setCurrentActivity(Activity mCurrentActivity){
        this.mCurrentActivity = mCurrentActivity;
    }
}
