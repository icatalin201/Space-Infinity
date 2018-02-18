package space.infinity.app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import space.infinity.app.R;
import space.infinity.app.adapters.GalaxyAdapter;
import space.infinity.app.adapters.MoonAdapter;
import space.infinity.app.adapters.PlanetAdapter;
import space.infinity.app.models.encyclopedia.Galaxy;
import space.infinity.app.models.encyclopedia.Moon;
import space.infinity.app.models.encyclopedia.Planet;
import space.infinity.app.sql.SqlService;

public class ChooseEncyclopedia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_encyclopedia);
        Toolbar toolbar = findViewById(R.id.my_awesome_toolbar);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_title.setText(R.string.wiki);
        RecyclerView recyclerView = findViewById(R.id.recycler);

        String type = getIntent().getStringExtra("type");
        switch (type) {

            case "planets":
                List<Planet> planetList = SqlService.getPlanets(this);
                PlanetAdapter planetAdapter = new PlanetAdapter(this, planetList);
                recyclerView.setAdapter(planetAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                planetAdapter.notifyDataSetChanged();
                break;

            case "moons":
                List<Moon> moonList = SqlService.getMoons(this);
                MoonAdapter moonAdapter = new MoonAdapter(this, moonList);
                recyclerView.setAdapter(moonAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                moonAdapter.notifyDataSetChanged();
                break;

            case "galaxies":
                List<Galaxy> galaxyList = SqlService.getGalaxies(this);
                GalaxyAdapter galaxyAdapter = new GalaxyAdapter(this, galaxyList);
                recyclerView.setAdapter(galaxyAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                galaxyAdapter.notifyDataSetChanged();
                break;

            case "others":

                break;

        }

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
