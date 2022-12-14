package com.user.grocery.utility;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.user.grocery.R;
import com.user.grocery.activities.HomeAct;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class GroceryNotification extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private String notificationType, title, meetupId, messageId;
    private String message;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "payload:" + remoteMessage.getData());
            Map<String,String> map = remoteMessage.getData();
            try {
                sendNotification("","",map);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "onMessageReceived for FCM");
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "c: " + remoteMessage.getData());
            try {
                sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getData());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendNotification(String message, String title,Map<String,String> map) throws JSONException {
        JSONObject jsonObject = null;
        jsonObject = new JSONObject(map.get("message"));
        String key = jsonObject.getString("key");
        Intent intent = new Intent();
        String key1 = jsonObject.getString("msg");
        if(key.equalsIgnoreCase("ACCEPTED_BY_ADMIN"))
        {
            String result = jsonObject.getString("result");
            intent = new Intent(this, HomeAct.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("notification","notification");
            intent.putExtra("key","notification");
            intent.putExtra("chat","test");
            Intent intent1 = new Intent("filter_string");
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);
        } else  if(key.equalsIgnoreCase("orders status"))
        {
            intent = new Intent(this, HomeAct.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("notification","new");
            intent.putExtra("key","notification");
            intent.putExtra("chat","test");
            Intent intent1 = new Intent("filter_string");
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);
        }
        PendingIntent pendingIntent;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity
                    (this, 0, intent, PendingIntent.FLAG_MUTABLE);
        }
        else
        {
            pendingIntent = PendingIntent.getActivity
                    (this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        }

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(key1)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
