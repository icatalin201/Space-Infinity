package space.infinity.app.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import android.widget.ProgressBar;

import com.eyalbira.loadingdots.LoadingDots;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import space.infinity.app.R;
import space.infinity.app.adapters.ImageAdapter;
import space.infinity.app.dao.ImageItemDao;
import space.infinity.app.database.AppDatabase;
import space.infinity.app.database.AppDatabaseHelper;
import space.infinity.app.models.ImageItem;
import space.infinity.app.utils.Constants;
import space.infinity.app.utils.ThreadHelper;

public class SearchActivity extends AppCompatActivity {

    private class ActivityHelper
            extends space.infinity.app.models.ActivityHelper {

        @SuppressLint("StaticFieldLeak")
        private class ImageCall extends AsyncTask<String, Void, JSONObject> {

            @Override
            protected JSONObject doInBackground(String... strings) {
                URL url;
                HttpURLConnection httpURLConnection;
                JSONObject jsonObject = null;
                StringBuilder stringBuilder = new StringBuilder();
                BufferedReader reader;
                try {
                    url = new URL(strings[0]);
                    Log.i("url", url.toString());
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    reader = new BufferedReader(new
                            InputStreamReader(httpURLConnection.getInputStream(),
                            "iso-8859-1"), 8);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    reader.close();
                    httpURLConnection.disconnect();
                    jsonObject = new JSONObject(stringBuilder.toString());
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                return jsonObject;
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                Log.i("json", jsonObject.toString());
                try {
                    JSONArray array = jsonObject.getJSONObject("collection").getJSONArray("items");
                    List<ImageItem> imageItemList = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = (JSONObject) array.get(i);
                        JSONObject o = (JSONObject) object.getJSONArray("data").get(0);
                        if (o.getString("media_type").equals("image")) {
                            JSONObject oo = (JSONObject) object.getJSONArray("links").get(0);
                            ImageItem imageInfo = new ImageItem();
                            if (o.has("date_created")) {
                                imageInfo.setDateCreated(o.getString("date_created"));
                            }
                            if (o.has("description")) {
                                imageInfo.setDescription(o.getString("description"));
                            }
                            if (o.has("title")) {
                                imageInfo.setTitle(o.getString("title"));
                            }
                            if (oo.has("href")) {
                                imageInfo.setImage(oo.getString("href"));
                            } else continue;
                            imageItemList.add(imageInfo);
                        }
                    }
                    imageAdapter.add(imageItemList);
                    showLayout();
                } catch (JSONException e) {
                    e.printStackTrace();
                    showLayout();
                }
            }
        }

        ActivityHelper(Context context) {
            super(context);
        }

        @Override
        public void onStart() {
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public void showLayout() {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        void onSearch(String query) {
            setTitle(query);
            recyclerView.setLayoutAnimation(AnimationUtils
                    .loadLayoutAnimation(getContext(), R.anim.layout_animation_down));
            imageAdapter.remove();
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            String url = Constants.NASA_IMAGE_URL.concat("search?q=").concat(query);
            new ImageCall().execute(url);
        }
    }

    private RecyclerView recyclerView;
    private LoadingDots progressBar;
    private ImageAdapter imageAdapter;
    private ActivityHelper activityHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24px);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        recyclerView = findViewById(R.id.search_recycler);
        progressBar = findViewById(R.id.progress_bar);
        imageAdapter = new ImageAdapter(this, new ArrayList<ImageItem>());
        recyclerView.setAdapter(imageAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        String query = getIntent().getStringExtra("query");
        activityHelper = new ActivityHelper(this);
        if (query != null) {
            activityHelper.onSearch(query);
        }
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
                    activityHelper.onSearch(query);
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
