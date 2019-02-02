package space.infinity.app.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.infinity.app.R;
import space.infinity.app.adapters.RocketAdapter;
import space.infinity.app.models.LaunchRocket;
import space.infinity.app.models.LaunchRocketsResponse;
import space.infinity.app.network.Client;
import space.infinity.app.network.Service;
import space.infinity.app.utils.Constants;

public class RocketsActivity extends AppCompatActivity {

    private LoadingDots progressBar;
    private RecyclerView recyclerView;
    private ActivityHelper activityHelper;
    private RocketAdapter rocketAdapter;
    private CoordinatorLayout coordinatorLayout;

    private boolean pagesOver = false;
    private int offset = 0;
    private boolean loading = true;
    private int previousTotal = 0;

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
        coordinatorLayout = findViewById(R.id.coordinator);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recycler);
        rocketAdapter = new RocketAdapter(this, new ArrayList<LaunchRocket>());
        recyclerView.setAdapter(rocketAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutAnimation(AnimationUtils
                .loadLayoutAnimation(this, R.anim.layout_animation_down));
        activityHelper = new ActivityHelper(this);
        activityHelper.onStart();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                activityHelper.handleScroll(gridLayoutManager);
            }
        });
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
            extends space.infinity.app.models.ActivityHelper {

        ActivityHelper(Context context) {
            super(context);
        }

        void handleScroll(LinearLayoutManager linearLayoutManager) {
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
                onStart();
                loading = true;
            }
        }

        @Override
        public void onStart() {
            if (pagesOver) return;
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setAlpha(0.3f);
            Call<LaunchRocketsResponse> call = Client
                    .getRetrofitClient(Constants.LAUNCH_LIBRARY_API)
                    .create(Service.class).getRockets(offset, 50);
            call.enqueue(new Callback<LaunchRocketsResponse>() {
                @Override
                public void onResponse(Call<LaunchRocketsResponse> call,
                                       Response<LaunchRocketsResponse> response) {
                    if (response.body() != null) {
                        List<LaunchRocket> rockets = response.body().getRockets();
                        rocketAdapter.add(rockets);
                        offset += 50;
                    }
                    else {
                        pagesOver = true;
                    }
                    showLayout();
                }

                @Override
                public void onFailure(Call<LaunchRocketsResponse> call, Throwable t) {
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
        public void onDestroy() { }

        @Override
        public void showLayout() {
            progressBar.setVisibility(View.GONE);
            recyclerView.setAlpha(1.0f);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
