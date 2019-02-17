package space.infinity.app.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.widget.NestedScrollView;
import space.infinity.app.R;
import space.infinity.app.model.entity.ImageItem;
import space.infinity.app.model.repository.ApodRepository;
import space.infinity.app.util.Constants;
import space.infinity.app.util.Helper;
import space.infinity.app.util.NotificationService;

import static space.infinity.app.util.Helper.launchJob;

public class MainActivity extends AppCompatActivity implements ApodRepository.ApodCallback {

    private LoadingDots mProgressBar;
    private NestedScrollView mainLayout;
    private Boolean pressed;
    private ImageView apodImage;
    private ImageView spacexImage;
    private ImageItem imageItem;
    private CoordinatorLayout coordinatorLayout;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pressed = false;
        toast = Toast.makeText(this, R.string.exit, Toast.LENGTH_SHORT);
        spacexImage = findViewById(R.id.spacex_roadster);
        mainLayout = findViewById(R.id.scrollView);
        mProgressBar = findViewById(R.id.progress_bar);
        apodImage = findViewById(R.id.apod_image);
        coordinatorLayout = findViewById(R.id.coordinator);
        launchJob(this, NotificationService.class, 43200000);
        ApodRepository apodRepository = new ApodRepository(getApplication(), this);
        apodRepository.loadData();
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
        if (imageItem != null) {
            intent.putExtra(Constants.IMAGE, imageItem);
        }
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        if (pressed) {
            toast.cancel();
            super.onBackPressed();
        }
        else {
            toast.show();
            pressed = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        pressed = false;
    }

    @Override
    public void onSuccess(ImageItem imageItem) {
        this.imageItem =  imageItem;
        Glide.with(this)
                .load(imageItem.getImage())
                .apply(RequestOptions.centerCropTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource,
                                                @Nullable Transition<? super Drawable> transition) {
                        apodImage.setImageDrawable(resource);
                        mProgressBar.setVisibility(View.GONE);
                        mainLayout.setVisibility(View.VISIBLE);
                        Helper.customAnimation(MainActivity.this, mainLayout,
                                1000, android.R.anim.fade_in);
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Enjoy your Space Travel. :)",
                                        3000);
                        snackbar.show();
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        onFailure("No Internet Connection");
                    }
                });
    }

    @Override
    public void onFailure(String message) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recreate();
                    }
                });
        snackbar.setActionTextColor(getResources().getColor(R.color.primaryTextColor));
        snackbar.show();
    }
}
