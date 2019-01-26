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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import space.infinity.app.R;
import space.infinity.app.models.APOD;
import space.infinity.app.models.ImageItem;
import space.infinity.app.network.CheckingConnection;
import space.infinity.app.utils.Helper;

public class FullscreenActivity extends AppCompatActivity {

    private ImageView full_image;
    private TextView full_image_title;
    private String hdpath;
    private String path;
    private String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        full_image = findViewById(R.id.full_image);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        full_image_title = findViewById(R.id.toolbar_title);
        TextView description = findViewById(R.id.description);
        Object object = getIntent().getParcelableExtra("imageObject");
        String title = "";
        if (object instanceof APOD) {
            APOD apod = (APOD) object;
            title = apod.getTitle();
            path = apod.getUrl();
            hdpath = apod.getHdurl();
            desc = apod.getExplanation();
        }
        else if (object instanceof ImageItem) {
            final ImageItem imageInfo = (ImageItem) object;
            title = imageInfo.getTitle();
            path = imageInfo.getImage();
            hdpath = imageInfo.getImage();
            desc = imageInfo.getDescription();
        }
        Glide.with(this)
                .load(path)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(full_image);
        full_image_title.setText(title);
        description.setText(desc);
        Helper.setAnimationForAll(this, full_image);
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
                new DownloadImage().execute(hdpath);
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
                    new DownloadImage().execute(hdpath);
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
                    bitmap = ((BitmapDrawable)full_image.getDrawable()).getBitmap();
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
            return Helper.saveImageToGallery(bitmap, full_image_title.getText().toString());
        }

        @Override
        protected void onPostExecute(File file) {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            sendBroadcast(intent);
            Toast.makeText(FullscreenActivity.this, R.string.download_complete,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
