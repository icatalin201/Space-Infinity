package space.infinity.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.eyalbira.loadingdots.LoadingDots;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import space.infinity.app.R;
import space.infinity.app.model.entity.ImageItem;
import space.infinity.app.model.network.CheckingConnection;
import space.infinity.app.viewmodel.ImageItemViewModel;
import space.infinity.app.viewmodel.adapters.ImageAdapter;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LoadingDots progressBar;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.images);
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
        ImageItemViewModel imageItemViewModel = ViewModelProviders.of(this)
                .get(ImageItemViewModel.class);
        imageItemViewModel.getImageItems().observe(this, new Observer<List<ImageItem>>() {
            @Override
            public void onChanged(List<ImageItem> imageItems) {
                imageAdapter.add(imageItems);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        onBackPressed();
        return true;
    }

}
