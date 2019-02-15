package space.infinity.app.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.eyalbira.loadingdots.LoadingDots;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import space.infinity.app.R;
import space.infinity.app.util.Constants;
import space.infinity.app.util.DownloadService;
import space.infinity.app.util.Helper;

import static space.infinity.app.util.DownloadService.ACTION_COMPLETE;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        LoadingDots loadingDots = findViewById(R.id.progress_bar);
        CoordinatorLayout coordinator = findViewById(R.id.coordinator);
        TextView funny = findViewById(R.id.funny);
        String firstTime = Helper.getFromSharedPreferences(Constants.FIRST_TIME_FLAG,
                this, Constants.FIRST_TIME_FLAG);
        if (!firstTime.equals("yes")) {
            loadingDots.setVisibility(View.VISIBLE);
            funny.setText(R.string.funny);
            Intent intent = new Intent(this, DownloadService.class);
            ContextCompat.startForegroundService(this, intent);
        } else {
            start();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter f = new IntentFilter(ACTION_COMPLETE);
        LocalBroadcastManager.getInstance(this).registerReceiver(event, f);
    }

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(event);
        super.onStop();
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

    private BroadcastReceiver event = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            start();
        }
    };
}
