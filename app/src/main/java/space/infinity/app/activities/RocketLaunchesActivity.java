package space.infinity.app.activities;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.eyalbira.loadingdots.LoadingDots;

import java.util.ArrayList;

import space.infinity.app.R;
import space.infinity.app.adapters.RocketLaunchesAdapter;
import space.infinity.app.models.ActivityHelper;
import space.infinity.app.models.Launch;

public class RocketLaunchesActivity extends AppCompatActivity {

    private ActivityHelper activityHelper;
    private RocketLaunchesAdapter rocketLaunchesAdapter;
    private LoadingDots progressBar;
    private Spinner spinner;
    private RecyclerView recyclerView;
    private final String[] items = new String[]{
        "Upcoming Launches", "All Launches"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocket_launches);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        spinner = toolbar.findViewById(R.id.action_bar_spinner);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recycler);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, items);
        spinner.setAdapter(arrayAdapter);
        recyclerView = findViewById(R.id.recycler);
        rocketLaunchesAdapter = new RocketLaunchesAdapter(this, new ArrayList<Launch>());
        recyclerView.setAdapter(rocketLaunchesAdapter);
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class ActivityHelper extends space.infinity.app.models.ActivityHelper {

        public ActivityHelper(Context context) {
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
}
