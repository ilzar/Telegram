package org.telegram.ui.webviewintro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
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
        final View root = findViewById(R.id.root);
        final WebView webview = (WebView) findViewById(R.id.webview_intro);
        final WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                setProgress(progress * 1000);
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view,
                                        int errorCode,
                                        String description,
                                        String failingUrl) {
                ApplicationLoader.loadFenegramWallpaper();
                root.setBackgroundDrawable(ApplicationLoader.getCachedWallpaper());
                webview.setVisibility(View.GONE);
            }
        });
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.loadUrl(getString(R.string.FenegramLandingUrl));

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
