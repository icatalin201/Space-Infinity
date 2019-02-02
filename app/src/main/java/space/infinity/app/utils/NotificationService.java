package space.infinity.app.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import space.infinity.app.R;
import space.infinity.app.activities.SplashActivity;

import static space.infinity.app.utils.Constants.CHANNEL_ID;

/**
 * Created by icatalin on 25.02.2018.
 */

public class NotificationService extends JobService {

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

    public static void launchJob(Context context, Class<?> cls, long millis) {
        ComponentName serviceComponent = new ComponentName(context, cls);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(millis);
        Object obj = context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobScheduler jobScheduler = (JobScheduler) obj;
        if (jobScheduler != null) jobScheduler.schedule(builder.build());
    }

    public static void stopJob(Context context) {
        Object obj = context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobScheduler jobScheduler = (JobScheduler) obj;
        if (jobScheduler != null) jobScheduler.cancelAll();
    }
}
