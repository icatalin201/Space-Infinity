package space.infinity.app.utils;

import android.content.Context;
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

    public static void setAnimationForAdapter(Context mContext, View viewToAnimate,
                                              int position, int lastPosition) {
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
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

    public static int getDP(int dp, Context context) {
        float d = context.getResources().getDisplayMetrics().density;
        return (int) (dp * d);
    }

    public static void putInSharedPreferences(String pref_name, Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(pref_name, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getFromSharedPreferences(String pref_name, Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(pref_name, MODE_PRIVATE);
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
        DateFormat date = new SimpleDateFormat("dd MMMM", Locale.getDefault());
        DateFormat hour = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return date.format(d).concat(" at ").concat(hour.format(d));
    }

    public static String dateToString(Calendar calendar) {
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss",
                Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }
}
