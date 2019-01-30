package space.infinity.app.activities;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import space.infinity.app.R;
import space.infinity.app.models.Launch;
import space.infinity.app.models.LaunchLocation;
import space.infinity.app.models.LaunchMission;
import space.infinity.app.models.LaunchPad;
import space.infinity.app.models.LaunchRocket;
import space.infinity.app.models.Rocket;
import space.infinity.app.utils.Constants;

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
        RecyclerView recyclerView = findViewById(R.id.missions_recycler);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map_view);
        ImageView launchImage = findViewById(R.id.image);
        TextView launchName = findViewById(R.id.name);
        TextView launchStatus = findViewById(R.id.launch_status);
        TextView launchDate = findViewById(R.id.date);
        TextView launchRocketName = findViewById(R.id.rocket_name);
        TextView launchRocketWiki = findViewById(R.id.rocket_wiki_url);
        TextView launchLocationName = findViewById(R.id.location_name);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
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
        launchRocketWiki.setText(rocket.getWikiURL());
        launchLocationName.setText(launchPads.get(0).getName());
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
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10.0f);
            googleMap.animateCamera(cameraUpdate);
        }
        loadingDots.setVisibility(View.GONE);
        nestedScrollView.setVisibility(View.VISIBLE);
    }
}
