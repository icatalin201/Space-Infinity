package space.infinity.app.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import space.infinity.app.R;
import space.infinity.app.activities.Splash;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by icatalin on 25.02.2018.
 */

public class NotificationService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        sendNotification(context);
    }

        private void sendNotification(Context context) {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(context, Splash.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        String CHANNEL_ID = "channel-01";
        Notification notification = new NotificationCompat
                .Builder(context, CHANNEL_ID)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getResources().getString(R.string.apod))
                .setContentText(context.getResources().getString(R.string.press))
                .setAutoCancel(true).build();

        NotificationManager notifManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            if (notifManager != null) {
                notifManager.notify(0, notification);
            }
        }
}
