package space.infinity.app.view.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.eyalbira.loadingdots.LoadingDots;

import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import space.infinity.app.R;
import space.infinity.app.model.entity.SpaceFact;
import space.infinity.app.util.OnItemClickListener;
import space.infinity.app.viewmodel.SpaceFactViewModel;
import space.infinity.app.viewmodel.adapters.FactAdapter;

public class FactsActivity extends AppCompatActivity implements OnItemClickListener {

    private LoadingDots progressBar;
    private FactAdapter factAdapter;
    private RecyclerView recyclerView;
    private SpaceFactViewModel spaceFactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.facts);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        factAdapter = new FactAdapter(this);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(factAdapter);
        recyclerView.setHasFixedSize(true);
//        SnapHelper snapHelper = new PagerSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutAnimation(AnimationUtils
                .loadLayoutAnimation(this, R.anim.layout_animation_down));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false));
        progressBar = findViewById(R.id.progress_bar);
        spaceFactViewModel = ViewModelProviders.of(this)
                .get(SpaceFactViewModel.class);
        spaceFactViewModel.getSpaceFacts().observe(this, new Observer<List<SpaceFact>>() {
            @Override
            public void onChanged(List<SpaceFact> spaceFacts) {
                factAdapter.submitList(spaceFacts);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

//    public void next(View view) {
//        int position = recyclerView.getVerticalScrollbarPosition();
//        recyclerView.scrollToPosition(position + 1);
//    }
//
//    public void prev(View view) {
//        int position = recyclerView.getVerticalScrollbarPosition();
//        recyclerView.scrollToPosition(position - 1);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    public void onItemClick(int position) {
        SpaceFact spaceFact = factAdapter.getSpaceFactAt(position);
        spaceFact.setFavorite(!spaceFact.isFavorite());
        spaceFactViewModel.update(spaceFact);
    }
}
