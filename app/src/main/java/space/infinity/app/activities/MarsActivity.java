package space.infinity.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import space.infinity.app.R;
import space.infinity.app.network.CheckingConnection;

public class MarsActivity extends AppCompatActivity {

    private Intent intent;
    private TextView toolbar_title;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mars);
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_title.setText(R.string.rover);
        intent = new Intent(this, MarsRoverActivity.class);
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

    public void curiosityRover(View view){
        if (CheckingConnection.isConnected(this)) {
            intent.putExtra("rover", "Curiosity");
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public void opportunityRover(View view){
        if (CheckingConnection.isConnected(this)) {
            intent.putExtra("rover", "Opportunity");
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public void spiritRover(View view){
        if (CheckingConnection.isConnected(this)) {
            intent.putExtra("rover", "Spirit");
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }
}
