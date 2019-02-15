package space.infinity.app.view.activity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import space.infinity.app.R;
import space.infinity.app.model.entity.LaunchSite;
import space.infinity.app.util.Constants;

public class LaunchSiteActivity extends AppCompatActivity implements OnMapReadyCallback {

    private LaunchSite launchSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_site);
        Toolbar toolbar = findViewById(R.id.toolbar);
        launchSite = getIntent().getParcelableExtra(Constants.LAUNCH_SITE);
        toolbar.setTitle(launchSite.getName());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map_view);
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
        String title = launchSite.getName();
        double latitude = launchSite.getLatitude();
        double longitude = launchSite.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        Marker marker = googleMap
                .addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(title));
        marker.setVisible(true);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15.0f);
        googleMap.animateCamera(cameraUpdate);
    }
}
