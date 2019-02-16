package space.infinity.app.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import space.infinity.app.R;

public class ISSLiveStreamActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isslive_stream);
        WebView videoView = findViewById(R.id.video);
        videoView.getSettings().setJavaScriptEnabled(true);
        videoView.loadUrl("http://www.ustream.tv/embed/17074538?html5ui");
        videoView.setWebChromeClient(new WebChromeClient());
    }
}
