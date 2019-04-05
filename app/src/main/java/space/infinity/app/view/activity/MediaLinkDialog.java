package space.infinity.app.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import space.infinity.app.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Locale;

public class MediaLinkDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_link_dialog);
        String title = getIntent().getStringExtra("title");
        String[] urls = getIntent().getStringArrayExtra("urls");
        getWindow().setNavigationBarColor(getResources().getColor(R.color.white));
        setTitle(title);
        LinearLayout layout = findViewById(R.id.media_layout);
        for (int i = 0; i < urls.length; i++) {
            final String url = urls[i];
            Button button = new Button(this);
            button.setText(String.format(Locale.getDefault(), "Link %d", i + 1));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MediaLinkDialog.this, InternalWebActivity.class);
                    intent.putExtra("url", url);
                    startActivity(intent);
                }
            });
            layout.addView(button);
        }
    }
}
