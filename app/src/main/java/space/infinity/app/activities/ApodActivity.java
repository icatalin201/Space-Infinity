package space.infinity.app.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import space.infinity.app.R;
import space.infinity.app.models.apod.APOD;

public class ApodActivity extends AppCompatActivity {

    private TextView toolbar_title;
    private Toolbar toolbar;

    private ImageView apodImage;
    private TextView apodTitle;
    private TextView apodAuthor;
    private TextView apodDate;
    private TextView apodExplanation;
    private ProgressBar progressBar;
    private ScrollView scrollView;
    private Bitmap mBitmap;
    private APOD apod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.my_awesome_toolbar);
        apodImage = findViewById(R.id.apod_image);
        apodTitle = findViewById(R.id.apod_title);
        apodAuthor = findViewById(R.id.apod_author);
        apodDate = findViewById(R.id.apod_date);
        apodExplanation = findViewById(R.id.apod_explanation);
        progressBar = findViewById(R.id.progress_bar);
        scrollView = findViewById(R.id.main_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        apod = getIntent().getParcelableExtra("apod");
        if (setContent(apod)){
            progressBar.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }

    }

    public void goFullscreen(View view) {
        Intent intent = new Intent(this, FullscreenActivity.class);
        intent.putExtra("apodObject", apod);
        startActivity(intent);
    }

    private boolean setContent(APOD apod){
        toolbar_title.setText(R.string.apod);
        if (apod.getMedia_type().equals("image")){
            Glide.with(this).load(apod.getUrl())
                    .asBitmap().centerCrop().into(apodImage);
        }
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
        return true;
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
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void downloadImg(View view){
        Toast.makeText(this, R.string.download, Toast.LENGTH_SHORT).show();
        if (apod.getMedia_type().equals("image")){
            new DownloadImage().execute(apod.getHdurl());
        }

    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
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

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (httpURLConnection != null){
                    httpURLConnection.disconnect();
                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mBitmap = bitmap;
            if (Build.VERSION.SDK_INT < 23){
                saveImageToGallery(mBitmap);
            }
            else {
                if (ActivityCompat.checkSelfPermission(ApodActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ApodActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
                } else {
                    saveImageToGallery(mBitmap);
                }
            }
        }
    }

    private void saveImageToGallery(Bitmap bitmap){
        FileOutputStream outStream = null;;
        if (ActivityCompat.checkSelfPermission(ApodActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            File picturesDir = new File(Environment.getExternalStorageDirectory() + File.separator + "Pictures");
            File dir = new File(picturesDir.getAbsolutePath() + File.separator + "Space Infinity");
            boolean succes = false;
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
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(outFile));
            sendBroadcast(intent);
            Toast.makeText(this, R.string.download_complete, Toast.LENGTH_SHORT).show();
        }
    }
}
