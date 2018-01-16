package space.infinity.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import space.infinity.app.R;

public class MarsActivity extends AppCompatActivity {

    private Intent intent;
    private TextView toolbar_title;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
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
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void curiosityRover(View view){
        intent.putExtra("rover", "Curiosity");
        startActivity(intent);
    }

    public void opportunityRover(View view){
        intent.putExtra("rover", "Opportunity");
        startActivity(intent);
    }

    public void spiritRover(View view){
        intent.putExtra("rover", "Spirit");
        startActivity(intent);
    }
}
