package space.infinity.app.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import space.infinity.app.R;
import space.infinity.app.models.Rocket;
import space.infinity.app.utils.Constants;

public class RocketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocket);
        Rocket rocket = getIntent().getParcelableExtra(Constants.ROCKET);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(rocket.getName());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ImageView imageView = findViewById(R.id.image);
        Glide.with(this)
                .load(rocket.getImage())
                .apply(RequestOptions.centerCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
        TextView costPerLaunch = findViewById(R.id.cost_per_launch);
        TextView height = findViewById(R.id.height);
        TextView diameter = findViewById(R.id.diameter);
        TextView mass = findViewById(R.id.mass);
        TextView stages = findViewById(R.id.stages);
        TextView totalLaunches = findViewById(R.id.total_launches);
        TextView successes = findViewById(R.id.successes);
        TextView failures = findViewById(R.id.failures);
        TextView status = findViewById(R.id.status);
        TextView name = findViewById(R.id.name);
        name.setText(rocket.getName());
        costPerLaunch.setText(String.format("Cost per launch: %s", rocket.getCostPerLaunch()));
        height.setText(String.format("Height: %s", rocket.getHeight()));
        diameter.setText(String.format("Diameter: %s", rocket.getDiameter()));
        mass.setText(String.format("Mass: %s", rocket.getMass()));
        stages.setText(String.format("Stages: %s", rocket.getStages()));
        totalLaunches.setText(String.format("Total launches: %s", rocket.getTotalLaunches()));
        successes.setText(String.format("Successes: %s", rocket.getSuccesses()));
        failures.setText(String.format("Failures: %s", rocket.getFailures()));
        status.setText(String.format("Status: %s", rocket.getStatus()));
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
