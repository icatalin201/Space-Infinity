package space.infinity.app.activities;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iss);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.my_awesome_toolbar);
        progressBar = findViewById(R.id.progress_bar);
        linearLayout = findViewById(R.id.main_layout);
        velocity = findViewById(R.id.velocity);
        altitude = findViewById(R.id.altitude);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_title.setText(R.string.iss);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_view);
        marker = null;
        loadAfterTime();
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

    private LatLng getLocationIss() throws ExecutionException, InterruptedException, JSONException {
        GetData getData = new GetData();
        JSONObject jsonObject = getData.execute(Constants.ISS_NOW).get();
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        String velo = getResources().getString(R.string.velocity).concat(": ")
                .concat(numberFormat.format(jsonObject.getDouble("velocity")).concat(" km/h"));
        String alti = getResources().getString(R.string.altitude).concat(": ")
                .concat(numberFormat.format(jsonObject.getDouble("altitude")).concat(" km"));
        velocity.setText(velo);
        altitude.setText(alti);

        LatLng location = new LatLng(jsonObject.getDouble("latitude"), jsonObject.getDouble("longitude"));
        progressBar.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
        velocity.setVisibility(View.VISIBLE);
        altitude.setVisibility(View.VISIBLE);
        Helper.setAnimationForAll(IssActivity.this, velocity);
        Helper.setAnimationForAll(IssActivity.this, altitude);

        return location;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        executorService.shutdown();
        return true;
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.getUiSettings().setZoomGesturesEnabled(false);

        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    final LatLng location = getLocationIss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("map", "updated");
                            updateLocation(location, googleMap);
                        }
                    });
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    void updateLocation(LatLng location, GoogleMap googleMap) {

        if(marker == null) {
            marker = googleMap.addMarker(new MarkerOptions().position(location)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.iss_map))
                    .zIndex(3.0f).title("International Space Station"));
        }
        else {
            marker.setPosition(location);
        }
        marker.setVisible(true);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(location);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        googleMap.animateCamera(cameraUpdate);

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
                    stringBuilder.append(line + "n");
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
