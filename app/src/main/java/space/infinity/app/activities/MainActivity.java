package space.infinity.app.activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.infinity.app.R;
import space.infinity.app.models.apod.APOD;
import space.infinity.app.network.CheckingConnection;
import space.infinity.app.network.Client;
import space.infinity.app.network.Service;
import space.infinity.app.sql.SqlService;
import space.infinity.app.utils.Constants;
import space.infinity.app.utils.Helper;
import space.infinity.app.utils.NotificationService;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private ScrollView mainLayout;
    private Boolean pressed;
    private ImageView apodImage;
    private APOD apod;
    private Intent intent;
    private CoordinatorLayout coordinatorLayout;

    private HashMap<ImageView, Integer> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(null);
        pressed = false;
        coordinatorLayout = findViewById(R.id.coordinator);
        mainLayout = findViewById(R.id.main_layout);
        mProgressBar = findViewById(R.id.progress_bar);
        apodImage = findViewById(R.id.apod_image);
        ImageView roverImage = findViewById(R.id.rover_image);
        ImageView galleryImage = findViewById(R.id.gallery_image);
        ImageView issImage = findViewById(R.id.iss_image);
        ImageView factsImage = findViewById(R.id.facts_image);
        ImageView wikiImage = findViewById(R.id.wiki_image);
        ImageView flaunches = findViewById(R.id.future_launches);
        images = new HashMap<>();
        images.put(roverImage, R.drawable.rover);
        images.put(galleryImage, R.drawable.gallery);
        images.put(issImage, R.drawable.iss);
        images.put(factsImage, R.drawable.facts);
        images.put(wikiImage, R.drawable.wiki);
        images.put(flaunches, R.drawable.ulaunches);
        if (Build.VERSION.SDK_INT < 23){
            starting();
        }
        else {
            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            }
            else {
                starting();
            }
        }
    }

    private void starting() {
        generateDb();
        if (CheckingConnection.isConnected(this)) {
            loadData();
            Calendar calendar = Calendar.getInstance();
            String day = getPreferences(Context.MODE_PRIVATE).getString("notification-today", "");
            if (day.equals("") || !Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)).equals(day)) {
                setNotifications();
            }
        }
        else {
            getImageFromDb();
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No Internet Connection", 8000)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                            startActivity(getIntent());
                        }
                    });
            snackbar.setActionTextColor(getResources().getColor(R.color.primaryTextColor));
            snackbar.show();
        }
    }

    private void generateDb() {
        File directory = new File(Environment.getExternalStorageDirectory() + "/BackupFolder");
        boolean check = false;
        if(!directory.exists()) {
            if(directory.mkdir()) {
                Log.i("created", "created");
                check = true;
            }
        }
        else {
            Log.i("directory", "exists");
            check = true;
        }
        String firstTime = getPreferences(Context.MODE_PRIVATE).getString("updated", "");
        if (check && !firstTime.equals("yes")) {
            //exportDB();
            importDB();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull
            String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            starting();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void importDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            if (sd.canWrite()) {

                String dbpath = this.getDatabasePath("space_infinity.db").toString();
                String dbname  = "space_infinity.db";

                OutputStream myOutput = new FileOutputStream(dbpath);
                byte[] buffer = new byte[1024];
                int length;
                InputStream myInput = getAssets().open(dbname);
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myInput.close();
                myOutput.flush();
                myOutput.close();
                SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("updated", "yes");
                editor.apply();
                Log.i("db", "copied");
            }
        } catch (Exception e) {
            Log.i("exception", e.toString());
        }
    }

    private void exportDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            if (sd.canWrite()) {
                String dbpath = this.getDatabasePath("space_infinity.db").toString();
                String backupDBPath  = "/BackupFolder/space_infinity.db";
                File currentDB = new File(dbpath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Log.i("db", "exported");
            }
        } catch (Exception e) {
            Log.i("exception", e.toString());
        }
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
        Call<APOD> apodCall = service.getAstronomyPictureOfTheDay(Constants.API_KEY);
        apodCall.enqueue(new Callback<APOD>() {
            @Override
            public void onResponse(Call<APOD> call, Response<APOD> response) {
                if (!response.isSuccessful()){
                    //apodCall = call.clone();
                    //apodCall.enqueue(this);
                    getImageFromDb();
                    Log.i("main", "getting apod from db");
                    return;
                }
                if (response.body() != null) {
                    apod = response.body();
                    if (apod.getMedia_type().equals("image")) {
                        Glide.with(MainActivity.this)
                                .load(apod.getUrl())
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(new SimpleTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource,
                                                                Transition<? super Drawable> glideAnimation) {
                                        apodImage.setImageDrawable(resource);
                                        settingLayout();
                                    }
                                });
                        SqlService.insertImageDataIntoSql(MainActivity.this, apod);
                    }
                    else {
                        getImageFromDb();
                    }
                }
                else {
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
        //Date c = Calendar.getInstance().getTime();
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //String formattedDate = df.format(c);
        apod = SqlService.getApod(MainActivity.this);
        Glide.with(MainActivity.this)
                .load(apod.getUrl())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource,
                                                Transition<? super Drawable> glideAnimation) {
                        apodImage.setImageDrawable(resource);
                        settingLayout();
                    }
                });
    }

    private void settingLayout(){
        for (Map.Entry<ImageView, Integer> entry : images.entrySet()){
            Glide.with(MainActivity.this).load(entry.getValue())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(entry.getKey());
        }
        mProgressBar.setVisibility(View.GONE);
        mainLayout.setVisibility(View.VISIBLE);
        Helper.customAnimation(this, mainLayout, 1000, android.R.anim.fade_in);
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

    public void showFacts(View view){
        intent = new Intent(this, FactsActivity.class);
        startActivity(intent);
    }

    public void showWiki(View view){
        intent = new Intent(this, PreEncyclopedia.class);
        startActivity(intent);
    }

    public void futureLaunches(View view){
        intent = new Intent(this, UpcomingLaunches.class);
        startActivity(intent);
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
