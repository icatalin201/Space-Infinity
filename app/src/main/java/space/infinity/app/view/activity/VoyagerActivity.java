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
import space.infinity.app.model.entity.Voyager;
import space.infinity.app.viewmodel.VoyagerViewModel;
import space.infinity.app.viewmodel.adapters.VoyagerAdapter;

public class VoyagerActivity extends AppCompatActivity {

    private LoadingDots loadingDots;
    private RecyclerView recyclerView;
    private VoyagerAdapter voyagerAdapter;

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
        VoyagerViewModel voyagerViewModel = ViewModelProviders.of(this)
                .get(VoyagerViewModel.class);
        voyagerViewModel.getVoyagers().observe(this, new Observer<List<Voyager>>() {
            @Override
            public void onChanged(List<Voyager> voyagers) {
                voyagerAdapter.add(voyagers);
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
}
