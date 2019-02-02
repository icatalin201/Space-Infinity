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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.eyalbira.loadingdots.LoadingDots;

import java.util.ArrayList;
import java.util.List;

import space.infinity.app.R;
import space.infinity.app.adapters.EncyclopediaAdapter;
import space.infinity.app.dao.CosmicDao;
import space.infinity.app.database.AppDatabase;
import space.infinity.app.database.AppDatabaseHelper;
import space.infinity.app.models.CosmicItem;
import space.infinity.app.utils.ThreadHelper;

public class EncyclopediasActivity extends AppCompatActivity {

    private class ActivityHelper
            extends space.infinity.app.models.ActivityHelper
            implements ThreadHelper.CosmicInterface {

        private AppDatabase appDatabase;
        private CosmicDao cosmicDao;
        private ThreadHelper threadHelper;

        ActivityHelper(Context context) {
            super(context);
            appDatabase = AppDatabaseHelper.getDatabase(context);
            cosmicDao = appDatabase.getCosmicDao();
        }

        @Override
        public void onStart() {
            final int pos = spinner.getSelectedItemPosition();
            if (threadHelper != null) {
                threadHelper.stopExecutor();
            }
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            threadHelper = new ThreadHelper(new Runnable() {
                @Override
                public void run() {
                    List<? extends CosmicItem> cosmicItems = new ArrayList<>();
                    switch (pos) {
                        case 0:
                            cosmicItems = cosmicDao.getPlanetList();
                            break;
                        case 1:
                            cosmicItems = cosmicDao.getMoonList();
                            break;
                        case 2:
                            cosmicItems = cosmicDao.getStarList();
                            break;
                        case 3:
                            cosmicItems = cosmicDao.getGalaxyList();
                            break;
                    }
                    onResult(cosmicItems);
                }
            });
            threadHelper.startExecutor();
        }

        @Override
        public void onDestroy() {
            appDatabase.close();
        }

        @Override
        public void showLayout() {
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onResult(final List<? extends CosmicItem> cosmicItemList) {
            threadHelper.stopExecutor();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerView.setLayoutAnimation(AnimationUtils
                            .loadLayoutAnimation(getContext(), R.anim.layout_animation_down));
                    encyclopediaAdapter.remove();
                    encyclopediaAdapter.add(cosmicItemList);
                    showLayout();
                }
            });
        }
    }

    private ActivityHelper activityHelper;
    private EncyclopediaAdapter encyclopediaAdapter;
    private LoadingDots progressBar;
    private Spinner spinner;
    private RecyclerView recyclerView;
    private final String[] items = new String[]{
        "Planets", "Moons", "Stars", "Galaxies"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_encyclopedia);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        progressBar = findViewById(R.id.progress_bar);
        spinner = toolbar.findViewById(R.id.action_bar_spinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
               R.layout.spinner_item, items);
        spinner.setAdapter(arrayAdapter);
        recyclerView = findViewById(R.id.recycler);
        encyclopediaAdapter = new EncyclopediaAdapter(this, new ArrayList<CosmicItem>());
        recyclerView.setAdapter(encyclopediaAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        activityHelper = new ActivityHelper(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                activityHelper.onStart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityHelper.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
