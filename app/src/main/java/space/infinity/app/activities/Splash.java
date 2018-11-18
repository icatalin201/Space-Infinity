package space.infinity.app.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Space;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import space.infinity.app.R;
import space.infinity.app.models.apod.APOD;
import space.infinity.app.models.facts.SpaceFact;
import space.infinity.app.sql.SqlService;
import space.infinity.app.utils.Constants;
import space.infinity.app.utils.Helper;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView image = findViewById(R.id.splash);
        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.animation);
        image.startAnimation(pulse);
        String firstTime = Helper.getFromSharedPreferences(Constants.FIRST_TIME_FLAG, this, Constants.FIRST_TIME_FLAG);
        if (firstTime.equals("")) {
            Helper.putInSharedPreferences(Constants.FIRST_TIME_FLAG, this, Constants.FIRST_TIME_FLAG, "no");
            new DoDirtyJob().execute();
        } else {
            start();
        }
    }

    private void start() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this, MainActivity.class));
                finish();
            }
        }, 3000);
    }

    @SuppressLint("StaticFieldLeak")
    private class DoDirtyJob extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            openAndReadApod();
            openAndReadFacts();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            start();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Log.i("back", "pressed - ha ha");
    }

    private void openAndReadFacts() {
        List<SpaceFact> factsList = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("doc.txt")));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                SpaceFact facts = new SpaceFact();
                facts.setName(mLine);
                factsList.add(facts);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        SqlService.insertFactList(this, factsList);
    }

    private void openAndReadApod() {
        List<APOD> apodList = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("apod.txt")));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                String[] result = mLine.split("-//-");
                APOD apod = new APOD();
                apod.setTitle(result[0]);
                apod.setExplanation(result[1]);
                apod.setHdurl(result[2]);
                apod.setUrl(result[3]);
                apod.setCopyright(result[4]);
                apodList.add(apod);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        SqlService.insertApodList(this, apodList);
    }
}
