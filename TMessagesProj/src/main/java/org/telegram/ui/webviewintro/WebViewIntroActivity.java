package org.telegram.ui.webviewintro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import org.telegram.messenger.R;
import org.telegram.ui.LaunchActivity;

/**
 * Created by ilzar on 25.09.15.
 */
public class WebViewIntroActivity extends Activity {

    private boolean startPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TMessages);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.webview_intro_layout);

        WebView webview = (WebView) findViewById(R.id.webview_intro);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl("http://vaseker.github.io/fenergram-land/ios.index.html");

        Button startMessaging = (Button) findViewById(R.id.start_messaging);
        startMessaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startPressed) {
                    return;
                }
                startPressed = true;
                Intent intent2 = new Intent(WebViewIntroActivity.this, LaunchActivity.class);
                intent2.putExtra("fromIntro", true);
                startActivity(intent2);
                finish();
            }
        });
    }
    
}
