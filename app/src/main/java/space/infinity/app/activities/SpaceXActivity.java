package space.infinity.app.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eyalbira.loadingdots.LoadingDots;

import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.infinity.app.R;
import space.infinity.app.models.SpaceXRoadster;
import space.infinity.app.network.Client;
import space.infinity.app.network.Service;
import space.infinity.app.utils.Constants;
import space.infinity.app.utils.Helper;

public class SpaceXActivity extends AppCompatActivity {

    private LoadingDots loadingDots;
    private NestedScrollView content;
    private LinearLayout error;
    private ActivityHelper activityHelper;

    private TextView details;
    private TextView wikipedia;
    private TextView earthDistance;
    private TextView marsDistance;
    private TextView speed;
    private TextView updatedAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_x);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.spacex_roadster);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        loadingDots = findViewById(R.id.progress_bar);
        content = findViewById(R.id.content);
        details = findViewById(R.id.details);
        wikipedia = findViewById(R.id.wikipedia);
        updatedAt = findViewById(R.id.updated_at);
        earthDistance = findViewById(R.id.earth_distance);
        marsDistance = findViewById(R.id.mars_distance);
        error = findViewById(R.id.error);
        speed = findViewById(R.id.speed);
        activityHelper = new ActivityHelper(this);
        activityHelper.onStart();
    }

    public void retry(View view) {
        activityHelper.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityHelper.onDestroy();
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

    private class ActivityHelper extends space.infinity.app.models.ActivityHelper {

        private SpaceXRoadster spaceXRoadster;

        public ActivityHelper(Context context) {
            super(context);
        }

        @Override
        public void onStart() {
            loadingDots.setVisibility(View.VISIBLE);
            error.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            Call<SpaceXRoadster> call = Client.getRetrofitClient(Constants.SPACEX_API)
                    .create(Service.class).getSpaceXRoadster();
            call.enqueue(new Callback<SpaceXRoadster>() {
                @Override
                public void onResponse(Call<SpaceXRoadster> call, Response<SpaceXRoadster> response) {
                    if (response.body() != null) {
                        spaceXRoadster = response.body();
                        details.setText(spaceXRoadster.getDetails());
                        earthDistance.setText(String.format(Locale.getDefault(),
                                "Earth distance: %.2f km",
                                spaceXRoadster.getEarthDistanceKm()));
                        marsDistance.setText(String.format(Locale.getDefault(),
                                "Mars distance: %.2f km",
                                spaceXRoadster.getMarsDistanceKm()));
                        speed.setText(String.format(Locale.getDefault(),
                                "Speed: %.2f km/h",
                                spaceXRoadster.getSpeedKph()));
                        updatedAt.setText(String.format("Updated at: %s",
                                Helper.dateToString(Calendar.getInstance())));
                        loadingDots.setVisibility(View.GONE);
                        content.setVisibility(View.VISIBLE);
                        error.setVisibility(View.GONE);
                        wikipedia.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse(spaceXRoadster.getWikipedia()));
                                startActivity(browserIntent);
                            }
                        });
                    } else {
                        loadingDots.setVisibility(View.GONE);
                        error.setVisibility(View.VISIBLE);
                        content.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<SpaceXRoadster> call, Throwable t) {
                    t.printStackTrace();
                    loadingDots.setVisibility(View.GONE);
                    error.setVisibility(View.VISIBLE);
                    content.setVisibility(View.GONE);
                }
            });
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public void showLayout() {

        }
    }
}
