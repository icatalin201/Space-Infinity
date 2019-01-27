package space.infinity.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import space.infinity.app.R;
import space.infinity.app.utils.Constants;
import space.infinity.app.utils.Helper;

public class SplashActivity extends AppCompatActivity {

    private class ActivityHelper extends space.infinity.app.models.ActivityHelper {

        ActivityHelper(Context context) {
            super(context);
        }

        @Override
        public void onStart() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public void showLayout() {

        }
    }

    private ActivityHelper activityHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activityHelper = new ActivityHelper(this);
        String firstTime = Helper.getFromSharedPreferences(Constants.FIRST_TIME_FLAG,
                this, Constants.FIRST_TIME_FLAG);
        if (!firstTime.equals("yes")) {
            Helper.putInSharedPreferences(Constants.FIRST_TIME_FLAG,
                    this, Constants.FIRST_TIME_FLAG, "yes");
            start();
        } else {
            start();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        activityHelper.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityHelper.onDestroy();
    }


    private void start() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 700);
    }

    @Override
    public void onBackPressed() { }
}
