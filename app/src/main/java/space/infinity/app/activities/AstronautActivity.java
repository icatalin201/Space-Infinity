package space.infinity.app.activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import space.infinity.app.R;
import space.infinity.app.models.Astronaut;
import space.infinity.app.utils.Constants;

public class AstronautActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_astronaut);
        Toolbar toolbar = findViewById(R.id.toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar_layout);
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        ImageView imageView = findViewById(R.id.image);
        TextView personalData = findViewById(R.id.personal_data);
        TextView summary = findViewById(R.id.summary);
        TextView experience = findViewById(R.id.experience);
        TextView education = findViewById(R.id.education);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        final Astronaut astronaut = getIntent().getParcelableExtra(Constants.ASTRONAUT);
        Glide.with(this)
                .load(astronaut.getImage())
                .apply(RequestOptions.circleCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
        personalData.setText(astronaut.getName().concat("\n").concat(astronaut.getPersonalData()));
        summary.setText(astronaut.getSummary());
        experience.setText(astronaut.getExperience());
        education.setText(astronaut.getEducation());
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (appBarLayout.getTotalScrollRange() + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(astronaut.getName());
                } else {
                    collapsingToolbarLayout.setTitle("");
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        supportFinishAfterTransition();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
    }
}
