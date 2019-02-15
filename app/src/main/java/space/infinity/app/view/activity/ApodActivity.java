package space.infinity.app.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import space.infinity.app.R;
import space.infinity.app.model.entity.ImageItem;
import space.infinity.app.model.network.CheckingConnection;
import space.infinity.app.util.Constants;
import space.infinity.app.util.DownloadImage;

public class ApodActivity extends AppCompatActivity {

    private ImageView image;
    private TextView credits;
    private TextView description;

    private String img;
    private String name;

    private ImageItem imageItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apod);
        TextView toolbartitle = findViewById(R.id.toolbar_title);
        Toolbar toolbar = findViewById(R.id.toolbar);
        image = findViewById(R.id.image);
        credits = findViewById(R.id.credits);
        description = findViewById(R.id.description);
        if (savedInstanceState == null) {
            imageItem = getIntent().getParcelableExtra(Constants.IMAGE);
        } else {
            imageItem = savedInstanceState.getParcelable(Constants.IMAGE);
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.IMAGE, imageItem);
        super.onSaveInstanceState(outState);
    }

    private void setContent(ImageItem imageItem) {
        description.setText(imageItem.getDescription());
        String copyright = getResources().getString(R.string.author);
        if (imageItem.getPhotographer() != null) {
            copyright = copyright.concat(" ")
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
                new DownloadImage(getApplication(), img, name).execute();
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
                    new DownloadImage(getApplication(), img, name).execute();
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
