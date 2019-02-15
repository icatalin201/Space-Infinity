package space.infinity.app.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.jsibbold.zoomage.ZoomageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import space.infinity.app.R;
import space.infinity.app.model.network.CheckingConnection;
import space.infinity.app.util.DownloadImage;
import space.infinity.app.util.Helper;

public class FullscreenActivity extends AppCompatActivity {

    private ZoomageView image;
    private String path;
    private String name;
    private ScrollView scrollView;
    private ImageButton hide;
    private ImageButton show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        TextView description = findViewById(R.id.description);
        image = findViewById(R.id.full_image);
        hide = findViewById(R.id.hide);
        show = findViewById(R.id.show);
        scrollView = findViewById(R.id.scroll);
        path = getIntent().getStringExtra("path");
        String desc = getIntent().getStringExtra("desc");
        name = getIntent().getStringExtra("name");
        Glide.with(this)
                .load(path)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(image);
        description.setText(Html.fromHtml(desc));
        toolbarTitle.setText(name);
    }

    public void showDesc(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
        animation.setDuration(300);
        scrollView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                scrollView.setVisibility(View.VISIBLE);
                show.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                hide.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void hideDesc(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bottom_down);
        animation.setDuration(300);
        scrollView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                hide.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                scrollView.setVisibility(View.GONE);
                show.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.download, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.download:
                downloadImg();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void downloadImg() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if (CheckingConnection.isConnected(this)) {
                Toast.makeText(this, R.string.download, Toast.LENGTH_SHORT).show();
                new DownloadImage(getApplication(), path, name).execute();
            } else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (CheckingConnection.isConnected(this)) {
                    Toast.makeText(this, R.string.download, Toast.LENGTH_SHORT).show();
                    new DownloadImage(getApplication(), path, name).execute();
                } else {
                    Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Can`t download photo due to " +
                        "insufficient permissions! :(", Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
