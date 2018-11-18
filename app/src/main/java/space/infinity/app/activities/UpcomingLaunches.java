package space.infinity.app.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import space.infinity.app.R;
import space.infinity.app.adapters.LaunchCardAdapter;
import space.infinity.app.models.launch.Launch;
import space.infinity.app.models.launch.LaunchSite;
import space.infinity.app.models.launch.Payloads;
import space.infinity.app.models.launch.Rocket;
import space.infinity.app.models.launch.SecondStage;
import space.infinity.app.network.CheckingConnection;
import space.infinity.app.utils.Constants;
import space.infinity.app.utils.Helper;

public class UpcomingLaunches extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private List<Launch> launches;
    private LaunchCardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_launches);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        Toolbar toolbar = findViewById(R.id.my_awesome_toolbar);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.launches_recycler);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar_title.setText(R.string.spacex_flaunches);
        launches = new ArrayList<>();
        adapter = new LaunchCardAdapter(this, launches);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if (CheckingConnection.isConnected(this)) {
            new GetDatas().execute(Constants.SPACEX_API);
        }
        else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
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

    @SuppressLint("StaticFieldLeak")
    private class GetDatas extends AsyncTask<String, Void, JSONArray>{

        @Override
        protected JSONArray doInBackground(String... strings) {

            HttpURLConnection httpURLConnection;
            URL url;
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader;
            JSONArray jsonArray = null;
            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"iso-8859-1"), 8);
                String line;
                while ((line = reader.readLine()) != null){
                    stringBuilder.append(line);
                }
                reader.close();
                httpURLConnection.disconnect();
                jsonArray = new JSONArray(stringBuilder.toString());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return jsonArray;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            Log.i("result", jsonArray.toString());
            JSONObject object;
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    object = (JSONObject) jsonArray.get(i);
                    JSONObject rocketJson = (JSONObject) object.get("rocket");
                    JSONObject launchSiteJson = (JSONObject) object.get("launch_site");
                    JSONArray payloadsArray = rocketJson.getJSONObject("second_stage").getJSONArray("payloads");
                    List<Payloads> payloadsList = new ArrayList<>();
                    for (int j = 0; j < payloadsArray.length(); j++){
                        JSONObject json = payloadsArray.getJSONObject(j);
                        payloadsList.add(new Payloads(json.getString("payload_id"),
                                json.getBoolean("reused"), (String) json.getJSONArray("customers").get(0),
                                json.getString("payload_type"), null));
                    }
                    Rocket rocket = new Rocket(rocketJson.getString("rocket_id"), rocketJson.getString("rocket_name"),
                            rocketJson.getString("rocket_type"),
                            new SecondStage(payloadsList));
                    LaunchSite launchSite = new LaunchSite(launchSiteJson.getString("site_id"),
                            launchSiteJson.getString("site_name"), launchSiteJson.getString("site_name_long"));
                    launches.add(new Launch(object.getInt("flight_number"),
                            object.getString("launch_year"), object.getLong("launch_date_unix"),
                            rocket, launchSite));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            adapter.notifyDataSetChanged();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    }, 2000);
                }
            });

            Helper.setAnimationForAll(UpcomingLaunches.this, recyclerView);
        }
    }
}
