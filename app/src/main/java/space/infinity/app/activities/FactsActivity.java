package space.infinity.app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import space.infinity.app.R;
import space.infinity.app.models.facts.SpaceFact;
import space.infinity.app.sql.SqlService;
import space.infinity.app.utils.Helper;

public class FactsActivity extends AppCompatActivity {

    private TextView toolbar_title;
    private Toolbar toolbar;
    private TextView factView;
    private List<SpaceFact> spaceFactList;
    private ImageButton fav;
    private int index;
    private int max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_title.setText(R.string.facts);
        factView = findViewById(R.id.fact);
        fav = findViewById(R.id.fav);
        spaceFactList = SqlService.getSpaceFactsList(this);
        index = 0;
        max = spaceFactList.size() - 1;
        factView.setText(spaceFactList.get(index).getName());
        Helper.customAnimation(this, factView, 700, android.R.anim.fade_in);
        setFavorites(spaceFactList.get(index));
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
                    Toast.makeText(FactsActivity.this, "Removed from favorites", Toast.LENGTH_SHORT).show();
                    fav.setTag("no");
                    fav.setImageResource(R.drawable.ic_favorite_border_black_18dp);
                }
                else if (fav.getTag().equals("no")) {
                    SqlService.handleFactToFavs(FactsActivity.this, fact, "add");
                    Toast.makeText(FactsActivity.this, "Marked as favorite", Toast.LENGTH_SHORT).show();
                    fav.setTag("yes");
                    fav.setImageResource(R.drawable.ic_favorite_black_18dp);
                }
            }
        });
    }
}
