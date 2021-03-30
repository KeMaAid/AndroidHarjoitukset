package com.example.week10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    Button searchButton;
    Button reloadButton;
    Button goBackButton;
    Button goForwardButton;
    EditText urlInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                handleButtons();
            };
        });
        searchButton = findViewById(R.id.buttonSearch);
        reloadButton = findViewById(R.id.buttonReload);
        goBackButton = findViewById(R.id.buttonGoBack);
        goForwardButton = findViewById(R.id.buttonGoForward);
        urlInput = findViewById(R.id.editTextURL);
    }

    public void handleSearchClick(View view){
        String payload = "https://";
        payload += urlInput.getText().toString();
        if(!payload.equals(webView.getUrl())){
            webView.loadUrl(payload);
        }
    }
    public void handleRefreshClick(View view){
        webView.reload();
    }

    public void handleInitClick(View view){
        String payload = "file:///android_asset/index.html";
        if(!payload.equals(webView.getUrl())){
            webView.loadUrl(payload);
        }
        webView.evaluateJavascript("javascript:initialize()", null);


    }
    public void handleShoutClick(View view){
        String payload = "file:///android_asset/index.html";
        if(!payload.equals(webView.getUrl())){
            webView.loadUrl(payload);
        }
        webView.evaluateJavascript("javascript:shoutOut()", null);
    }
    public void handleGoBackClick(View view){
        webView.goBack();
    }
    public void handleGoForwardClick(View view){
        webView.goForward();
    }
    public void handleButtons(){
        if(!webView.canGoForward()){
            goForwardButton.setEnabled(false);
        } else {
            goForwardButton.setEnabled(true);
        }
        if(!webView.canGoBack()){
            goBackButton.setEnabled(false);
        } else {
            goBackButton.setEnabled(true);
        }
        urlInput.setText(webView.getOriginalUrl().substring(8));
    }
}