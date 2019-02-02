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
import space.infinity.app.adapters.AstronautAdapter;
import space.infinity.app.dao.AstronautDao;
import space.infinity.app.database.AppDatabase;
import space.infinity.app.database.AppDatabaseHelper;
import space.infinity.app.models.Astronaut;
import space.infinity.app.utils.ThreadHelper;

public class AstronautsActivity extends AppCompatActivity {

    private LoadingDots progressBar;
    private RecyclerView recyclerView;
    private ActivityHelper activityHelper;
    private AstronautAdapter astronautAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astronauts);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.astronauts);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recycler);
        astronautAdapter = new AstronautAdapter(this, new ArrayList<Astronaut>());
        recyclerView.setAdapter(astronautAdapter);
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
            implements ThreadHelper.AstronautsInterface {

        private AppDatabase appDatabase;
        private AstronautDao astronautDao;
        private ThreadHelper threadHelper;

        ActivityHelper(Context context) {
            super(context);
            appDatabase = AppDatabaseHelper.getDatabase(context);
            astronautDao = appDatabase.getAstronautDao();
            threadHelper = new ThreadHelper(new Runnable() {
                @Override
                public void run() {
                    List<Astronaut> astronautList = astronautDao.getAstronautList();
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
        public void onResult(final List<Astronaut> astronautList) {
            threadHelper.stopExecutor();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    astronautAdapter.add(astronautList);
                    showLayout();
                }
            });
        }
    }
}
