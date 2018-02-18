package space.infinity.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import space.infinity.app.R;

public class PreEncyclopedia extends AppCompatActivity {

    private ImageView galaxies;
    private ImageView planets;
    private ImageView moons;
    private ImageView others;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_encyclopedia);
        Toolbar toolbar = findViewById(R.id.my_awesome_toolbar);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_title.setText(R.string.wiki);
        galaxies = findViewById(R.id.galaxies);
        planets = findViewById(R.id.planets);
        moons = findViewById(R.id.moons);
        others = findViewById(R.id.others);
        Glide.with(this).load(R.drawable.galaxies).asBitmap().centerCrop().into(galaxies);
        Glide.with(this).load(R.drawable.planets).asBitmap().centerCrop().into(planets);
        Glide.with(this).load(R.drawable.moons).asBitmap().centerCrop().into(moons);
        Glide.with(this).load(R.drawable.others).asBitmap().centerCrop().into(others);
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
}
