package space.infinity.app.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

import space.infinity.app.R;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        Toolbar toolbar = findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar_title.setText(R.string.about);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void rate(View view) {
        String packageName = getPackageName();
        String playStoreLink = "https://play.google.com/store/apps/details?id=" + packageName;
        Intent appRateUsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(playStoreLink));
        startActivity(appRateUsIntent);
    }

    public void share(View view) {
        String packageName = getPackageName();
        Intent appShareIntent = new Intent(Intent.ACTION_SEND);
        appShareIntent.setType("text/plain");
        String extraText = "Hey! Check out this amazing app on Play Store. \n";
        extraText += "https://play.google.com/store/apps/details?id=" + packageName;
        appShareIntent.putExtra(Intent.EXTRA_TEXT, extraText);
        startActivity(appShareIntent);
    }

    public void getSpace(View view) {
        String packageName = "space.pal.sig";
        String playStoreLink = "https://play.google.com/store/apps/details?id=" + packageName;
        Intent spaceApp = new Intent(Intent.ACTION_VIEW, Uri.parse(playStoreLink));
        startActivity(spaceApp);
    }
}
