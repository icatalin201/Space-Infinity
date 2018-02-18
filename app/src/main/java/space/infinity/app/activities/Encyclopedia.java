package space.infinity.app.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import space.infinity.app.R;
import space.infinity.app.models.encyclopedia.Galaxy;
import space.infinity.app.models.encyclopedia.Moon;
import space.infinity.app.models.encyclopedia.Other;
import space.infinity.app.models.encyclopedia.Planet;

public class Encyclopedia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encyclopedia);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setBackground(getDrawable(android.R.color.transparent));
        //AppBarLayout mAppBarLayout = findViewById(R.id.app_bar_layout);
        CollapsingToolbarLayout mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        ImageView imageView = findViewById(R.id.image);
        TextView descriptionView = findViewById(R.id.description);
        TextView designationView = findViewById(R.id.designation);
        TextView diameterView = findViewById(R.id.diameter);
        TextView distanceView = findViewById(R.id.distance);
        TextView massView = findViewById(R.id.mass);
        TextView ageView = findViewById(R.id.age);
        TextView constellationView = findViewById(R.id.constellation);
        TextView galaxyGroupView = findViewById(R.id.galaxy_group);
        TextView numberOfStarsView = findViewById(R.id.number_of_stars);
        TextView typeView = findViewById(R.id.type);
        TextView moonsView = findViewById(R.id.moons);
        TextView orbitsView = findViewById(R.id.orbits);
        TextView orbitDistanceView = findViewById(R.id.orbit_distance);
        TextView orbitPeriodView = findViewById(R.id.orbit_period);
        TextView surfaceTemperatureView = findViewById(R.id.surface_temperature);
        TextView firstRecordView = findViewById(R.id.first_record);
        TextView recordedByView = findViewById(R.id.recorded_by);
        TextView factsView = findViewById(R.id.facts);
        TextView otherFactsView = findViewById(R.id.other_facts);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String name = "";
        String image = "";
        Object object = getIntent().getParcelableExtra("object");
        if (object instanceof Planet) {
            Planet planet = (Planet) object;
            name = planet.getName();
            image = planet.getImage().split("\\.")[0];
            descriptionView.setText(getResources().getString(R.string.description).
                    concat("\n\n").concat(planet.getDescription()));
            diameterView.setText(getResources().getString(R.string.diameter).concat(" ")
                    .concat(planet.getDiameter()));
            moonsView.setText(getResources().getString(R.string.moon).concat(" ")
                    .concat(planet.getMoons()));
            massView.setText(getResources().getString(R.string.mass).concat(" ")
                    .concat(planet.getMass()));
            orbitPeriodView.setText(getResources().getString(R.string.orbitp).concat(" ")
                    .concat(planet.getOrbitPeriod()));
            orbitDistanceView.setText(getResources().getString(R.string.orbitd).concat(" ")
                    .concat(planet.getOrbitDistance()));
            surfaceTemperatureView.setText(getResources().getString(R.string.surfacet).concat(" ")
                    .concat(planet.getSurfaceTemperature()));
            firstRecordView.setText(getResources().getString(R.string.firstrec).concat(" ")
                    .concat(planet.getFirstRecord()));
            recordedByView.setText(getResources().getString(R.string.recby).concat(" ")
                    .concat(planet.getRecordedBy()));
            factsView.setText(getResources().getString(R.string.quickfacts).concat("\n\n")
                    .concat(planet.getFacts()));
            descriptionView.setVisibility(View.VISIBLE);
            diameterView.setVisibility(View.VISIBLE);
            massView.setVisibility(View.VISIBLE);
            orbitDistanceView.setVisibility(View.VISIBLE);
            orbitPeriodView.setVisibility(View.VISIBLE);
            surfaceTemperatureView.setVisibility(View.VISIBLE);
            moonsView.setVisibility(View.VISIBLE);
            firstRecordView.setVisibility(View.VISIBLE);
            recordedByView.setVisibility(View.VISIBLE);
            factsView.setVisibility(View.VISIBLE);
        }
        else if (object instanceof Moon) {
            Moon moon = (Moon) object;
            name = moon.getName();
            image = moon.getImage().split("\\.")[0];
            descriptionView.setText(getResources().getString(R.string.description).
                    concat("\n\n").concat(moon.getDescription()));
            diameterView.setText(getResources().getString(R.string.diameter).concat(" ")
                    .concat(moon.getDiameter()));
            orbitsView.setText(getResources().getString(R.string.orbits).concat(" ")
                    .concat(moon.getOrbits()));
            massView.setText(getResources().getString(R.string.mass).concat(" ")
                    .concat(moon.getMass()));
            orbitPeriodView.setText(getResources().getString(R.string.orbitp).concat(" ")
                    .concat(moon.getOrbitPeriod()));
            orbitDistanceView.setText(getResources().getString(R.string.orbitd).concat(" ")
                    .concat(moon.getOrbitDistance()));
            surfaceTemperatureView.setText(getResources().getString(R.string.surfacet).concat(" ")
                    .concat(moon.getSurfaceTemperature()));
            firstRecordView.setText(getResources().getString(R.string.firstrec).concat(" ")
                    .concat(moon.getFirstRecord()));
            recordedByView.setText(getResources().getString(R.string.recby).concat(" ")
                    .concat(moon.getRecordedBy()));
            factsView.setText(getResources().getString(R.string.quickfacts).concat("\n\n")
                    .concat(moon.getFacts()));
            descriptionView.setVisibility(View.VISIBLE);
            diameterView.setVisibility(View.VISIBLE);
            massView.setVisibility(View.VISIBLE);
            orbitDistanceView.setVisibility(View.VISIBLE);
            orbitPeriodView.setVisibility(View.VISIBLE);
            surfaceTemperatureView.setVisibility(View.VISIBLE);
            orbitsView.setVisibility(View.VISIBLE);
            firstRecordView.setVisibility(View.VISIBLE);
            recordedByView.setVisibility(View.VISIBLE);
            factsView.setVisibility(View.VISIBLE);
        }
        else if (object instanceof Galaxy) {
            Galaxy galaxy = (Galaxy) object;
            name = galaxy.getName();
            image = galaxy.getImage().split("\\.")[0];
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            descriptionView.setText(getResources().getString(R.string.description).
                    concat("\n\n").concat(galaxy.getDescription()));
            diameterView.setText(getResources().getString(R.string.diameter).concat(" ")
                    .concat(galaxy.getDiameter()));
            designationView.setText(getResources().getString(R.string.designation).concat(" ")
                    .concat(galaxy.getDesignation()));
            massView.setText(getResources().getString(R.string.mass).concat(" ")
                    .concat(galaxy.getMass()));
            distanceView.setText(getResources().getString(R.string.distance).concat(" ")
                    .concat(galaxy.getDistance()));
            constellationView.setText(getResources().getString(R.string.constellation).concat(" ")
                    .concat(galaxy.getConstellation()));
            galaxyGroupView.setText(getResources().getString(R.string.galaxy_group).concat(" ")
                    .concat(galaxy.getGalaxyGroup()));
            numberOfStarsView.setText(getResources().getString(R.string.number_of_stars).concat(" ")
                    .concat(galaxy.getNumberOfStars()));
            typeView.setText(getResources().getString(R.string.galaxy_type).concat(" ")
                    .concat(galaxy.getType()));
            factsView.setText(getResources().getString(R.string.quickfacts).concat("\n\n")
                    .concat(galaxy.getFacts()));
            descriptionView.setVisibility(View.VISIBLE);
            diameterView.setVisibility(View.VISIBLE);
            massView.setVisibility(View.VISIBLE);
            designationView.setVisibility(View.VISIBLE);
            distanceView.setVisibility(View.VISIBLE);
            constellationView.setVisibility(View.VISIBLE);
            galaxyGroupView.setVisibility(View.VISIBLE);
            numberOfStarsView.setVisibility(View.VISIBLE);
            typeView.setVisibility(View.VISIBLE);
            factsView.setVisibility(View.VISIBLE);
        }
        else if (object instanceof Other) {
            Other other = (Other) object;
            name = other.getName();
            image = other.getImage().split("\\.")[0];
        }
        int resID = getResources().getIdentifier(image , "drawable", getPackageName());
        mCollapsingToolbarLayout.setTitle(name);
        Glide.with(this).load(resID).asBitmap().into(imageView);
        /*mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (appBarLayout.getTotalScrollRange() + verticalOffset == 0) {
                    //mToolbar.setBackground(getDrawable(R.color.colorPrimaryDark));

                    //Helper.customAnimation(Encyclopedia.this, mToolbar, 300, android.R.anim.fade_in);
                }
                else {
                    //mToolbar.setBackground(getDrawable(android.R.color.transparent));
                    //Helper.customAnimation(Encyclopedia.this, mToolbar, 300, android.R.anim.fade_in);
                }
            }
        });*/
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
