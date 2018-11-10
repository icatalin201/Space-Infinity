package space.infinity.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.Objects;

import space.infinity.app.R;
import space.infinity.app.utils.Helper;

public class PreEncyclopedia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_encyclopedia);
        Toolbar toolbar = findViewById(R.id.my_awesome_toolbar);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar_title.setText(R.string.wiki);
        ImageView galaxies = findViewById(R.id.galaxies);
        ImageView planets = findViewById(R.id.planets);
        ImageView moons = findViewById(R.id.moons);
        ImageView others = findViewById(R.id.others);
        CardView card_galaxy = findViewById(R.id.card_galaxy);
        CardView card_planet = findViewById(R.id.card_planets);
        CardView card_moon = findViewById(R.id.card_moons);
        CardView card_other = findViewById(R.id.card_other);
        Glide.with(this)
                .load(R.drawable.galaxies)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(galaxies);
        Glide.with(this)
                .load(R.drawable.planets)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(planets);
        Glide.with(this)
                .load(R.drawable.moons)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(moons);
        Glide.with(this)
                .load(R.drawable.others)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(others);
        Helper.customAnimation(this, card_galaxy, 700, android.R.anim.fade_in);
        Helper.customAnimation(this, card_planet, 700, android.R.anim.fade_in);
        Helper.customAnimation(this, card_moon, 700, android.R.anim.fade_in);
        Helper.customAnimation(this, card_other, 700, android.R.anim.fade_in);
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

    public void planetsEn(View view) {
        Intent intent = new Intent(this, ChooseEncyclopedia.class);
        intent.putExtra("type", "planets");
        startActivity(intent);
    }

    public void moonsEn(View view) {
        Intent intent = new Intent(this, ChooseEncyclopedia.class);
        intent.putExtra("type", "moons");
        startActivity(intent);
    }

    public void galaxiesEn(View view) {
        Intent intent = new Intent(this, ChooseEncyclopedia.class);
        intent.putExtra("type", "galaxies");
        startActivity(intent);
    }

    public void othersEn(View view) {
        Intent intent = new Intent(this, ChooseEncyclopedia.class);
        intent.putExtra("type", "others");
        startActivity(intent);
    }
}
