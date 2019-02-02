package space.infinity.app.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.infinity.app.R;
import space.infinity.app.dao.ImageItemDao;
import space.infinity.app.database.AppDatabase;
import space.infinity.app.database.AppDatabaseHelper;
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
    private ImageView spacexImage;
    private CoordinatorLayout coordinatorLayout;
    private APOD apod;

    private ActivityHelper activityHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pressed = false;
        spacexImage = findViewById(R.id.spacex_roadster);
        mainLayout = findViewById(R.id.scrollView);
        mProgressBar = findViewById(R.id.progress_bar);
        apodImage = findViewById(R.id.apod_image);
        coordinatorLayout = findViewById(R.id.coordinator);
        activityHelper = new ActivityHelper(this);
        activityHelper.onStart();
        NotificationService.stopJob(this);
        NotificationService.launchJob(this, NotificationService.class, 43200000);
    }

    public void goEncyclopedia(View view) {
        startActivity(new Intent(this, EncyclopediasActivity.class));
    }

    public void goSpaceXRoadster(View view) {
        Intent intent = new Intent(this, SpaceXActivity.class);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, spacexImage, "image");
        startActivity(intent, activityOptionsCompat.toBundle());
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

    public void goLaunchSites(View view) {
        startActivity(new Intent(this, LaunchSitesActivity.class));
    }

    public void goFutureLaunches(View view) {
        startActivity(new Intent(this, RocketLaunchesActivity.class));
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
        startActivity(intent);
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

    private void share() {
        String packageName = getPackageName();
        Intent appShareIntent = new Intent(Intent.ACTION_SEND);
        appShareIntent.setType("text/plain");
        String extraText = String.format("Hy there! Try this awesome space app - %s.",
                getResources().getString(R.string.app_name));
        extraText += "https://play.google.com/store/apps/details?id=" + packageName;
        appShareIntent.putExtra(Intent.EXTRA_TEXT, extraText);
        startActivity(appShareIntent);
    }

    private void rate() {
        String packageName = getPackageName();
        String playStoreLink = "https://play.google.com/store/apps/details?id=" + packageName;
        Intent app = new Intent(Intent.ACTION_VIEW, Uri.parse(playStoreLink));
        startActivity(app);
    }

    private void follow() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://twitter.com/icatalin201"));
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                share();
                return true;

            case R.id.follow:
                follow();
                return true;

            case R.id.rate:
                rate();
                return true;

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
                        Glide.with(getContext())
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
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    recreate();
                                }
                            });
                    snackbar.setActionTextColor(getResources().getColor(R.color.primaryTextColor));
                    snackbar.show();
                }
            });
        }

        private void getImageFromDb() {
            final AppDatabase appDatabase = AppDatabaseHelper.getDatabase(getContext());
            final ImageItemDao imageItemDao = appDatabase.getImageItemDao();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final ImageItem imageItem = imageItemDao.getLastImage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(getContext())
                                    .load(imageItem.getImage())
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .apply(RequestOptions.centerCropTransform())
                                    .into(new SimpleTarget<Drawable>() {
                                        @Override
                                        public void onResourceReady(@NonNull Drawable resource,
                                                                    Transition<? super Drawable> glideAnimation) {
                                            apodImage.setImageDrawable(resource);
                                            showLayout();
                                        }

                                        @Override
                                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                            super.onLoadFailed(errorDrawable);
                                            Snackbar snackbar = Snackbar
                                                    .make(coordinatorLayout, "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                                                    .setAction("Retry", new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            recreate();
                                                        }
                                                    });
                                            snackbar.setActionTextColor(getResources().getColor(R.color.primaryTextColor));
                                            snackbar.show();
                                        }
                                    });
                        }
                    });
                    appDatabase.close();
                }
            }).start();
        }

    }
}
