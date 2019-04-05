package space.infinity.app.view.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.infinity.app.R;
import space.infinity.app.model.entity.LaunchRocket;
import space.infinity.app.model.repository.RocketsRepository;
import space.infinity.app.viewmodel.adapters.RocketAdapter;

public class RocketsActivity extends AppCompatActivity implements RocketsRepository.RocketCallback {

    private LoadingDots progressBar;
    private RecyclerView recyclerView;
    private RocketAdapter rocketAdapter;
    private CoordinatorLayout coordinatorLayout;
    private RocketsRepository rocketsRepository;

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
        rocketsRepository = new RocketsRepository(this);
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
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                rocketsRepository.handleScroll(gridLayoutManager);
            }
        });
        rocketsRepository.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onSuccess(List<LaunchRocket> rocketList) {
        rocketAdapter.add(rocketList);
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
