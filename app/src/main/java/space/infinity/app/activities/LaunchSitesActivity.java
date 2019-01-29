package space.infinity.app.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.eyalbira.loadingdots.LoadingDots;

import java.util.ArrayList;
import java.util.List;

import space.infinity.app.R;
import space.infinity.app.adapters.LaunchSitesAdapter;
import space.infinity.app.dao.LaunchSiteDao;
import space.infinity.app.database.AppDatabase;
import space.infinity.app.database.AppDatabaseHelper;
import space.infinity.app.models.LaunchSite;
import space.infinity.app.utils.ThreadHelper;

public class LaunchSitesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LoadingDots loadingDots;
    private ActivityHelper activityHelper;
    private LaunchSitesAdapter launchSitesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_sites);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.launch_sites);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        launchSitesAdapter = new LaunchSitesAdapter(this, new ArrayList<LaunchSite>());
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(launchSitesAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutAnimation(AnimationUtils
                .loadLayoutAnimation(this, R.anim.layout_animation_down));
        loadingDots = findViewById(R.id.progress_bar);
        activityHelper = new ActivityHelper(this);
        activityHelper.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityHelper.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class ActivityHelper
            extends space.infinity.app.models.ActivityHelper
            implements ThreadHelper.LaunchSitesInterface {

        private AppDatabase appDatabase;
        private LaunchSiteDao launchSiteDao;
        private ThreadHelper threadHelper;

        public ActivityHelper(Context context) {
            super(context);
            appDatabase = AppDatabaseHelper.getDatabase(context);
            launchSiteDao = appDatabase.getLaunchSiteDao();
            threadHelper = new ThreadHelper(new Runnable() {
                @Override
                public void run() {
                    List<LaunchSite> launchSites = launchSiteDao.getLaunchSites();
                    for (int i = 0; i < 10; i++) {
                        LaunchSite launchSite = new LaunchSite();
                        launchSite.setImage("https://img.purch.com/w/660/aHR0cDovL3d3dy5zcGFjZS5jb20vaW1hZ2VzL2kvMDAwLzAxOS84Njcvb3JpZ2luYWwvcnVzc2lhLXNveXV6LWZnLWxhdW5jaC5qcGc=");
                        launchSite.setName("LAUNCH SITE");
                        launchSites.add(launchSite);
                    }
                    onResult(launchSites);
                }
            });
        }

        @Override
        public void onStart() {
            threadHelper.startExecutor();
        }

        @Override
        public void onDestroy() {
            appDatabase.close();
        }

        @Override
        public void showLayout() {
            recyclerView.setVisibility(View.VISIBLE);
            loadingDots.setVisibility(View.GONE);
        }

        @Override
        public void onResult(final List<LaunchSite> launchSiteList) {
            threadHelper.stopExecutor();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    launchSitesAdapter.add(launchSiteList);
                    showLayout();
                }
            });
        }
    }
}
