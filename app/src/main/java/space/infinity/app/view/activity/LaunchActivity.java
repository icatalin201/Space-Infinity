package space.infinity.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
        Launch launch = getIntent().getParcelableExtra(Constants.LAUNCH);
        LaunchRocket rocket = launch.getRocket();
        launchPads = launch.getLocation().getPads();
        List<LaunchMission> launchMissionList = launch.getMissions();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        loadingDots = findViewById(R.id.progress_bar);
        nestedScrollView = findViewById(R.id.scroll);
        TextView missionsLabel = findViewById(R.id.missions);
        MissionsAdapter missionsAdapter = new MissionsAdapter(this, new ArrayList<LaunchMission>());
        RecyclerView recyclerView = findViewById(R.id.missions_recycler);
        recyclerView.setAdapter(missionsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map_view);
        ImageView launchImage = findViewById(R.id.image);
        TextView launchName = findViewById(R.id.name);
        TextView launchStatus = findViewById(R.id.launch_status);
        TextView launchDate = findViewById(R.id.date);
        TextView launchRocketName = findViewById(R.id.rocket_name);
        TextView launchRocketWiki = findViewById(R.id.rocket_wiki_url);
        TextView launchLocationName = findViewById(R.id.location_name);
        Glide.with(this)
                .load(rocket.getImageURL())
                .apply(RequestOptions.centerCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(launchImage);
        launchName.setText(launch.getName());
        String status = "";
        int color = 0;
        switch (launch.getStatus()) {
            case 1:
                status = "Available for launch";
                color = getColor(android.R.color.holo_green_dark);
                break;
            case 2:
                status = "Not available for launch";
                color = getColor(android.R.color.holo_red_dark);
                break;
            case 3:
                status = "Launch successfully";
                color = getColor(android.R.color.holo_green_dark);
                break;
            case 4:
                status = "Launch failed";
                color = getColor(android.R.color.holo_red_dark);
                break;
        }
        launchStatus.setText(status);
        if (color != 0) {
            launchStatus.setTextColor(color);
        }
        launchDate.setText(launch.getNet());
        launchRocketName.setText(rocket.getName());
        launchRocketWiki.setTag(rocket.getWikiURL());
        launchRocketWiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LaunchActivity.this, InternalWebActivity.class);
                intent.putExtra("url", view.getTag().toString());
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