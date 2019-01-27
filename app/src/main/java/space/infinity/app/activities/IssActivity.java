package space.infinity.app.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eyalbira.loadingdots.LoadingDots;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import space.infinity.app.R;
import space.infinity.app.network.CheckingConnection;
import space.infinity.app.utils.Constants;
import space.infinity.app.utils.Helper;

public class IssActivity extends AppCompatActivity {

    private class ActivityHelper
            extends space.infinity.app.models.ActivityHelper
            implements OnMapReadyCallback {

        @SuppressLint("StaticFieldLeak")
        private class GetData extends AsyncTask<String, Void, JSONObject> {

            @Override
            protected JSONObject doInBackground(String... strings) {

                String urlString = null;
                switch (strings.length) {
                    case 1:
                        urlString = strings[0];
                        break;
                }
                StringBuilder stringBuilder = new StringBuilder();
                HttpURLConnection httpURLConnection;
                URL url;
                JSONObject json = null;
                BufferedReader reader;
                try {
                    url = new URL(urlString);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    reader = new BufferedReader(new InputStreamReader(httpURLConnection
                            .getInputStream(), "iso-8859-1"), 8);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    reader.close();
                    httpURLConnection.disconnect();
                    json = new JSONObject(stringBuilder.toString());
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                return json;
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                super.onPostExecute(jsonObject);
                try {
                    DecimalFormat numberFormat = new DecimalFormat("#.00");
                    String velo = getResources().getString(R.string.velocity).concat(": ")
                            .concat(numberFormat.format(jsonObject
                                    .getDouble("velocity")).concat(" km/h"));
                    String alti = getResources().getString(R.string.altitude).concat(": ")
                            .concat(numberFormat.format(jsonObject
                                    .getDouble("altitude")).concat(" km"));
                    LatLng location = new LatLng(jsonObject.getDouble("latitude"),
                            jsonObject.getDouble("longitude"));
                    velocity.setText(velo);
                    altitude.setText(alti);
                    Helper.setAnimationForAll(getContext(), velocity);
                    Helper.setAnimationForAll(getContext(), altitude);
                    updateLocation(location, googleMap);
                } catch (JSONException e) {
                    e.printStackTrace();
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
            }
        }

        private Marker marker;
        private ScheduledExecutorService executorService;
        private GoogleMap googleMap;

        ActivityHelper(Context context) {
            setContext(context);
            executorService = Executors.newSingleThreadScheduledExecutor();
        }

        @Override
        public void onStart() {
            mapFragment.getMapAsync(this);
        }

        @Override
        public void onDestroy() {
            executorService.shutdown();
        }

        @Override
        public void showLayout() {
            progressBar.setVisibility(View.GONE);
            content.setVisibility(View.VISIBLE);
        }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            showLayout();
            getSomeData(googleMap);
            this.googleMap = googleMap;
        }

        private void getSomeData(final GoogleMap googleMap) {
            if (CheckingConnection.isConnected(getContext())) {
                googleMap.getUiSettings().setZoomGesturesEnabled(false);
                googleMap.getUiSettings().setAllGesturesEnabled(false);
                executorService.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("location", "updated");
                        new GetData().execute(Constants.ISS_NOW);
                    }
                }, 0, 5, TimeUnit.SECONDS);
            }
            else {
                Snackbar snackbar = Snackbar
                        .make(coordinator, "No Internet Connection", Snackbar.LENGTH_LONG)
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

        void updateLocation(LatLng location, GoogleMap googleMap) {
            if (googleMap == null) return;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(location);
            googleMap.animateCamera(cameraUpdate);
            if(marker == null) {
                marker = googleMap.addMarker(new MarkerOptions().position(location)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.iss_map))
                        .zIndex(3.0f).title("International Space Station"));
            }
            else {
                marker.setPosition(location);
            }
            marker.setVisible(true);
        }
    }

    private LoadingDots progressBar;
    private RelativeLayout content;
    private SupportMapFragment mapFragment;
    private TextView velocity;
    private TextView altitude;
    private CoordinatorLayout coordinator;
    private ActivityHelper activityHelper;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iss);
        Toolbar toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progress_bar);
        velocity = findViewById(R.id.velocity);
        altitude = findViewById(R.id.altitude);
        content = findViewById(R.id.content);
        coordinator = findViewById(R.id.coordinator);
        toolbar.setTitle(R.string.iss);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_view);
        activityHelper = new ActivityHelper(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        activityHelper.onStart();
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
                                if (location != null) {
                                    Log.i("locationn", location.toString());
                                    issPass(location);
                                } else {
                                    Snackbar snackbar = Snackbar
                                            .make(coordinator, getResources()
                                                    .getString(R.string.isserror), 8000)
                                            .setAction("Retry", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    findIssPass();
                                                }
                                            });
                                    snackbar.setActionTextColor(getResources()
                                            .getColor(R.color.primaryTextColor));
                                    snackbar.show();
                                }
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
                            if (location != null) {
                                Log.i("locationn", location.toString());
                                issPass(location);
                            } else {
                                Snackbar snackbar = Snackbar
                                        .make(coordinator, getResources()
                                                .getString(R.string.isserror), 8000)
                                        .setAction("Retry", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                findIssPass();
                                            }
                                        });
                                snackbar.setActionTextColor(getResources()
                                        .getColor(R.color.primaryTextColor));
                                snackbar.show();
                            }
                        }
                    });
        }
    }

    private void issPass(Location location) {
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();
        final String params = "lat=".concat(latitude.toString())
                .concat("&lon=").concat(longitude.toString())
                .concat("&n=3");
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder stringBuilder = new StringBuilder();
                HttpURLConnection httpURLConnection;
                URL url;
                JSONObject json;
                BufferedReader reader;
                try {
                    url = new URL(Constants.ISS_PASS.concat(params));
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    reader = new BufferedReader(new InputStreamReader(httpURLConnection
                            .getInputStream(), "iso-8859-1"), 8);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    reader.close();
                    httpURLConnection.disconnect();
                    json = new JSONObject(stringBuilder.toString());
                    JSONArray jsonArray = json.getJSONArray("response");
                    JSONObject object = (JSONObject) jsonArray.get(0);
                    final String date = Helper.unixToDate(object.getLong("risetime"));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
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
                    });
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
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
                    });
                }
            }
        }).start();
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
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityHelper.onDestroy();
    }
}
