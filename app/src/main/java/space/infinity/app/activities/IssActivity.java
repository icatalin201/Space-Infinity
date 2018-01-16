package space.infinity.app.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.infinity.app.R;
import space.infinity.app.models.ISS;
import space.infinity.app.network.Client;
import space.infinity.app.network.Service;
import space.infinity.app.utils.Constants;

public class IssActivity extends AppCompatActivity {

    private Call<ISS> issCall;
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
        loadIss();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void loadIss(){
        Service service = Client.getRetrofitClient(Constants.OPEN_NOTIFY_URL).create(Service.class);
        issCall = service.getISSNow();
        issCall.enqueue(new Callback<ISS>() {
            @Override
            public void onResponse(Call<ISS> call, Response<ISS> response) {
                if (!response.isSuccessful()){
                    issCall = call.clone();
                    Log.i("unsucces", "no");
                    issCall.enqueue(this);
                    return;
                }
                if (response.body() == null) return;

                Log.i("response", response.body().getAltitude());
            }

            @Override
            public void onFailure(Call<ISS> call, Throwable t) {

            }
        });
    }

}
