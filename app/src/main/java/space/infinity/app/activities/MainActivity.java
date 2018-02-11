package space.infinity.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.infinity.app.R;
import space.infinity.app.models.apod.APOD;
import space.infinity.app.network.Client;
import space.infinity.app.network.Service;
import space.infinity.app.utils.Constants;
import space.infinity.app.utils.Helper;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private ScrollView mainLayout;
    private Boolean pressed;
    private Call<APOD> apodCall;
    private VideoView apodVideo;
    private ImageView apodImage;
    private ImageView roverImage;
    private ImageView galleryImage;
    private ImageView issImage;
    private ImageView newsImage;
    private ImageView wikiImage;
    private ImageView flaunches;
    private APOD apod;
    private Intent intent;

    private HashMap<ImageView, Integer> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(null);

        pressed = false;

        mainLayout = findViewById(R.id.main_layout);
        mProgressBar = findViewById(R.id.progress_bar);
        apodImage = findViewById(R.id.apod_image);
        apodVideo = findViewById(R.id.apod_video);
        roverImage = findViewById(R.id.rover_image);
        galleryImage = findViewById(R.id.gallery_image);
        issImage = findViewById(R.id.iss_image);
        newsImage = findViewById(R.id.news_image);
        wikiImage = findViewById(R.id.wiki_image);
        flaunches = findViewById(R.id.future_launches);
        images = new HashMap<>();
        images.put(roverImage, R.drawable.rover);
        images.put(galleryImage, R.drawable.gallery);
        images.put(issImage, R.drawable.iss);
        images.put(newsImage, R.drawable.news);
        images.put(wikiImage, R.drawable.wiki);
        images.put(flaunches, R.drawable.ulaunches);
        loadData();
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        if (pressed){
            super.onBackPressed();
        }
        else {
            Toast.makeText(this, R.string.exit, Toast.LENGTH_SHORT).show();
            pressed = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        pressed = false;
    }

    private void loadData(){
        Service service = Client.getRetrofitClient(Constants.NASA_URL).create(Service.class);
        apodCall = service.getAstronomyPictureOfTheDay(Constants.API_KEY);
        apodCall.enqueue(new Callback<APOD>() {
            @Override
            public void onResponse(Call<APOD> call, Response<APOD> response) {
                if (!response.isSuccessful()){
                    apodCall = call.clone();
                    apodCall.enqueue(this);
                    return;
                }
                if (response.body() == null) return;
                if (response.body().getMedia_type().equals("image")) {
                    Glide.with(MainActivity.this).load(response.body().getUrl())
                            .asBitmap()
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(apodImage);
                    apodVideo.setVisibility(View.GONE);
                    apodImage.setVisibility(View.VISIBLE);
                }
                apod = response.body();
                settingLayout();
            }

            @Override
            public void onFailure(Call<APOD> call, Throwable t) {

            }
        });
    }

    private void settingLayout(){
        for (Map.Entry<ImageView, Integer> entry : images.entrySet()){
            Glide.with(MainActivity.this).load(entry.getValue())
                    .asBitmap().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(entry.getKey());
        }
        mProgressBar.setVisibility(View.GONE);
        mainLayout.setVisibility(View.VISIBLE);
        Helper.setAnimationForAll(this, mainLayout);
    }

    public void showApodInfo(View view){
        intent = new Intent(this, ApodActivity.class);
        if (apod != null) {
            intent.putExtra("apod", apod);
        }
        startActivity(intent);
    }

    public void showRoverImages(View view){
        intent = new Intent(this, MarsActivity.class);
        startActivity(intent);
    }

    public void showImageGallery(View view){
        intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }


    public void showIssInfos(View view){
        intent = new Intent(this, IssActivity.class);
        startActivity(intent);
    }

    public void showNews(View view){

    }

    public void showWiki(View view){

    }

    public void futureLaunches(View view){
        intent = new Intent(this, UpcomingLaunches.class);
        startActivity(intent);
    }
}
