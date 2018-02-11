package space.infinity.app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.infinity.app.R;
import space.infinity.app.adapters.ApodGalleryAdapter;
import space.infinity.app.models.apod.APOD;
import space.infinity.app.network.Client;
import space.infinity.app.network.Service;
import space.infinity.app.utils.Constants;

public class GalleryActivity extends AppCompatActivity {

    private Call<APOD> imageCall;
    private List<APOD> imageDataList;
    private ApodGalleryAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Date dateInstance;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = findViewById(R.id.my_awesome_toolbar);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_title.setText(R.string.images);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.gallery_recycler);
        imageDataList = new ArrayList<>();
        adapter = new ApodGalleryAdapter(this, imageDataList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        loadGallery();
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

    private void loadGallery() {
        for (int i = 1; i < 21; i++) {
            Log.i("iteration nr: ", Integer.toString(i));
            dateInstance = new Date();
            calendar = Calendar.getInstance();
            calendar.setTime(dateInstance);
            calendar.add(Calendar.DATE, -i);
            Date dateBeforeXDays = calendar.getTime();
            DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
            String date = dateFormat.format(dateBeforeXDays);
            Log.i("date iteration", date);
            Service service = Client.getRetrofitClient(Constants.NASA_URL).create(Service.class);
            imageCall = service.getApodByDate(date, Constants.API_KEY);
            imageCall.enqueue(new Callback<APOD>() {
                @Override
                public void onResponse(Call<APOD> call, Response<APOD> response) {
                    if (!response.isSuccessful()) {
                        imageCall = call.clone();
                        imageCall.enqueue(this);
                        return;
                    }
                    if (response.body() == null) return;
                    if (response.body().getMedia_type().equals("video")) return;

                    imageDataList.add(response.body());
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<APOD> call, Throwable t) {

                }
            });
        }
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
