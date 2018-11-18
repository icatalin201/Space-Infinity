package space.infinity.app.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

import space.infinity.app.R;
import space.infinity.app.models.apod.APOD;
import space.infinity.app.network.CheckingConnection;

public class ApodActivity extends AppCompatActivity {

    private TextView toolbar_title;

    private ImageView apodImage;
    private TextView apodTitle;
    private TextView apodAuthor;
    private TextView apodDate;
    private TextView apodExplanation;
    private APOD apod;

    private ProgressBar progressBar;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod);
        toolbar_title = findViewById(R.id.toolbar_title);
        Toolbar toolbar = findViewById(R.id.my_awesome_toolbar);
        apodImage = findViewById(R.id.apod_image);
        apodTitle = findViewById(R.id.apod_title);
        apodAuthor = findViewById(R.id.apod_author);
        apodDate = findViewById(R.id.apod_date);
        apodExplanation = findViewById(R.id.apod_explanation);
        progressBar = findViewById(R.id.progress_bar);
        scrollView = findViewById(R.id.main_layout);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        apod = getIntent().getParcelableExtra("apod");
        setContent(apod);
    }

    public void goFullscreen(View view) {
        Intent intent = new Intent(this, FullscreenActivity.class);
        intent.putExtra("imageObject", apod);
        startActivity(intent);
    }

    private void setContent(APOD apod){
        toolbar_title.setText(R.string.apod);
        apodTitle.setText(apod.getTitle());
        apodExplanation.setText(apod.getExplanation());
        if (apod.getDate() != null) {
            String date = getResources().getString(R.string.date).concat(" ").concat(apod.getDate());
            apodDate.setText(date);
        }
        if (apod.getCopyright() != null) {
            String copyright = getResources().getString(R.string.author).concat(" ").concat(apod.getCopyright());
            apodAuthor.setText(copyright);
        }
        Glide.with(this)
                .load(apod.getUrl())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource,
                                        Transition<? super Drawable> glideAnimation) {
                apodImage.setImageDrawable(resource);
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
        });
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

    public void downloadImg(View view){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if (CheckingConnection.isConnected(this)) {
                Toast.makeText(this, R.string.download, Toast.LENGTH_SHORT).show();
                if (apod.getMedia_type().equals("image")) {
                    new DownloadImage().execute(apod.getHdurl());
                }
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
                    if (apod.getMedia_type().equals("image")) {
                        new DownloadImage().execute(apod.getHdurl());
                    }
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

    @SuppressLint("StaticFieldLeak")
    private class DownloadImage extends AsyncTask<String, Void, File>{

        @Override
        protected File doInBackground(String... urls) {
            Bitmap bitmap = null;

            HttpURLConnection httpURLConnection = null;

            try {
                URL url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                int status_code = httpURLConnection.getResponseCode();
                if (status_code != 200){
                    bitmap = ((BitmapDrawable)apodImage.getDrawable()).getBitmap();
                }
                InputStream inputStream = httpURLConnection.getInputStream();
                if (inputStream != null){
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (httpURLConnection != null){
                    httpURLConnection.disconnect();
                }
            }
            return saveImageToGallery(bitmap);
        }

        @Override
        protected void onPostExecute(File file) {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            sendBroadcast(intent);
            Toast.makeText(ApodActivity.this, R.string.download_complete, Toast.LENGTH_SHORT).show();
        }
    }

    private File saveImageToGallery(Bitmap bitmap){
        FileOutputStream outStream;
        File picturesDir = new File(Environment.getExternalStorageDirectory() + File.separator + "Pictures");
        File dir = new File(picturesDir.getAbsolutePath() + File.separator + "Space Infinity");
        boolean succes;
        if (!dir.exists()) {
            succes = dir.mkdir();
            if (succes) {
                Log.i("Directory", "Created");
            }
        }
        Random r = new Random();
        String name = apodTitle.getText().toString()
                .replace(" ", "")
                .concat(Integer.toString(r.nextInt()));

        String fileName = String.format("%s.jpg", name);
        File outFile = new File(dir, fileName);
        try {
            outStream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (IOException e ) {
            e.printStackTrace();
        }
        return outFile;
    }
}
