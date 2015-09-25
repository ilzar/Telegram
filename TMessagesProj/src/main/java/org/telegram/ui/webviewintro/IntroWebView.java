package org.telegram.ui.webviewintro;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by ilzar on 25.09.15.
 */
class IntroWebView extends WebView {

    public IntroWebView(Context context) {
        super(context);
    }

    public IntroWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IntroWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public IntroWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }
}