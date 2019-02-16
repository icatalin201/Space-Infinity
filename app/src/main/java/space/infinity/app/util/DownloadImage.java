package space.infinity.app.util;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import space.infinity.app.R;

public class DownloadImage extends AsyncTask<Void, Void, File> {

    private Application application;
    private String urlString;
    private String name;

    public DownloadImage(Application application, String url, String name) {
        this.application = application;
        this.urlString = url;
        this.name = name;
    }

    @Override
    protected File doInBackground(Void... voids) {
        Bitmap bitmap = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(urlString);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            int status_code = httpURLConnection.getResponseCode();
            if (status_code == 200) {
                InputStream inputStream = httpURLConnection.getInputStream();
                if (inputStream != null) {
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }
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
        super.onPostExecute(file);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        application.sendBroadcast(intent);
        Toast.makeText(application, R.string.download_complete, Toast.LENGTH_SHORT).show();
    }
}
