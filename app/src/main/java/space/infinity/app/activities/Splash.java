package space.infinity.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.infinity.app.R;
import space.infinity.app.models.apod.APOD;
import space.infinity.app.network.Client;
import space.infinity.app.network.Service;
import space.infinity.app.sql.SqlService;
import space.infinity.app.utils.Constants;
import space.infinity.app.utils.Helper;

public class Splash extends AppCompatActivity {

    private final int SPLASH_TIME_OUT = 3000;
    private Call<APOD> imageCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView image = findViewById(R.id.splash);
        Helper.setAnimationForAll(this, image);
        //populateDb();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this, MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void populateDb() {
        for (int i = 1; i < 51; i++) {
            Log.i("iteration nr: ", Integer.toString(i));
            Date dateInstance = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateInstance);
            calendar.add(Calendar.DATE, -i);
            Date dateBeforeXDays = calendar.getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
                    if (response.body().getMedia_type().equals("image")) {
                        SqlService.insertImageDataIntoSql(Splash.this, response.body());
                    }

                }

                @Override
                public void onFailure(Call<APOD> call, Throwable t) {

                }
            });
        }
    }
}
