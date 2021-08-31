package com.aure;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import androidx.core.app.NotificationCompat;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class NotificationService extends Service {

    private Socket mSocket;
    private static final String URL = "https://glacial-springs-30545.herokuapp.com";

    public NotificationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Okay ne'er mind", Toast.LENGTH_SHORT).show();
        displayChatNotification("My Notification is here","Yeah receiver");
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

                    displayChatNotification(message,receiverId);

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


        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void displayChatNotification(String message,String senderName){

        Intent intent = new Intent(this, MainActivity.class);
// use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
        Notification n  = new Notification.Builder(this)
                .setContentTitle("New mail from " + "test@gmail.com")
                .setContentText("Subject")
                .setSmallIcon(R.drawable.good_icon)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                 .build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
    }
}
