package space.infinity.app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import space.infinity.app.models.MarsRovers;
import space.infinity.app.models.RoverImages;
import space.infinity.app.network.Client;
import space.infinity.app.network.Service;
import space.infinity.app.utils.Constants;

public class MarsRoverActivity extends AppCompatActivity {

    private TextView toolbar_title;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private Call<MarsRovers> roversCall;
    private RoverCardAdapter roverCardAdapter;
    private List<RoverImages> roverImagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mars_rover);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.my_awesome_toolbar);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.images_recycler);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_title.setText(R.string.rover);

        roverImagesList = new ArrayList<>();
        roverCardAdapter = new RoverCardAdapter(this, roverImagesList);
        recyclerView.setAdapter(roverCardAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        loadImages();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void loadImages(){
        Service service = Client.getRetrofitClient(Constants.NASA_URL).create(Service.class);
        roversCall = service.getRoverImages(1000, Constants.API_KEY);
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

                for (RoverImages roverImage : response.body().getPhotos()){
                    roverImagesList.add(roverImage);
                }
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
