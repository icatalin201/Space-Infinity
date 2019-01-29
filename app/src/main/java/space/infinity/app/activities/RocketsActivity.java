package space.infinity.app.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.eyalbira.loadingdots.LoadingDots;

import java.util.ArrayList;
import java.util.List;

import space.infinity.app.R;
import space.infinity.app.adapters.RocketAdapter;
import space.infinity.app.dao.RocketDao;
import space.infinity.app.database.AppDatabase;
import space.infinity.app.database.AppDatabaseHelper;
import space.infinity.app.models.Rocket;
import space.infinity.app.utils.ThreadHelper;

public class RocketsActivity extends AppCompatActivity {

    private LoadingDots progressBar;
    private RecyclerView recyclerView;
    private ActivityHelper activityHelper;
    private RocketAdapter rocketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rockets);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.rockets);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recycler);
        rocketAdapter = new RocketAdapter(this, new ArrayList<Rocket>());
        recyclerView.setAdapter(rocketAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutAnimation(AnimationUtils
                .loadLayoutAnimation(this, R.anim.layout_animation_down));
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

    private class ActivityHelper
            extends space.infinity.app.models.ActivityHelper
            implements ThreadHelper.RocketsInterface {

        private AppDatabase appDatabase;
        private RocketDao rocketDao;
        private ThreadHelper threadHelper;

        ActivityHelper(Context context) {
            super(context);
            appDatabase = AppDatabaseHelper.getDatabase(context);
            rocketDao = appDatabase.getRocketDao();
            threadHelper = new ThreadHelper(new Runnable() {
                @Override
                public void run() {
                    List<Rocket> astronautList = rocketDao.getRocketList();
                    for (int i = 0; i < 10; i++) {
                        Rocket rocket = new Rocket();
                        rocket.setName("Racheta");
                        rocket.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2b/Falcon_Heavy_cropped.jpg/270px-Falcon_Heavy_cropped.jpg");
                        astronautList.add(rocket);
                    }
                    onResult(astronautList);
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
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onResult(final List<Rocket> rocketList) {
            threadHelper.stopExecutor();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    rocketAdapter.add(rocketList);
                    showLayout();
                }
            });
        }
    }
}
