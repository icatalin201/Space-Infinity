package space.infinity.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.infinity.app.R;
import space.infinity.app.adapters.RoverCardAdapter;
import space.infinity.app.models.mars.MarsRovers;
import space.infinity.app.models.mars.RoverImages;
import space.infinity.app.network.Client;
import space.infinity.app.network.Service;
import space.infinity.app.utils.Constants;

public class MarsRoverActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private Call<MarsRovers> roversCall;
    private RoverCardAdapter roverCardAdapter;
    private List<RoverImages> roverImagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mars_rover);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        Toolbar toolbar = findViewById(R.id.my_awesome_toolbar);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.images_recycler);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        roverImagesList = new ArrayList<>();
        roverCardAdapter = new RoverCardAdapter(this, roverImagesList);
        recyclerView.setAdapter(roverCardAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        String rover = getIntent().getStringExtra("rover");
        toolbar_title.setText(rover);

        loadImages(rover);
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
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void loadImages(String rover){
        Service service = Client.getRetrofitClient(Constants.NASA_URL).create(Service.class);

        switch (rover){
            case "Curiosity":
                roversCall = service.getCuriosityImages(1000, Constants.API_KEY);
                break;
            case "Opportunity":
                roversCall = service.getOpportunityImages(900, Constants.API_KEY);
                break;
            case "Spirit":
                roversCall = service.getSpiritImages(100, Constants.API_KEY);
                break;
        }
        roversCall.enqueue(new Callback<MarsRovers>() {
            @Override
            public void onResponse(Call<MarsRovers> call, Response<MarsRovers> response) {
                if (!response.isSuccessful()){
                    roversCall = call.clone();
                    roversCall.enqueue(this);
                    return;
                }
                if (response.body() == null) return;
                if (response.body().getPhotos() == null) return;

                roverImagesList.addAll(response.body().getPhotos());
                roverCardAdapter.notifyDataSetChanged();
                settingLayout();
            }

            @Override
            public void onFailure(Call<MarsRovers> call, Throwable t) {

            }
        });
    }

    private void settingLayout(){
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
