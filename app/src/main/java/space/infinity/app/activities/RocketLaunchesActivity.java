package space.infinity.app.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.eyalbira.loadingdots.LoadingDots;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.infinity.app.R;
import space.infinity.app.adapters.RocketLaunchesAdapter;
import space.infinity.app.models.ActivityHelper;
import space.infinity.app.models.Launch;
import space.infinity.app.models.LaunchResponse;
import space.infinity.app.network.Client;
import space.infinity.app.network.Service;
import space.infinity.app.utils.Constants;

public class RocketLaunchesActivity extends AppCompatActivity {

    private ActivityHelper activityHelper;
    private RocketLaunchesAdapter rocketLaunchesAdapter;
    private LoadingDots progressBar;
    private Spinner spinner;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private final String[] items = new String[]{
        "Upcoming Launches", "All Launches"
    };

    private boolean pagesOver = false;
    private int offset = 0;
    private boolean loading = true;
    private int previousTotal = 0;

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
        activityHelper = new ActivityHelper(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                activityHelper.onStart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                activityHelper.handleScroll(linearLayoutManager);
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
        activityHelper.onDestroy();
    }

    private class ActivityHelper
            extends space.infinity.app.models.ActivityHelper {

        public ActivityHelper(Context context) {
            super(context);
        }

        void handleScroll(LinearLayoutManager linearLayoutManager) {
            int pos = spinner.getSelectedItemPosition();
            if (pos == 0) return;
            int visibleItemCount = linearLayoutManager.getChildCount();
            int totalItemCount = linearLayoutManager.getItemCount();
            int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            int visibleThreshold = 5;
            if (!loading && (totalItemCount - visibleItemCount) <=
                    (firstVisibleItem + visibleThreshold)) {
                makeAllCall();
                loading = true;
            }
        }

        void makeFutureCall() {
            Call<LaunchResponse> launchResponseCall = Client.getRetrofitClient(Constants.LAUNCH_LIBRARY_API)
                    .create(Service.class).getNextLaunches(20);
            launchResponseCall.enqueue(new Callback<LaunchResponse>() {
                @Override
                public void onResponse(Call<LaunchResponse> call, Response<LaunchResponse> response) {
                    Log.e("URL", call.request().url().toString());
                    if (response.body() != null) {
                        List<Launch> launchList = new ArrayList<>();
                        for (Launch launch : response.body().getLaunches()) {
                            if (launch.getRocket() == null) continue;
                            launchList.add(launch);
                        }
                        rocketLaunchesAdapter.add(launchList);
                    }
                    showLayout();
                }

                @Override
                public void onFailure(Call<LaunchResponse> call, Throwable t) {
                    t.printStackTrace();
                    showLayout();
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
            });
        }

        void makeAllCall() {
            if (pagesOver) return;
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setAlpha(0.3f);
            Call<LaunchResponse> launchResponseCall = Client.getRetrofitClient(Constants.LAUNCH_LIBRARY_API)
                    .create(Service.class).getLaunches(offset, 20, "desc",
                            "2000-01-01", "2019-01-01");
            launchResponseCall.enqueue(new Callback<LaunchResponse>() {
                @Override
                public void onResponse(Call<LaunchResponse> call, Response<LaunchResponse> response) {
                    Log.e("URL", call.request().url().toString());
                    if (response.body() != null) {
                        List<Launch> launchList = new ArrayList<>();
                        for (Launch launch : response.body().getLaunches()) {
                            if (launch.getRocket() == null) continue;
                            launchList.add(launch);
                        }
                        rocketLaunchesAdapter.add(launchList);
                        offset += 20;
                    } else {
                        pagesOver = true;
                    }
                    showLayout();
                }

                @Override
                public void onFailure(Call<LaunchResponse> call, Throwable t) {
                    t.printStackTrace();
                    showLayout();
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
            });
        }

        @Override
        public void onStart() {
            offset = 0;
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            rocketLaunchesAdapter.remove();
            final int pos = spinner.getSelectedItemPosition();
            if (pos == 0) {
                makeFutureCall();
            } else {
                makeAllCall();
            }
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public void showLayout() {
            progressBar.setVisibility(View.GONE);
            recyclerView.setAlpha(1.0f);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
