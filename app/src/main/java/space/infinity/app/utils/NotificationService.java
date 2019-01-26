package space.infinity.app.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import space.infinity.app.R;
import space.infinity.app.activities.SplashActivity;

import static android.content.Context.NOTIFICATION_SERVICE;
import static space.infinity.app.utils.Constants.CHANNEL_ID;

/**
 * Created by icatalin on 25.02.2018.
 */

public class NotificationService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        sendNotification(context);
    }

    private String createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "space_infinity";
            String description = "space infinity notification channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
            return channel.getId();
        } else {
            return CHANNEL_ID;
        }
    }

    private void sendNotification(Context context) {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(context, SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        Notification notification = new NotificationCompat
                .Builder(context, createNotificationChannel(context))
                .setSound(soundUri)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getResources().getString(R.string.apod))
                .setContentText(context.getResources().getString(R.string.press))
                .setAutoCancel(true).build();

        NotificationManager notifManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            if (notifManager != null) {
                notifManager.notify(1, notification);
            }
    }
}
