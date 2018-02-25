package space.infinity.app.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import space.infinity.app.models.gallery.ImageInfo;
import space.infinity.app.models.mars.RoverImages;
import space.infinity.app.utils.Helper;

public class FullscreenActivity extends AppCompatActivity {

    private ImageView full_image;
    private TextView full_image_title;
    private Object object;
    private String hdpath;
    private String path;
    private String desc;
    private TextView description;
    //private ImageButton fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        full_image = findViewById(R.id.full_image);
        full_image_title = findViewById(R.id.full_image_title);
        description = findViewById(R.id.description);
        //fav = findViewById(R.id.fav);
        object = getIntent().getParcelableExtra("imageObject");
        String title = "";
        if (object instanceof APOD) {
            final APOD apod = (APOD) object;
            title = apod.getTitle();
            path = apod.getUrl();
            hdpath = apod.getHdurl();
            desc = apod.getExplanation();

            /*if (SqlService.isImageFav(FullscreenActivity.this, apod.getTitle())) {
                fav.setImageResource(R.drawable.fav_big);
                fav.setTag("yes");
            }
            else {
                fav.setImageResource(R.drawable.notfav_big);
                fav.setTag("no");
            }
            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (fav.getTag().equals("yes")) {
                        SqlService.handleImageFavs(FullscreenActivity.this, "remove",
                                apod.getTitle(), apod.getUrl(), apod.getHdurl(), apod.getExplanation());
                        Toast.makeText(FullscreenActivity.this, "Removed from favorites",
                                Toast.LENGTH_SHORT).show();
                        fav.setTag("no");
                        fav.setImageResource(R.drawable.notfav_big);
                    }
                    else if (fav.getTag().equals("no")) {
                        SqlService.handleImageFavs(FullscreenActivity.this, "add",
                                apod.getTitle(), apod.getUrl(), apod.getHdurl(), apod.getExplanation());
                        Toast.makeText(FullscreenActivity.this, "Marked as favorite",
                                Toast.LENGTH_SHORT).show();
                        fav.setTag("yes");
                        fav.setImageResource(R.drawable.fav_big);
                    }
                }
            });*/

        }
        else if (object instanceof RoverImages){
            RoverImages roverImages = (RoverImages) object;
            title = "Photo taken on ".concat(roverImages.getEarth_date());
            path = roverImages.getImg_src();
            hdpath = roverImages.getImg_src();
            //fav.setVisibility(View.GONE);
        }
        else if (object instanceof ImageInfo) {
            final ImageInfo imageInfo = (ImageInfo) object;
            title = imageInfo.getTitle();
            path = imageInfo.getImage();
            hdpath = imageInfo.getImage();
            desc = imageInfo.getDescription();

            /*if (SqlService.isImageFav(FullscreenActivity.this, imageInfo.getTitle())) {
                fav.setImageResource(R.drawable.fav_big);
                fav.setTag("yes");
            }
            else {
                fav.setImageResource(R.drawable.notfav_big);
                fav.setTag("no");
            }
            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (fav.getTag().equals("yes")) {
                        SqlService.handleImageFavs(FullscreenActivity.this, "remove",
                                imageInfo.getTitle(), imageInfo.getImage(), imageInfo.getImage(),
                                imageInfo.getDescription());
                        Toast.makeText(FullscreenActivity.this, "Removed from favorites",
                                Toast.LENGTH_SHORT).show();
                        fav.setTag("no");
                        fav.setImageResource(R.drawable.notfav_big);
                    }
                    else if (fav.getTag().equals("no")) {
                        SqlService.handleImageFavs(FullscreenActivity.this, "add",
                                imageInfo.getTitle(), imageInfo.getImage(), imageInfo.getImage(),
                                imageInfo.getDescription());
                        Toast.makeText(FullscreenActivity.this, "Marked as favorite",
                                Toast.LENGTH_SHORT).show();
                        fav.setTag("yes");
                        fav.setImageResource(R.drawable.fav_big);
                    }
                }
            });*/
        }
        Glide.with(this).load(path)
                .asBitmap().into(full_image);
        full_image_title.setText(title);
        description.setText(desc);
        Helper.setAnimationForAll(this, full_image);
    }

    public void downloadImg(View view) {
        Toast.makeText(this, R.string.download, Toast.LENGTH_SHORT).show();
        new DownloadImage().execute(hdpath);
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
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
            saveImageToGallery(bitmap);
        }
    }

    private void saveImageToGallery(Bitmap bitmap){
        FileOutputStream outStream = null;;
        if (ActivityCompat.checkSelfPermission(FullscreenActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            File picturesDir = new File(Environment.getExternalStorageDirectory() +
                    File.separator + "Pictures");
            File dir = new File(picturesDir.getAbsolutePath() + File.separator +
                    "Space Infinity");
            boolean success = false;
            if (!dir.exists()) {
                success = dir.mkdir();
                if (success) {
                    Log.i("Directory", "Created");
                }
            }
            Random r = new Random();
            String name = full_image_title.getText().toString()
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
