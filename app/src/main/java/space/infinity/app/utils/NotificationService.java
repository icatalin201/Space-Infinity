package space.infinity.app.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import space.infinity.app.R;
import space.infinity.app.activities.Splash;

/**
 * Created by icatalin on 25.02.2018.
 */

public class NotificationService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = 1;
        String CHANNEL_ID = "channel-01";
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID);
        Intent splashIntent = new Intent(context, Splash.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1,
                splashIntent, 0);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(context.getResources().getString(R.string.apod));
        mBuilder.setContentText(context.getResources().getString(R.string.press));
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (mNotificationManager != null) {
            mNotificationManager.notify(notificationId, mBuilder.build());
        }
    }
}
