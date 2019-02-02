package space.infinity.app.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import space.infinity.app.R;
import space.infinity.app.models.ImageItem;
import space.infinity.app.network.CheckingConnection;
import space.infinity.app.utils.Constants;
import space.infinity.app.utils.Helper;

public class ApodActivity extends AppCompatActivity {

    private ImageView image;
    private TextView credits;
    private TextView description;

    private String img;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod);
        TextView toolbartitle = findViewById(R.id.toolbar_title);
        Toolbar toolbar = findViewById(R.id.toolbar);
        image = findViewById(R.id.image);
        credits = findViewById(R.id.credits);
        description = findViewById(R.id.description);
        final ImageItem imageItem = getIntent().getParcelableExtra(Constants.IMAGE);
        img = imageItem.getImage();
        name = imageItem.getTitle();
        toolbartitle.setText(name);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setContent(imageItem);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ApodActivity.this, FullscreenActivity.class);
                intent.putExtra("path", imageItem.getImage());
                intent.putExtra("desc", imageItem.getDescription());
                intent.putExtra("name", imageItem.getTitle());
                startActivity(intent);
            }
        });
    }

    private void setContent(ImageItem imageItem) {
        description.setText(imageItem.getDescription());
        String copyright = "";
        if (imageItem.getPhotographer() != null) {
            copyright = getResources().getString(R.string.author).concat(" ")
                    .concat(imageItem.getPhotographer());
        }
        credits.setText(copyright);
        Glide.with(this)
                .load(imageItem.getImage())
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.centerCropTransform())
                .into(image);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
        super.onBackPressed();
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
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void downloadImg(){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if (CheckingConnection.isConnected(this)) {
                Toast.makeText(this, R.string.download, Toast.LENGTH_SHORT).show();
                new DownloadImage().execute(img);
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
                    new DownloadImage().execute(img);
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
                    bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
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
            return Helper.saveImageToGallery(bitmap, name);
        }

        @Override
        protected void onPostExecute(File file) {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            sendBroadcast(intent);
            Toast.makeText(ApodActivity.this, R.string.download_complete, Toast.LENGTH_SHORT).show();
        }
    }
}
