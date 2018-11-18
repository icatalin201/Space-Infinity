package space.infinity.app.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import space.infinity.app.R;
import space.infinity.app.models.facts.SpaceFact;
import space.infinity.app.sql.SqlService;
import space.infinity.app.utils.Helper;
import space.infinity.app.utils.SwipeController;

public class FactsActivity extends AppCompatActivity {

    private TextView factView;
    private List<SpaceFact> spaceFactList;
    private LinearLayout buttons;
    private ImageButton fav;
    private int index;
    private int max;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        Toolbar toolbar = findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar_title.setText(R.string.facts);
        factView = findViewById(R.id.fact);
        fav = findViewById(R.id.fav);
        buttons = findViewById(R.id.buttons);
        spaceFactList = SqlService.getSpaceFactsList(this);
        max = spaceFactList.size() - 1;
        index = 0;
        Collections.shuffle(spaceFactList);
        factView.setText(spaceFactList.get(index).getName());
        Helper.customAnimation(this, factView, 700, android.R.anim.fade_in);
        setFavorites(spaceFactList.get(index));
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("filter", "no");
        editor.apply();

        ScrollView swipeSurface = findViewById(R.id.swipe_surface);
        swipeSurface.setOnTouchListener(new SwipeController(getApplicationContext()) {
            @Override
            public void onSwipeRight() {
                Helper.customAnimation(getApplicationContext(),
                        factView, 700, android.R.anim.fade_in);
                if (index > 0) {
                    index--;
                    factView.setText(spaceFactList.get(index).getName());
                }
                else if (index == 0) {
                    index = max;
                    factView.setText(spaceFactList.get(index).getName());
                    //Toast.makeText(this, "You are already at the
                    // beginning of facts!", Toast.LENGTH_LONG).show();
                }
                setFavorites(spaceFactList.get(index));
                super.onSwipeRight();
            }

            @Override
            public void onSwipeLeft() {
                Helper.customAnimation(getApplicationContext(),
                        factView, 700, android.R.anim.fade_in);
                if (index < max) {
                    index++;
                    factView.setText(spaceFactList.get(index).getName());
                }
                else if (index == max){
                    index = 0;
                    factView.setText(spaceFactList.get(index).getName());
                    //Toast.makeText(this, "You`ve reached
                    // the end of facts!", Toast.LENGTH_LONG).show();
                }
                setFavorites(spaceFactList.get(index));
                super.onSwipeLeft();
            }
        });
        buttons.setOnTouchListener(new SwipeController(getApplicationContext()) {
            @Override
            public void onSwipeRight() {
                Helper.customAnimation(getApplicationContext(),
                        factView, 700, android.R.anim.fade_in);
                if (index > 0) {
                    index--;
                    factView.setText(spaceFactList.get(index).getName());
                }
                else if (index == 0) {
                    index = max;
                    factView.setText(spaceFactList.get(index).getName());
                    //Toast.makeText(this, "You are already at the
                    // beginning of facts!", Toast.LENGTH_LONG).show();
                }
                setFavorites(spaceFactList.get(index));
                super.onSwipeRight();
            }

            @Override
            public void onSwipeLeft() {
                Helper.customAnimation(getApplicationContext(),
                        factView, 700, android.R.anim.fade_in);
                if (index < max) {
                    index++;
                    factView.setText(spaceFactList.get(index).getName());
                }
                else if (index == max){
                    index = 0;
                    factView.setText(spaceFactList.get(index).getName());
                    //Toast.makeText(this, "You`ve reached
                    // the end of facts!", Toast.LENGTH_LONG).show();
                }
                setFavorites(spaceFactList.get(index));
                super.onSwipeLeft();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info:
                Intent aboutIntent = new Intent(getApplicationContext(), About.class);
                startActivity(aboutIntent);
                return true;
            case R.id.share_fact:
                String packageName = getPackageName();
                Intent appShareIntent = new Intent(Intent.ACTION_SEND);
                appShareIntent.setType("text/plain");
                String extraText = spaceFactList.get(index).getName().concat("\n");
                extraText += "See more. Download the app!\n";
                extraText += "https://play.google.com/store/apps/details?id=" + packageName;
                appShareIntent.putExtra(Intent.EXTRA_TEXT, extraText);
                startActivity(appShareIntent);
                return true;
            case R.id.action_filter:
                String c = getPreferences(Context.MODE_PRIVATE).getString("filter", "");
                if (c.equals("no")) {
                    spaceFactList.clear();
                    spaceFactList = SqlService.getFavoriteFacts(FactsActivity.this);
                    if (spaceFactList.size() > 0) {
                        max = spaceFactList.size() - 1;
                        index = 0;
                        factView.setText(spaceFactList.get(index).getName());
                        Helper.customAnimation(this, factView, 700, android.R.anim.fade_in);
                        setFavorites(spaceFactList.get(index));
                        buttons.setVisibility(View.VISIBLE);
                    }
                    else {
                        factView.setText(R.string.no_fav);
                        buttons.setVisibility(View.GONE);
                    }
                    SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("filter", "yes");
                    editor.apply();
                    Toast.makeText(this, "Favorite facts", Toast.LENGTH_SHORT).show();
                }
                else if (c.equals("yes")) {
                    spaceFactList = SqlService.getSpaceFactsList(this);
                    max = spaceFactList.size() - 1;
                    index = 0;
                    Collections.shuffle(spaceFactList);
                    factView.setText(spaceFactList.get(index).getName());
                    Helper.customAnimation(this, factView, 700, android.R.anim.fade_in);
                    setFavorites(spaceFactList.get(index));
                    buttons.setVisibility(View.VISIBLE);
                    SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("filter", "no");
                    editor.apply();
                    Toast.makeText(this, "All facts", Toast.LENGTH_SHORT).show();
                }
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

    public void doPrev(View view) {
        Helper.customAnimation(this, factView, 700, android.R.anim.fade_in);
        if (index > 0) {
            index--;
            factView.setText(spaceFactList.get(index).getName());
        }
        else if (index == 0) {
            index = max;
            factView.setText(spaceFactList.get(index).getName());
            //Toast.makeText(this, "You are already at the beginning of facts!", Toast.LENGTH_LONG).show();
        }
        setFavorites(spaceFactList.get(index));
    }

    public void doNext(View view) {
        Helper.customAnimation(this, factView, 700, android.R.anim.fade_in);
        if (index < max) {
            index++;
            factView.setText(spaceFactList.get(index).getName());
        }
        else if (index == max){
            index = 0;
            factView.setText(spaceFactList.get(index).getName());
            //Toast.makeText(this, "You`ve reached the end of facts!", Toast.LENGTH_LONG).show();
        }
        setFavorites(spaceFactList.get(index));
    }

    private void setFavorites(final SpaceFact fact) {
        if (SqlService.isFactFav(FactsActivity.this, fact)) {
            fav.setImageResource(R.drawable.ic_favorite_black_18dp);
            fav.setTag("yes");
        }
        else {
            fav.setImageResource(R.drawable.ic_favorite_border_black_18dp);
            fav.setTag("no");
        }
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fav.getTag().equals("yes")) {
                    SqlService.handleFactToFavs(FactsActivity.this, fact, "remove");
                    Toast.makeText(FactsActivity.this, "Removed from favorites",
                            Toast.LENGTH_SHORT).show();
                    fav.setTag("no");
                    fav.setImageResource(R.drawable.ic_favorite_border_black_18dp);
                }
                else if (fav.getTag().equals("no")) {
                    SqlService.handleFactToFavs(FactsActivity.this, fact, "add");
                    Toast.makeText(FactsActivity.this, "Marked as favorite",
                            Toast.LENGTH_SHORT).show();
                    fav.setTag("yes");
                    fav.setImageResource(R.drawable.ic_favorite_black_18dp);
                }
            }
        });
    }
}
