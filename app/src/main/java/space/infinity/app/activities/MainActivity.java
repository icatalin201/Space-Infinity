package space.infinity.app.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.infinity.app.R;
import space.infinity.app.models.apod.APOD;
import space.infinity.app.network.Client;
import space.infinity.app.network.Service;
import space.infinity.app.sql.SqlService;
import space.infinity.app.utils.Constants;
import space.infinity.app.utils.Helper;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private ScrollView mainLayout;
    private Boolean pressed;
    private Call<APOD> apodCall;
    private ImageView apodImage;
    private ImageView roverImage;
    private ImageView galleryImage;
    private ImageView issImage;
    private ImageView factsImage;
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

        if (Build.VERSION.SDK_INT < 23){
            generateDb();
        }
        else {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            }
            else {
                generateDb();
            }
        }

        mainLayout = findViewById(R.id.main_layout);
        mProgressBar = findViewById(R.id.progress_bar);
        apodImage = findViewById(R.id.apod_image);
        roverImage = findViewById(R.id.rover_image);
        galleryImage = findViewById(R.id.gallery_image);
        issImage = findViewById(R.id.iss_image);
        factsImage = findViewById(R.id.facts_image);
        wikiImage = findViewById(R.id.wiki_image);
        flaunches = findViewById(R.id.future_launches);
        images = new HashMap<>();
        images.put(roverImage, R.drawable.rover);
        images.put(galleryImage, R.drawable.gallery);
        images.put(issImage, R.drawable.iss);
        images.put(factsImage, R.drawable.facts);
        images.put(wikiImage, R.drawable.wiki);
        images.put(flaunches, R.drawable.ulaunches);
        loadData();
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
        String firstTime = getPreferences(Context.MODE_PRIVATE).getString("first_time", "");
        if (check && !firstTime.equals("no")) {
            //exportDB();
            importDB();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            generateDb();
        }
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
                editor.putString("first_time", "no");
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
                    //apodCall = call.clone();
                    //apodCall.enqueue(this);
                    getImageFromDb();
                    Log.i("main", "getting apod from db");
                    return;
                }
                if (response.body() == null) return;
                if (response.body().getMedia_type().equals("image")) {
                    Glide.with(MainActivity.this).load(response.body().getUrl())
                            .asBitmap()
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(apodImage);
                    apod = response.body();
                    SqlService.insertImageDataIntoSql(MainActivity.this, apod);
                    settingLayout();
                }
            }

            @Override
            public void onFailure(Call<APOD> call, Throwable t) {

            }
        });
    }

    private void getImageFromDb() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        apod = SqlService.getApod(MainActivity.this, formattedDate);
        Glide.with(MainActivity.this).load(apod.getUrl())
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(apodImage);
        settingLayout();
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

    public void showFacts(View view){
        intent = new Intent(this, FactsActivity.class);
        startActivity(intent);
    }

    public void showWiki(View view){

    }

    public void futureLaunches(View view){
        intent = new Intent(this, UpcomingLaunches.class);
        startActivity(intent);
    }
}
