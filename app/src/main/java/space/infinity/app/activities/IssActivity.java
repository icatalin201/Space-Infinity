package space.infinity.app.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import space.infinity.app.R;
import space.infinity.app.utils.Constants;
import space.infinity.app.utils.Helper;

public class IssActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView toolbar_title;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private SupportMapFragment mapFragment;
    private TextView velocity;
    private TextView altitude;
    private ScheduledExecutorService executorService;
    private Marker marker;
    private CoordinatorLayout coordinator;

    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iss);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.my_awesome_toolbar);
        progressBar = findViewById(R.id.progress_bar);
        linearLayout = findViewById(R.id.main_layout);
        velocity = findViewById(R.id.velocity);
        altitude = findViewById(R.id.altitude);
        coordinator = findViewById(R.id.coordinator);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_title.setText(R.string.iss);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_view);
        marker = null;
        loadAfterTime();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION }, 1);
        }
        else {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Log.i("locationn", location.toString());
                                SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("lat", Double.toString(location.getLatitude()));
                                editor.putString("lon", Double.toString(location.getLongitude()));
                                editor.apply();
                            }
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.i("location", "granted");
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    Log.i("locationn", location.toString());
                                    SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("lat", Double.toString(location.getLatitude()));
                                    editor.putString("lon", Double.toString(location.getLongitude()));
                                    editor.apply();
                                }
                            }
                        });
            }
        }
    }

    public void issPass(View view) {

        String lat = getPreferences(Context.MODE_PRIVATE).getString("lat", "");
        String lon = getPreferences(Context.MODE_PRIVATE).getString("lon", "");
        String params = "lat=".concat(lat).concat("&lon=").concat(lon).concat("&n=1");
        try {
            JSONObject jsonObject = new GetData().execute(Constants.ISS_PASS.concat(params)).get();
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            JSONObject object = (JSONObject) jsonArray.get(0);
            String date = Helper.unixToDate(object.getLong("risetime"));

            Snackbar snackbar = Snackbar
                    .make(coordinator, getResources().getString(R.string.iss_pass_result)
                            .concat(":\n").concat(date), 8000);
            snackbar.show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadAfterTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mapFragment.getMapAsync(IssActivity.this);
            }
        }, 300);
    }

    private LatLng getLocationIss() {
        GetData getData = new GetData();
        JSONObject jsonObject = null;
        LatLng location = null;

        try {
            jsonObject = getData.execute(Constants.ISS_NOW).get();
            DecimalFormat numberFormat = new DecimalFormat("#.00");
            final String velo = getResources().getString(R.string.velocity).concat(": ")
                    .concat(numberFormat.format(jsonObject.getDouble("velocity")).concat(" km/h"));
            final String alti = getResources().getString(R.string.altitude).concat(": ")
                    .concat(numberFormat.format(jsonObject.getDouble("altitude")).concat(" km"));
            location = new LatLng(jsonObject.getDouble("latitude"), jsonObject.getDouble("longitude"));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    velocity.setText(velo);
                    altitude.setText(alti);
                    progressBar.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                    velocity.setVisibility(View.VISIBLE);
                    altitude.setVisibility(View.VISIBLE);
                    Helper.setAnimationForAll(IssActivity.this, velocity);
                    Helper.setAnimationForAll(IssActivity.this, altitude);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return location;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info:
                Intent aboutIntent = new Intent(getApplicationContext(), About.class);
                startActivity(aboutIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        executorService.shutdown();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.getUiSettings().setZoomGesturesEnabled(false);

        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.i("location", "updated");
                final LatLng location = getLocationIss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("map", "updated");
                        updateLocation(location, googleMap);
                    }
                });
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    void updateLocation(LatLng location, GoogleMap googleMap) {

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
            BufferedReader reader = null;
            try {
                url = new URL(urlString);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "iso-8859-1"), 8);
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
                httpURLConnection.disconnect();
                json = new JSONObject(stringBuilder.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            Log.i("Result", json.toString());
        }
    }


}
