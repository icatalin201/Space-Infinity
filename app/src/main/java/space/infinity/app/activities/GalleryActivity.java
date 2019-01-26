package space.infinity.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.eyalbira.loadingdots.LoadingDots;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import space.infinity.app.R;
import space.infinity.app.adapters.ImageAdapter;
import space.infinity.app.dao.ImageItemDao;
import space.infinity.app.database.AppDatabase;
import space.infinity.app.database.AppDatabaseHelper;
import space.infinity.app.models.ImageItem;
import space.infinity.app.network.CheckingConnection;
import space.infinity.app.utils.ThreadHelper;

public class GalleryActivity extends AppCompatActivity {

    private class ActivityHelper
            extends space.infinity.app.models.ActivityHelper
            implements ThreadHelper.ImageInterface {

        private AppDatabase appDatabase;
        private ImageItemDao imageItemDao;
        private ThreadHelper threadHelper;

        ActivityHelper(Context context) {
            setContext(context);
            appDatabase = AppDatabaseHelper.getDatabase(context);
            imageItemDao = appDatabase.getImageItemDao();
            threadHelper = new ThreadHelper(new Runnable() {
                @Override
                public void run() {
                    List<ImageItem> imageItemList = imageItemDao.getImageList();
                    onResult(imageItemList);
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
            recyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onResult(final List<ImageItem> imageItemList) {
            threadHelper.stopExecutor();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imageAdapter.add(imageItemList);
                    showLayout();
                }
            });
        }
    }

    private RecyclerView recyclerView;
    private LoadingDots progressBar;
    private ImageAdapter imageAdapter;
    private ActivityHelper activityHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.gallery_recycler);
        imageAdapter = new ImageAdapter(this, new ArrayList<ImageItem>());
        recyclerView.setAdapter(imageAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutAnimation(AnimationUtils
                .loadLayoutAnimation(this, R.anim.layout_animation_down));
        activityHelper = new ActivityHelper(this);
        activityHelper.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityHelper.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.searchk));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("search", query);
                menuItem.collapseActionView();
                searchView.onActionViewCollapsed();
                searchView.setQuery("", false);
                searchView.clearFocus();
                if (!query.trim().equals("")) {
                    if (CheckingConnection.isConnected(getApplicationContext())) {
                        Intent intent = new Intent(GalleryActivity.this,
                                SearchActivity.class);
                        intent.putExtra("query", query);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),
                                "No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
