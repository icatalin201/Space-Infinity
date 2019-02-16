package space.infinity.app.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eyalbira.loadingdots.LoadingDots;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.infinity.app.R;
import space.infinity.app.util.CheckingConnection;
import space.infinity.app.model.repository.ISSRepository;
import space.infinity.app.util.Helper;
import space.infinity.app.viewmodel.adapters.ISSCrewAdapter;

public class IssActivity extends AppCompatActivity
        implements ISSRepository.ISSCallback, OnMapReadyCallback {

    @Override
    public void onIssPass(String date) {
        final Snackbar snackbar = Snackbar
                .make(coordinator, getResources().getString(R.string.iss_pass_result)
                        .concat("\n").concat(date), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.primaryTextColor));
        snackbar.show();
    }

    @Override
    public void onIssPassFailure() {
        Snackbar snackbar = Snackbar
                .make(coordinator, getResources().getString(R.string.isserror), 8000)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findIssPass();
                    }
                });
        snackbar.setActionTextColor(getResources().getColor(R.color.primaryTextColor));
        snackbar.show();
    }

    @Override
    public void onGetAstronauts(final List<String> crewList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                issCrewAdapter.add(crewList);
                progressBar.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onFailure() {
        Snackbar snackbar = Snackbar
                .make(coordinator, "Unexpected error has occured.",
                        Snackbar.LENGTH_LONG)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recreate();
                    }
                });
        snackbar.setActionTextColor(getResources().getColor(R.color.primaryTextColor));
        snackbar.show();
    }

    @Override
    public void onUpdatePosition(LatLng latLng, String v, String a) {
        String date = "Updated at: ".concat(Helper.dateToString(Calendar.getInstance()));
        velocity.setText(String.format("%s: %s km/h", getString(R.string.velocity), v));
        altitude.setText(String.format("%s: %s km", getString(R.string.altitude), a));
        updatedAt.setText(date);
        Helper.setAnimationForAll(this, velocity);
        Helper.setAnimationForAll(this, altitude);
        Helper.setAnimationForAll(this, updatedAt);
    }

    private LoadingDots progressBar;
    private LinearLayout content;
    private TextView velocity;
    private TextView altitude;
    private TextView updatedAt;
    private CoordinatorLayout coordinator;
    private ISSCrewAdapter issCrewAdapter;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ISSRepository issRepository;
    private ScheduledExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iss);
        Toolbar toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progress_bar);
        velocity = findViewById(R.id.velocity);
        altitude = findViewById(R.id.altitude);
        content = findViewById(R.id.content);
        updatedAt = findViewById(R.id.updated_at);
        coordinator = findViewById(R.id.coordinator);
        RecyclerView crewRecycler = findViewById(R.id.crew_recycler);
        crewRecycler.setItemAnimator(new DefaultItemAnimator());
        crewRecycler.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        crewRecycler.setHasFixedSize(true);
        issCrewAdapter = new ISSCrewAdapter(this, new ArrayList<String>());
        crewRecycler.setAdapter(issCrewAdapter);
        toolbar.setTitle(R.string.iss);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        executorService = Executors.newSingleThreadScheduledExecutor();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_view);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        issRepository = new ISSRepository(this);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.i("location", "granted");
                fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this,
                        new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                issRepository.issPass(location);
                            }
                        });
            }
        }
        else {
            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION }, 1);
        }
    }

    private void findIssPass() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION }, 1);
        } else {
            fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this,
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            issRepository.issPass(location);
                        }
                    });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.iss, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pass:
                findIssPass();
                break;
            case R.id.live:
                startActivity(new Intent(this, ISSLiveStreamActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.getUiSettings().setZoomGesturesEnabled(false);
        if (CheckingConnection.isConnected(this)) {
            issRepository.getAstronautsInSpace();
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    issRepository.updatePosition(googleMap);
                }
            }, 0, 5, TimeUnit.SECONDS);
        } else {
            Snackbar snackbar = Snackbar
                    .make(coordinator, "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            recreate();
                        }
                    });
            snackbar.setActionTextColor(getResources().getColor(R.color.primaryTextColor));
            snackbar.show();
        }
    }
}
