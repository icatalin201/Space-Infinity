package space.infinity.app.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.infinity.app.R;
import space.infinity.app.models.APOD;
import space.infinity.app.network.Client;
import space.infinity.app.network.Service;
import space.infinity.app.utils.Constants;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private ScrollView mainLayout;
    private Boolean pressed;
    private Call<APOD> apodCall;
    private ImageView apodImage;
    private ImageView roverImage;
    private ImageView galleryImage;
    private ImageView epicImage;
    private ImageView issImage;
    private ImageView newsImage;
    private ImageView wikiImage;
    private APOD apod;

    private HashMap<ImageView, Drawable> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(null);

        pressed = false;
        mainLayout = findViewById(R.id.main_layout);
        mProgressBar = findViewById(R.id.progress_bar);
        apodImage = findViewById(R.id.apod_image);
        roverImage = findViewById(R.id.rover_image);
        galleryImage = findViewById(R.id.gallery_image);
        epicImage = findViewById(R.id.epic_image);
        issImage = findViewById(R.id.iss_image);
        newsImage = findViewById(R.id.news_image);
        wikiImage = findViewById(R.id.wiki_image);
        images = new HashMap<>();
        images.put(roverImage, getDrawable(R.drawable.rover));
        images.put(galleryImage, getDrawable(R.drawable.gallery));
        images.put(epicImage, getDrawable(R.drawable.epic));
        images.put(issImage, getDrawable(R.drawable.iss));
        images.put(newsImage, getDrawable(R.drawable.news));
        images.put(wikiImage, getDrawable(R.drawable.wiki));
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
        for (Map.Entry<ImageView, Drawable> entry : images.entrySet()){
            entry.getKey().setImageDrawable(entry.getValue());
            entry.getKey().setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        mProgressBar.setVisibility(View.GONE);
        mainLayout.setVisibility(View.VISIBLE);
    }

    public void showApodInfo(View view){
        Intent intent = new Intent(MainActivity.this, ApodActivity.class);
        if (apod != null) {
            intent.putExtra("apod", apod);
        }
        startActivity(intent);
    }

    public void showRoverImages(View view){

    }

    public void showImageGallery(View view){

    }

    public void showEpicImages(View view){

    }

    public void showIssInfos(View view){

    }

    public void showNews(View view){

    }

    public void showWiki(View view){

    }
}
