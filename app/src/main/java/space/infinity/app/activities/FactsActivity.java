package space.infinity.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eyalbira.loadingdots.LoadingDots;

import java.util.Collections;
import java.util.List;

import space.infinity.app.R;
import space.infinity.app.dao.SpaceFactDao;
import space.infinity.app.database.AppDatabase;
import space.infinity.app.database.AppDatabaseHelper;
import space.infinity.app.models.SpaceFact;
import space.infinity.app.utils.Helper;
import space.infinity.app.utils.ThreadHelper;

public class FactsActivity extends AppCompatActivity {

    private class ActivityHelper
            extends space.infinity.app.models.ActivityHelper
            implements ThreadHelper.FactsInterface {

        private List<SpaceFact> spaceFactList;
        private ThreadHelper threadHelper;
        private AppDatabase appDatabase;
        private SpaceFactDao spaceFactDao;
        private int size = 0;
        private int index = -1;
        private int max = 0;

        ActivityHelper(Context context) {
            super(context);
            appDatabase = AppDatabaseHelper.getDatabase(context);
            spaceFactDao = appDatabase.getSpaceFactDao();
            threadHelper = new ThreadHelper(new Runnable() {
                @Override
                public void run() {
                    spaceFactList = spaceFactDao.getSpaceFactList();
                    Collections.shuffle(spaceFactList);
                    size = spaceFactList.size();
                    max = size - 1;
                    if (size > 0) index = 0;
                    onResult(spaceFactList);
                }
            });
        }

        @Override
        public void onStart() {
            threadHelper.startExecutor();
        }

        @Override
        public void onDestroy() {
            appDatabase.close();
        }

        @Override
        public void showLayout() {
            progressBar.setVisibility(View.GONE);
            content.setVisibility(View.VISIBLE);
        }

        public SpaceFact getFact() {
            return spaceFactList.get(index);
        }

        private void setFavorite(SpaceFact fact) {
            if (fact.isFavorite()) {
                fav.setImageResource(R.drawable.ic_baseline_favorite_24px);
                fav.setTag(true);
            } else {
                fav.setImageResource(R.drawable.ic_baseline_favorite_border_24px);
                fav.setTag(false);
            }
        }

        void alterFavorite() {
            if (index == -1) return;
            final SpaceFact spaceFact = getFact();
            spaceFact.setFavorite(!spaceFact.isFavorite());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    spaceFactDao.update(spaceFact);
                }
            }).start();
            setFavorite(spaceFact);
        }

        void changeFactNext() {
            if (index == -1) return;
            if (index < max) {
                index++;
            }
            else if (index == max){
                index = 0;
            }
            changeFact();
        }

        void changeFactPrev() {
            if (index == -1) return;
            if (index > 0) {
                index--;
            }
            else if (index == 0) {
                index = max;
            }
            changeFact();
        }

        void changeFact() {
            SpaceFact spaceFact = getFact();
            factView.setText(spaceFact.getName());
            setFavorite(spaceFact);
            Helper.customAnimation(getContext(), factView, 700, android.R.anim.fade_in);
        }

        void shareFact() {
            if (index == -1) return;
            String packageName = getPackageName();
            Intent appShareIntent = new Intent(Intent.ACTION_SEND);
            appShareIntent.setType("text/plain");
            SpaceFact spaceFact = getFact();
            String extraText = spaceFact.getName().concat("\n");
            extraText += "See more. Download the app!\n";
            extraText += "https://play.google.com/store/apps/details?id=" + packageName;
            appShareIntent.putExtra(Intent.EXTRA_TEXT, extraText);
            startActivity(appShareIntent);
        }

        @Override
        public void onResult(List<SpaceFact> spaceFactList) {
            threadHelper.stopExecutor();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showLayout();
                    if (index == -1) {
                        factView.setText("No space fact in your database. :(");
                        return;
                    }
                    changeFact();
                }
            });
        }
    }

    private TextView factView;
    private LinearLayout content;
    private LoadingDots progressBar;
    private ActivityHelper activityHelper;
    private ImageButton fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.facts);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        factView = findViewById(R.id.fact);
        content = findViewById(R.id.content);
        fav = findViewById(R.id.fav);
        progressBar = findViewById(R.id.progress_bar);
        activityHelper = new ActivityHelper(this);
        activityHelper.onStart();
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityHelper.alterFavorite();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityHelper.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.facts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share_fact:
                activityHelper.shareFact();
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
        activityHelper.changeFactPrev();
    }

    public void doNext(View view) {
        activityHelper.changeFactNext();
    }
}
