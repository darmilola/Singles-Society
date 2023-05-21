package com.aure;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.os.Handler;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;


import androidx.core.app.NotificationCompat;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class NotificationService extends Service {

    private Socket mSocket;
    private String userId = "";
    private static final String URL = "https://aesthetic-sunshine-b2b4ba.netlify.app/";

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

         userId = intent.getStringExtra("userId");

        try {
            mSocket = IO.socket("https://aura-chat-socket.herokuapp.com");
            mSocket.connect();
            mSocket.emit("join", intent.getStringExtra("userId"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getReason(), Toast.LENGTH_SHORT).show();
        }

        mSocket.on("notification", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    //extract data from fired event
                    String receiverId = data.getString("receiverId");
                    String message = data.getString("message");
                    String senderName = data.getString("senderName");
                    String senderImageUrl = data.getString("senderImageUrl");

                    displayChatNotification(message,senderName,senderImageUrl);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        mSocket.on("onMatch", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    //extract data from fired event
                    String receiverId = data.getString("receiverId");
                    String matchFirstname = data.getString("matchFirstname");
                    String matchImageUrl = data.getString("matchImageUrl");

                    displayMatchNotification(matchFirstname,matchImageUrl);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();

    }

    private void displayChatNotification(String message,String senderName,String senderImageUrl){
        String CHANNEL_ID = "AURATAYYA";
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this,CHANNEL_ID)
                        .setSmallIcon(R.drawable.iconfinder_usa)
                        .setContentTitle(senderName)
                        .setContentText(StringEscapeUtils.unescapeJava(message))
                        .setAutoCancel(true)
                        .setOngoing(false)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.profileplaceholder))
                        .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "AURATAYYA_MESSAGES";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(1, mBuilder.build());

        final Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                Picasso.get()
                        .load(senderImageUrl)
                        .resize(250, 250)
                        .transform(new CropCircleTransformation())
                        .into(new Target() {
                            @Override
                            public void onBitmapLoaded(final Bitmap bitmap, final Picasso.LoadedFrom from) {
                                mBuilder.setLargeIcon(bitmap);
                                notificationManager.notify(1, mBuilder.build());
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(final Drawable placeHolderDrawable) {
                                // Do nothing
                            }
                        });
            }
        });


    }



    private void displayMatchNotification(String matchFirstName,String matchImageUrl){
        String CHANNEL_ID = "AURATAYYA MATCH";
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this,CHANNEL_ID)
                        .setSmallIcon(R.drawable.iconfinder_usa)
                        .setContentTitle(StringEscapeUtils.unescapeJava("\uD83c\uDF89\uD83c\uDF89\uD83c\uDF89 Congratulations \uD83c\uDF89\uD83c\uDF89\uD83c\uDF89"))
                        .setContentText("You have successfully matched with "+matchFirstName)
                        .setAutoCancel(true)
                        .setOngoing(false)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.profileplaceholder))
                        .setPriority(NotificationCompat.PRIORITY_MAX);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "AURATAYYA_MESSAGES";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(1, mBuilder.build());

        final Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                Picasso.get()
                        .load(matchImageUrl)
                        .resize(250, 250)
                        .transform(new CropCircleTransformation())
                        .into(new Target() {
                            @Override
                            public void onBitmapLoaded(final Bitmap bitmap, final Picasso.LoadedFrom from) {
                                mBuilder.setLargeIcon(bitmap);
                                notificationManager.notify(1, mBuilder.build());
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(final Drawable placeHolderDrawable) {
                                // Do nothing
                            }
                        });
            }
        });


    }
}
