package space.infinity.app.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.infinity.app.R;
import space.infinity.app.adapters.LaunchCardAdapter;
import space.infinity.app.models.Launch;
import space.infinity.app.network.Client;
import space.infinity.app.network.Service;
import space.infinity.app.utils.Constants;

public class UpcomingLaunches extends AppCompatActivity {

    private TextView toolbar_title;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private List<Launch> launches;
    private LaunchCardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_launches);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.my_awesome_toolbar);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.launches_recycler);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_title.setText(R.string.spacex_flaunches);
        launches = new ArrayList<>();
        adapter = new LaunchCardAdapter(this, launches);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        new GetDatas().execute(Constants.SPACEX_API);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

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
                    stringBuilder.append(line + "n");
                }
                reader.close();
                httpURLConnection.disconnect();
                jsonArray = new JSONArray(stringBuilder.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonArray;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            Log.i("result", jsonArray.toString());
        }
    }
}
