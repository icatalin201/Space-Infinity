package space.infinity.app.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.eyalbira.loadingdots.LoadingDots;

import java.util.ArrayList;
import java.util.List;

import space.infinity.app.R;
import space.infinity.app.adapters.VoyagerAdapter;
import space.infinity.app.dao.VoyagerDao;
import space.infinity.app.database.AppDatabase;
import space.infinity.app.database.AppDatabaseHelper;
import space.infinity.app.models.Voyager;

public class VoyagerActivity extends AppCompatActivity {

    private LoadingDots loadingDots;
    private RecyclerView recyclerView;
    private VoyagerAdapter voyagerAdapter;
    private ActivityHelper activityHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voyager);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.voyager);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        loadingDots = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recycler);
        voyagerAdapter = new VoyagerAdapter(this, new ArrayList<Voyager>());
        recyclerView.setAdapter(voyagerAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    private class ActivityHelper extends space.infinity.app.models.ActivityHelper {

        private AppDatabase appDatabase;

        public ActivityHelper(Context context) {
            super(context);
            appDatabase = AppDatabaseHelper.getDatabase(context);
        }

        @Override
        public void onStart() {
            final VoyagerDao voyagerDao = appDatabase.getVoyagerDao();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final List<Voyager> voyagerList = voyagerDao.getVoyagerList();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            voyagerAdapter.add(voyagerList);
                            showLayout();
                        }
                    });
                }
            }).start();
        }

        @Override
        public void onDestroy() {
            appDatabase.close();
        }

        @Override
        public void showLayout() {
            loadingDots.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
