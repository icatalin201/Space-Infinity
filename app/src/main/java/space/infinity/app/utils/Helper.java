package space.infinity.app.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Catalin on 12/28/2017.
 */

public class Helper {

    public static void setAnimationForAdapter(Context mContext, View viewToAnimate, int position, int lastPosition) {
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
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
        Date d = new Date(unix_date*1000);
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        return dateFormat.format(d);
    }
}
