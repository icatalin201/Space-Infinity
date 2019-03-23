package space.infinity.app.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Catalin on 12/28/2017.
 */

public class Helper {

    public static void launchJob(Context context, Class<?> cls) {
        ComponentName serviceComponent = new ComponentName(context, cls);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        long millis = 1000 * 60 * 60 * 3;
        builder.setMinimumLatency(millis);
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (jobScheduler != null) jobScheduler.schedule(builder.build());
    }

    public static void stopJob(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (jobScheduler != null) jobScheduler.cancelAll();
    }

    public static void startAlarmManager(Context context, Calendar calendar, Class cls) {
        Intent alarmIntent = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public static void stopAlarmManager(Context context, Class cls) {
        Intent alarmIntent = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }

    public static File saveImageToGallery(Bitmap bitmap, String title){
        FileOutputStream outStream;
        File picturesDir = new File(Environment.getExternalStorageDirectory() +
                File.separator + "Pictures");
        File dir = new File(picturesDir.getAbsolutePath() +
                File.separator + "Space Infinity");
        boolean succes;
        if (!dir.exists()) {
            succes = dir.mkdir();
            if (succes) { Log.i("Directory", "Created"); }
        }
        Random r = new Random();
        String name = title.replace(" ", "")
                .concat(Integer.toString(r.nextInt()));
        String fileName = String.format("%s.jpg", name);
        File outFile = new File(dir, fileName);
        try {
            outStream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (IOException e ) {
            e.printStackTrace();
        }
        return outFile;
    }

    public static void putInSharedPreferences(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(Constants.SPACE_INFINITY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getFromSharedPreferences(Context context, String key) {
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(Constants.SPACE_INFINITY, MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void setAnimationForAll(Context mContext, View viewToAnimate){
        Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in);
        viewToAnimate.startAnimation(animation);
    }

    public static void customAnimation(Context context, View view, int duration, int anim) {
        Animation animation = AnimationUtils.loadAnimation(context, anim);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    public static String unixToDate(Long unix_date){
        Date d = new Date(unix_date * 1000);
        DateFormat date = new SimpleDateFormat(Constants.DATE_FORMAT_WITHOUT_YEAR_1, Locale.getDefault());
        DateFormat hour = new SimpleDateFormat(Constants.HOUR_FORMAT_1, Locale.getDefault());
        return date.format(d).concat(" at ").concat(hour.format(d));
    }

    public static String dateToString(Calendar calendar) {
        DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_TIME_FORMAT_1,
                Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    public static String dateToString(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String formatNumber(long number) {
        String numberStr;
        if (number < 10) {
            numberStr = String.format("0%s", number);
        } else {
            numberStr = String.valueOf(number);
        }
        return numberStr;
    }

}
