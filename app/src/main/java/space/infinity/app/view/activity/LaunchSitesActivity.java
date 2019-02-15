package space.infinity.app.view.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.eyalbira.loadingdots.LoadingDots;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.infinity.app.R;
import space.infinity.app.model.entity.LaunchSite;
import space.infinity.app.viewmodel.LaunchSiteViewModel;
import space.infinity.app.viewmodel.adapters.LaunchSitesAdapter;

public class LaunchSitesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LoadingDots loadingDots;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutAnimation(AnimationUtils
                .loadLayoutAnimation(this, R.anim.layout_animation_down));
        loadingDots = findViewById(R.id.progress_bar);
        LaunchSiteViewModel launchSiteViewModel = ViewModelProviders.of(this)
                .get(LaunchSiteViewModel.class);
        launchSiteViewModel.getLaunchSites().observe(this, new Observer<List<LaunchSite>>() {
            @Override
            public void onChanged(List<LaunchSite> launchSites) {
                launchSitesAdapter.add(launchSites);
                loadingDots.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
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
    public void onBackPressed() {
        super.onBackPressed();
    }
}
