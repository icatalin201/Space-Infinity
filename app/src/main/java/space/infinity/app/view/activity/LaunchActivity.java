package space.infinity.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.eyalbira.loadingdots.LoadingDots;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.infinity.app.R;
import space.infinity.app.model.entity.Launch;
import space.infinity.app.model.entity.LaunchMission;
import space.infinity.app.model.entity.LaunchPad;
import space.infinity.app.model.entity.LaunchRocket;
import space.infinity.app.util.Constants;
import space.infinity.app.viewmodel.adapters.MissionsAdapter;

public class LaunchActivity extends AppCompatActivity implements OnMapReadyCallback {

    private List<LaunchPad> launchPads;
    private LoadingDots loadingDots;
    private NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        final Launch launch = getIntent().getParcelableExtra(Constants.LAUNCH);
        final LaunchRocket rocket = launch.getRocket();
        launchPads = launch.getLocation().getPads();
        List<LaunchMission> launchMissionList = launch.getMissions();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        loadingDots = findViewById(R.id.progress_bar);
        nestedScrollView = findViewById(R.id.scroll);
        TextView missionsLabel = findViewById(R.id.missions);
        MissionsAdapter missionsAdapter = new MissionsAdapter(this, new ArrayList<LaunchMission>());
        RecyclerView recyclerView = findViewById(R.id.missions_recycler);
        recyclerView.setAdapter(missionsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map_view);
        ImageView launchImage = findViewById(R.id.rocket_image);
        TextView launchName = findViewById(R.id.name);
        Button button = findViewById(R.id.watch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LaunchActivity.this, InternalWebActivity.class);
                intent.putExtra("url", launch.getVidURLs()[0]);
                startActivity(intent);
            }
        });
        TextView launchStatus = findViewById(R.id.launch_status);
        CardView statusCard = findViewById(R.id.card_status);
        LinearLayout clockLayout = findViewById(R.id.clock);
        View divider2 = findViewById(R.id.divider2);
        View divider3 = findViewById(R.id.divider3);
        TextView launchDate = findViewById(R.id.date);
        TextView launchLocationName = findViewById(R.id.location_name);
        final TextView days = findViewById(R.id.days);
        final TextView hours = findViewById(R.id.hours);
        final TextView minutes = findViewById(R.id.minutes);
        final TextView seconds = findViewById(R.id.seconds);
        Glide.with(this)
                .load(rocket.getImageURL())
                .apply(RequestOptions.centerCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(launchImage);
        String status = "";
        int color = 0;
        if (launch.getVidURLs().length > 0) {
            button.setVisibility(View.VISIBLE);
            divider3.setVisibility(View.VISIBLE);
        }
        switch (launch.getStatus()) {
            case 1:
                status = "Available for launch";
                color = getColor(android.R.color.holo_green_dark);
                long now = Calendar.getInstance().getTimeInMillis();
                long countDown = launch.getNetstamp() * 1000 - now;
                final Calendar calendar = Calendar.getInstance();
                new CountDownTimer(countDown, 1000) {
                    @Override
                    public void onTick(long l) {
                        calendar.setTimeInMillis(l);
                        long d = calendar.get(Calendar.DAY_OF_MONTH);
                        long h = calendar.get(Calendar.HOUR);
                        long m = calendar.get(Calendar.MINUTE);
                        long s = calendar.get(Calendar.SECOND);
                        String dString, hString, mString, sString;
                        if (d < 10) {
                            dString = String.format("0%s", d);
                        } else {
                            dString = String.valueOf(d);
                        }
                        if (h < 10) {
                            hString = String.format("0%s", h);
                        } else {
                            hString = String.valueOf(h);
                        }
                        if (s < 10) {
                            sString = String.format("0%s", s);
                        } else {
                            sString = String.valueOf(s);
                        }
                        if (m < 10) {
                            mString = String.format("0%s", m);
                        } else {
                            mString = String.valueOf(m);
                        }
                        days.setText(dString);
                        hours.setText(hString);
                        minutes.setText(mString);
                        seconds.setText(sString);
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
                clockLayout.setVisibility(View.VISIBLE);
                divider2.setVisibility(View.VISIBLE);
                break;
            case 2:
                status = "Not available for launch";
                color = getColor(android.R.color.holo_red_dark);
                break;
            case 3:
                status = "Successfully launched";
                color = getColor(android.R.color.holo_green_dark);
                break;
            case 4:
                status = "Launch failed";
                color = getColor(android.R.color.holo_red_dark);
                break;
        }
        launchStatus.setText(status);
        if (color != 0) {
            statusCard.setCardBackgroundColor(color);
        }
        launchDate.setText(launch.getNet());
        launchName.setText(launch.getName());
        collapsingToolbarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LaunchActivity.this, InternalWebActivity.class);
                intent.putExtra("url", rocket.getWikiURL());
                startActivity(intent);
            }
        });
        launchLocationName.setText(launchPads.get(0).getName());
        missionsAdapter.add(launchMissionList);
        if (launchMissionList != null && launchMissionList.size() > 0) {
            missionsLabel.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        for (LaunchPad launchPad : launchPads) {
            String title = launchPad.getName();
            double latitude = Double.parseDouble(launchPad.getLatitude());
            double longitude = Double.parseDouble(launchPad.getLongitude());
            LatLng latLng = new LatLng(latitude, longitude);
            Marker marker = googleMap
                    .addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(title));
            marker.setVisible(true);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15.0f);
            googleMap.animateCamera(cameraUpdate);
        }
        loadingDots.setVisibility(View.GONE);
        nestedScrollView.setVisibility(View.VISIBLE);
    }
}
