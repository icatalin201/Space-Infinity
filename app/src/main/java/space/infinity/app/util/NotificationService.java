package space.infinity.app.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import space.infinity.app.R;
import space.infinity.app.view.activity.SplashActivity;

import static space.infinity.app.util.Constants.CHANNEL_ID;
import static space.infinity.app.util.Helper.launchJob;

/**
 * Created by icatalin on 25.02.2018.
 */

public class NotificationService extends JobService {

    private void sendNotification(Context context) {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(context, SplashActivity.class);
        PendingIntent pendingIntent = PendingIntent
                .getActivity(context, 0, intent, 0);
        Notification notification = new NotificationCompat
                .Builder(context, CHANNEL_ID)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getResources().getString(R.string.apod))
                .setContentText(context.getResources().getString(R.string.press))
                .setAutoCancel(true).build();

        NotificationManager notifManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);
            if (notifManager != null) {
                notifManager.notify(1, notification);
            }
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        sendNotification(this);
        launchJob(this, NotificationService.class, 43200000);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }


}
