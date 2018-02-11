package space.infinity.app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import space.infinity.app.R;
import space.infinity.app.models.apod.APOD;
import space.infinity.app.utils.Helper;

public class FullscreenActivity extends AppCompatActivity {

    private ImageView full_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        final ProgressBar progressBar = findViewById(R.id.progress_bar);
        full_image = findViewById(R.id.full_image);
        APOD apodObject = getIntent().getParcelableExtra("apodObject");
        if (apodObject != null) {
            Glide.with(this).load(apodObject.getUrl())
                    .asBitmap().into(full_image);
        }
        else {
            String image_path = getIntent().getStringExtra("imagePath");
            Glide.with(this).load(image_path)
                    .asBitmap().into(full_image);
        }
        Helper.setAnimationForAll(this, full_image);
        progressBar.setVisibility(View.GONE);
    }
}
