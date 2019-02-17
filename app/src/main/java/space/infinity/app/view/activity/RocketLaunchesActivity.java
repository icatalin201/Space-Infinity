package space.infinity.app.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.eyalbira.loadingdots.LoadingDots;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.infinity.app.R;
import space.infinity.app.model.entity.Launch;
import space.infinity.app.model.repository.RocketLaunchesRepository;
import space.infinity.app.viewmodel.adapters.RocketLaunchesAdapter;

public class RocketLaunchesActivity extends AppCompatActivity
        implements RocketLaunchesRepository.RocketLaunchCallback {

    private RocketLaunchesAdapter rocketLaunchesAdapter;
    private LoadingDots progressBar;
    private Spinner spinner;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private final String[] items = new String[]{
        "Upcoming Launches", "All Launches"
    };
    private RocketLaunchesRepository rocketLaunchesRepository;

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
        rocketLaunchesRepository = new RocketLaunchesRepository(this);
        coordinatorLayout = findViewById(R.id.coordinator);
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
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                progressBar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                rocketLaunchesAdapter.remove();
                rocketLaunchesRepository.start(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (spinner.getSelectedItemPosition() == 0) return;
                rocketLaunchesRepository.handleScroll(linearLayoutManager);
            }
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

    @Override
    public void onSuccess(List<Launch> launches) {
        rocketLaunchesAdapter.add(launches);
        recyclerView.scrollToPosition(0);
        progressBar.setVisibility(View.GONE);
        recyclerView.setAlpha(1.0f);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFailure(String message) {
        progressBar.setVisibility(View.GONE);
        recyclerView.setAlpha(1.0f);
        recyclerView.setVisibility(View.VISIBLE);
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Unexpected error has occured.",
                        Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recreate();
                    }
                });
        snackbar.setActionTextColor(getResources().getColor(R.color.primaryTextColor));
        snackbar.show();
    }

    @Override
    public void onLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setAlpha(0.3f);
    }
}
