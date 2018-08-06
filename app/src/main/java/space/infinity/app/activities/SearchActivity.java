package space.infinity.app.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import space.infinity.app.adapters.SearchAdapter;
import space.infinity.app.models.gallery.ImageInfo;
import space.infinity.app.utils.Constants;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<ImageInfo> imageList;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.my_awesome_toolbar);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_title.setText(R.string.search_result);
        recyclerView = findViewById(R.id.search_recycler);
        progressBar = findViewById(R.id.progress_bar);
        imageList = new ArrayList<>();
        searchAdapter = new SearchAdapter(this, imageList);
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        String query = getIntent().getStringExtra("query");
        if (query != null) {
            String url = Constants.NASA_IMAGE_URL.concat("search?q=").concat(query);
            new ImageCall().execute(url);
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
                    imageList.clear();
                    recyclerView.removeAllViews();
                    recyclerView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    String url = Constants.NASA_IMAGE_URL.concat("search?q=").concat(query);
                    new ImageCall().execute(url);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info:
                Intent aboutIntent = new Intent(getApplicationContext(), About.class);
                startActivity(aboutIntent);
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
                reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),
                        "iso-8859-1"), 8);
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                reader.close();
                httpURLConnection.disconnect();
                jsonObject = new JSONObject(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            Log.i("json", jsonObject.toString());
            try {
                JSONArray array = jsonObject.getJSONObject("collection").getJSONArray("items");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = (JSONObject) array.get(i);
                    JSONObject o = (JSONObject) object.getJSONArray("data").get(0);
                    if (o.getString("media_type").equals("image")) {
                        JSONObject oo = (JSONObject) object.getJSONArray("links").get(0);
                        ImageInfo imageInfo = new ImageInfo(o.getString("date_created"),
                                o.getString("media_type"), o.getString("description"),
                                o.getString("title"), "",
                                oo.getString("href"));
                        imageList.add(imageInfo);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            searchAdapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

}
