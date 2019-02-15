package space.infinity.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.eyalbira.loadingdots.LoadingDots;

import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.infinity.app.R;
import space.infinity.app.model.entity.Astronaut;
import space.infinity.app.util.Constants;
import space.infinity.app.util.OnItemClickListener;
import space.infinity.app.viewmodel.AstronautViewModel;
import space.infinity.app.viewmodel.adapters.AstronautAdapter;

public class AstronautsActivity extends AppCompatActivity implements OnItemClickListener {

    private LoadingDots progressBar;
    private RecyclerView recyclerView;
    private AstronautAdapter astronautAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astronauts);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.astronauts);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recycler);
        astronautAdapter = new AstronautAdapter(this);
        recyclerView.setAdapter(astronautAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutAnimation(AnimationUtils
                .loadLayoutAnimation(this, R.anim.layout_animation_down));
        AstronautViewModel astronautViewModel = ViewModelProviders.of(this)
                .get(AstronautViewModel.class);
        astronautViewModel.getAstronauts().observe(this, new Observer<List<Astronaut>>() {
            @Override
            public void onChanged(List<Astronaut> astronauts) {
                astronautAdapter.submitList(astronauts);
                progressBar.setVisibility(View.GONE);
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
    public void onItemClick(int position) {
        Astronaut astronaut = astronautAdapter.getAstronautAt(position);
        Intent intent = new Intent(this, AstronautActivity.class);
        intent.putExtra(Constants.ASTRONAUT, astronaut);
        AstronautAdapter.AstronautsViewHolder astronautsViewHolder =
                (AstronautAdapter.AstronautsViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        if (astronautsViewHolder != null) {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(this, astronautsViewHolder.getImage(),
                            "image");
            startActivity(intent, activityOptionsCompat.toBundle());
        }
    }
}
