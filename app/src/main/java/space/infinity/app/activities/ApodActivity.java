package space.infinity.app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import space.infinity.app.R;
import space.infinity.app.models.APOD;

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
        if (setContent((APOD)getIntent().getParcelableExtra("apod"))){
            progressBar.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private boolean setContent(APOD apod){
        toolbar_title.setText(R.string.apod);
        Glide.with(this).load(apod.getHdurl())
                .asBitmap().centerCrop().into(apodImage);
        apodTitle.setText(apod.getTitle());
        apodExplanation.setText(apod.getExplanation());
        String date = getResources().getString(R.string.date).concat(" ").concat(apod.getDate());
        apodDate.setText(date);
        String copyright = getResources().getString(R.string.author).concat(" ").concat(apod.getCopyright());
        apodAuthor.setText(copyright);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
