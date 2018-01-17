package space.infinity.app.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import space.infinity.app.R;
import space.infinity.app.utils.Constants;

public class IssActivity extends AppCompatActivity {

    private TextView toolbar_title;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iss);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_title.setText("ISS");

        GetData getData = new GetData();
        getData.execute(Constants.ISS_NOW);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private class GetData extends AsyncTask<String, Void, JSONObject>{

        @Override
        protected JSONObject doInBackground(String... strings) {
            
            String urlString = null;
            switch (strings.length){
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
                while ((line = reader.readLine()) != null){
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
