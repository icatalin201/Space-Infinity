package space.infinity.app.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.eyalbira.loadingdots.LoadingDots;

import java.util.ArrayList;
import java.util.List;

import space.infinity.app.R;
import space.infinity.app.adapters.EncyclopediaAdapter;
import space.infinity.app.dao.CosmicDao;
import space.infinity.app.database.AppDatabase;
import space.infinity.app.database.AppDatabaseHelper;
import space.infinity.app.models.CosmicItem;
import space.infinity.app.models.Galaxy;
import space.infinity.app.models.Moon;
import space.infinity.app.models.Planet;
import space.infinity.app.models.Star;
import space.infinity.app.utils.Constants;
import space.infinity.app.utils.ThreadHelper;

public class EncyclopediaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LoadingDots progressBar;
    private EncyclopediaAdapter encyclopediaAdapter;
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
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recycler);
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
        encyclopediaAdapter = new EncyclopediaAdapter(this, new ArrayList<CosmicItem>());
        recyclerView.setAdapter(encyclopediaAdapter);
        recyclerView.setLayoutAnimation(AnimationUtils
                .loadLayoutAnimation(this, R.anim.layout_animation_down));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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
            extends space.infinity.app.models.ActivityHelper
            implements ThreadHelper.CosmicInterface {

        private AppDatabase appDatabase;
        private CosmicDao cosmicDao;
        private ThreadHelper threadHelper;
        private CosmicItem cosmicItem;

        ActivityHelper(Context context, CosmicItem cosmicItem) {
            super(context);
            this.cosmicItem = cosmicItem;
            appDatabase = AppDatabaseHelper.getDatabase(context);
            cosmicDao = appDatabase.getCosmicDao();
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
                    info1String = ((Galaxy) cosmicItem).getConstellation();
                    info2String = ((Galaxy) cosmicItem).getDesignation();
                    info3String = ((Galaxy) cosmicItem).getDiameter();
                    info4String = ((Galaxy) cosmicItem).getDistance();
                    info5String = ((Galaxy) cosmicItem).getGalaxyGroup();
                    info6String = ((Galaxy) cosmicItem).getGalaxyType();
                    info7String = ((Galaxy) cosmicItem).getMass();
                    info8String = ((Galaxy) cosmicItem).getNumberOfStars();
                    break;
                case CosmicItem.CosmicType.MOON:
                    factsString = ((Moon) cosmicItem).getFacts();
                    info1String = ((Moon) cosmicItem).getDiameter();
                    info2String = ((Moon) cosmicItem).getMass();
                    info3String = ((Moon) cosmicItem).getFirstRecord();
                    info4String = ((Moon) cosmicItem).getRecordedBy();
                    info5String = ((Moon) cosmicItem).getOrbits();
                    info6String = ((Moon) cosmicItem).getOrbitPeriod();
                    info7String = ((Moon) cosmicItem).getOrbitDistance();
                    info8String = ((Moon) cosmicItem).getSurfaceTemperature();
                    break;
                case CosmicItem.CosmicType.PLANET:
                    factsString = ((Planet) cosmicItem).getFacts();
                    info1String = ((Planet) cosmicItem).getDiameter();
                    info2String = ((Planet) cosmicItem).getMass();
                    info3String = ((Planet) cosmicItem).getFirstRecord();
                    info4String = ((Planet) cosmicItem).getRecordedBy();
                    info5String = ((Planet) cosmicItem).getMoons();
                    info6String = ((Planet) cosmicItem).getOrbitDistance();
                    info7String = ((Planet) cosmicItem).getOrbitPeriod();
                    info8String = ((Planet) cosmicItem).getSurfaceTemperature();
                    break;
                case CosmicItem.CosmicType.STAR:
                    factsString = ((Star) cosmicItem).getFacts();
                    info1String = ((Star) cosmicItem).getStarType();
                    info2String = ((Star) cosmicItem).getDiameter();
                    info3String = ((Star) cosmicItem).getMass();
                    info4String = ((Star) cosmicItem).getAge();
                    info5String = ((Star) cosmicItem).getSurfaceTemperature();
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
            threadHelper = new ThreadHelper(new Runnable() {
                @Override
                public void run() {
                    List<? extends CosmicItem> cosmicItemList = new ArrayList<>();
                    switch (cosmicItem.getType()) {
                        case CosmicItem.CosmicType.GALAXY:
                            cosmicItemList = cosmicDao.getGalaxyList();
                            break;
                        case CosmicItem.CosmicType.MOON:
                            cosmicItemList = cosmicDao.getMoonList();
                            break;
                        case CosmicItem.CosmicType.PLANET:
                            cosmicItemList = cosmicDao.getPlanetList();
                            break;
                        case CosmicItem.CosmicType.STAR:
                            cosmicItemList = cosmicDao.getStarList();
                            break;
                        case CosmicItem.CosmicType.OTHER:
                            break;
                    }
                    onResult(cosmicItemList);
                }
            });
            threadHelper.startExecutor();
        }

        @Override
        public void onDestroy() {
            appDatabase.close();
        }

        @Override
        public void showLayout() {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onResult(final List<? extends CosmicItem> cosmicItemList) {
            threadHelper.stopExecutor();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    encyclopediaAdapter.add(cosmicItemList);
                    showLayout();
                }
            });
        }
    }
}
