package space.infinity.app.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import space.infinity.app.R;
import space.infinity.app.model.entity.CosmicItem;
import space.infinity.app.model.entity.Galaxy;
import space.infinity.app.model.entity.Moon;
import space.infinity.app.model.entity.Planet;
import space.infinity.app.model.entity.Star;
import space.infinity.app.util.Constants;

public class EncyclopediaActivity extends AppCompatActivity {

    private ActivityHelper activityHelper;
    private ImageView imageView;
    private TextView facts;
    private TextView info1;
    private TextView info2;
    private TextView info3;
    private TextView info4;
    private TextView info5;
    private TextView info6;
    private TextView info7;
    private TextView info8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encyclopedia);
        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        imageView = findViewById(R.id.image);
        TextView description = findViewById(R.id.description);
        facts = findViewById(R.id.facts);
        info1 = findViewById(R.id.info1);
        info2 = findViewById(R.id.info2);
        info3 = findViewById(R.id.info3);
        info4 = findViewById(R.id.info4);
        info5 = findViewById(R.id.info5);
        info6 = findViewById(R.id.info6);
        info7 = findViewById(R.id.info7);
        info8 = findViewById(R.id.info8);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        CosmicItem cosmicItem = getIntent().getParcelableExtra(Constants.ENCYCLOPEDIA);
        collapsingToolbarLayout.setTitle(cosmicItem.getName());
        description.setText(cosmicItem.getDescription());
        activityHelper = new ActivityHelper(this, cosmicItem);
        activityHelper.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityHelper.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp(){
        supportFinishAfterTransition();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
    }

    private class ActivityHelper
            extends space.infinity.app.model.entity.ActivityHelper {

        private CosmicItem cosmicItem;

        ActivityHelper(Context context, CosmicItem cosmicItem) {
            super(context);
            this.cosmicItem = cosmicItem;
        }

        private void setTexts() {
            String factsString = "";
            String info1String = "";
            String info2String = "";
            String info3String = "";
            String info4String = "";
            String info5String = "";
            String info6String = "";
            String info7String = "";
            String info8String = "";
            switch (cosmicItem.getType()) {
                case CosmicItem.CosmicType.GALAXY:
                    factsString = ((Galaxy) cosmicItem).getFacts();
                    info1String = "Constellation\n".concat(((Galaxy) cosmicItem).getConstellation());
                    info2String = "Designation\n".concat(((Galaxy) cosmicItem).getDesignation());
                    info3String = "Diameter\n".concat(((Galaxy) cosmicItem).getDiameter());
                    info4String = "Distance\n".concat(((Galaxy) cosmicItem).getDistance());
                    info5String = "Galaxy Group\n".concat(((Galaxy) cosmicItem).getGalaxyGroup());
                    info6String = "Galaxy Type\n".concat(((Galaxy) cosmicItem).getGalaxyType());
                    info7String = "Mass\n".concat(((Galaxy) cosmicItem).getMass());
                    info8String = "Number of Stars\n".concat(((Galaxy) cosmicItem).getNumberOfStars());
                    break;
                case CosmicItem.CosmicType.MOON:
                    factsString = ((Moon) cosmicItem).getFacts();
                    info1String = "Diameter\n".concat(((Moon) cosmicItem).getDiameter());
                    info2String = "Mass\n".concat(((Moon) cosmicItem).getMass());
                    info3String = "First Record\n".concat(((Moon) cosmicItem).getFirstRecord());
                    info4String = "Recorded by\n".concat(((Moon) cosmicItem).getRecordedBy());
                    info5String = "Orbits\n".concat(((Moon) cosmicItem).getOrbits());
                    info6String = "Orbit Period\n".concat(((Moon) cosmicItem).getOrbitPeriod());
                    info7String = "Orbit Distance\n".concat(((Moon) cosmicItem).getOrbitDistance());
                    info8String = "Surface Temperature\n".concat(((Moon) cosmicItem).getSurfaceTemperature());
                    break;
                case CosmicItem.CosmicType.PLANET:
                    factsString = ((Planet) cosmicItem).getFacts();
                    info1String = "Diameter\n".concat(((Planet) cosmicItem).getDiameter());
                    info2String = "Mass\n".concat(((Planet) cosmicItem).getMass());
                    info3String = "First Record\n".concat(((Planet) cosmicItem).getFirstRecord());
                    info4String = "Recorded by\n".concat(((Planet) cosmicItem).getRecordedBy());
                    info5String = "Moons\n".concat(((Planet) cosmicItem).getMoons());
                    info6String = "Orbit Distance\n".concat(((Planet) cosmicItem).getOrbitDistance());
                    info7String = "Orbit Period\n".concat(((Planet) cosmicItem).getOrbitPeriod());
                    info8String = "Surface Temperature\n".concat(((Planet) cosmicItem).getSurfaceTemperature());
                    break;
                case CosmicItem.CosmicType.STAR:
                    factsString = ((Star) cosmicItem).getFacts();
                    info1String = "Star Type\n".concat(((Star) cosmicItem).getStarType());
                    info2String = "Diameter\n".concat(((Star) cosmicItem).getDiameter());
                    info3String = "Mass\n".concat(((Star) cosmicItem).getMass());
                    info4String = "Age\n".concat(((Star) cosmicItem).getAge());
                    info5String = "Surface Temperature\n".concat(((Star) cosmicItem).getSurfaceTemperature());
                    break;
                case CosmicItem.CosmicType.OTHER:
                    break;
            }
            facts.setText(factsString);
            info1.setText(info1String);
            info2.setText(info2String);
            info3.setText(info3String);
            info4.setText(info4String);
            info5.setText(info5String);
            info6.setText(info6String);
            info7.setText(info7String);
            info8.setText(info8String);
        }

        @Override
        public void onStart() {
            setTexts();
            String path = cosmicItem.getImage();
            Glide.with(getContext())
                    .load(path)
                    .apply(RequestOptions.centerCropTransform())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }

        @Override
        public void onDestroy() {
            
        }

        @Override
        public void showLayout() {
        }
    }
}
