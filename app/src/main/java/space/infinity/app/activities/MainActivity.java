package space.infinity.app.activities;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.eyalbira.loadingdots.LoadingDots;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.infinity.app.R;
import space.infinity.app.models.APOD;
import space.infinity.app.models.ImageItem;
import space.infinity.app.network.Client;
import space.infinity.app.network.Service;
import space.infinity.app.utils.Constants;
import space.infinity.app.utils.Helper;
import space.infinity.app.utils.NotificationService;

public class MainActivity extends AppCompatActivity {

    private LoadingDots mProgressBar;
    private NestedScrollView mainLayout;
    private Boolean pressed;
    private ImageView apodImage;
    private APOD apod;

    private ActivityHelper activityHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pressed = false;
        mainLayout = findViewById(R.id.scrollView);
        mProgressBar = findViewById(R.id.progress_bar);
        apodImage = findViewById(R.id.apod_image);
        activityHelper = new ActivityHelper(this);
        activityHelper.onStart();
    }

    public void goEncyclopedia(View view) {
        startActivity(new Intent(this, EncyclopediasActivity.class));
    }

    public void goVoyager(View view) {
        startActivity(new Intent(this, VoyagerActivity.class));
    }

    public void goNewsFeed(View view) {
        startActivity(new Intent(this, NewsFeedActivity.class));
    }

    public void goRockets(View view) {
        startActivity(new Intent(this, RocketsActivity.class));
    }

    public void goAstronauts(View view) {
        startActivity(new Intent(this, AstronautsActivity.class));
    }

    public void goFacts(View view) {
        startActivity(new Intent(this, FactsActivity.class));
    }

    public void goGallery(View view) {
        startActivity(new Intent(this, GalleryActivity.class));
    }

    public void goIss(View view) {
        startActivity(new Intent(this, IssActivity.class));
    }

    public void goApod(View view) {
        Intent intent = new Intent(this, ApodActivity.class);
        if (apod != null) {
            ImageItem imageItem = new ImageItem();
            imageItem.setDateCreated(apod.getDate());
            imageItem.setDescription(apod.getExplanation());
            imageItem.setImage(apod.getHdurl());
            imageItem.setPhotographer(apod.getCopyright());
            imageItem.setTitle(apod.getTitle());
            intent.putExtra(Constants.IMAGE, imageItem);
        }
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, apodImage, "image");
        startActivity(intent, activityOptionsCompat.toBundle());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityHelper.onDestroy();
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
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.about_dialog);
                dialog.setTitle(null);
                dialog.show();
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

    private class ActivityHelper extends space.infinity.app.models.ActivityHelper {

        ActivityHelper(Context context) {
            super(context);
        }

        @Override
        public void onStart() {
            loadData();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public void showLayout() {
            mProgressBar.setVisibility(View.GONE);
            mainLayout.setVisibility(View.VISIBLE);
            Helper.customAnimation(getContext(), mainLayout, 1000, android.R.anim.fade_in);
        }

        private void loadData(){
            Service service = Client.getRetrofitClient(Constants.NASA_URL).create(Service.class);
            Call<APOD> apodCall = service.getAstronomyPictureOfTheDay(Constants.API_KEY);
            apodCall.enqueue(new Callback<APOD>() {
                @Override
                public void onResponse(Call<APOD> call, Response<APOD> response) {
                    if (!response.isSuccessful()){
                        getImageFromDb();
                        Log.i("main", "getting apod from db");
                        return;
                    }
                    if (response.body() == null) {
                        getImageFromDb();
                        return;
                    }
                    apod = response.body();
                    if (apod.getMedia_type().equals("image")) {
                        Glide.with(MainActivity.this)
                                .load(apod.getHdurl())
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .apply(RequestOptions.centerCropTransform())
                                .into(new SimpleTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource,
                                                                Transition<? super Drawable> glideAnimation) {
                                        apodImage.setImageDrawable(resource);
                                        showLayout();
                                    }
                                });
                    } else {
                        getImageFromDb();
                    }
                }

                @Override
                public void onFailure(Call<APOD> call, Throwable t) {
                    getImageFromDb();
                }
            });
        }

        private void getImageFromDb() {
//            Glide.with(MainActivity.this)
//                    .load(apod.getUrl())
//                    .transition(DrawableTransitionOptions.withCrossFade())
//                    .into(new SimpleTarget<Drawable>() {
//                        @Override
//                        public void onResourceReady(@NonNull Drawable resource,
//                                                    Transition<? super Drawable> glideAnimation) {
//                            apodImage.setImageDrawable(resource);
//                            showLayout();
//                        }
//                    });
            showLayout();
        }

    }

    private void setNotifications() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 30);
        Intent intent = new Intent(MainActivity.this, NotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,
                1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("notification-today", Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
        editor.apply();
    }
}
