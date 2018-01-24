package space.infinity.app.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

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

    public static Date unixToDate(Long unix_date){
        return new Date(unix_date*1000);
    }
}
