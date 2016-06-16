package kingja.com.kingja_exception;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppManager.getAppManager().addActivity(this);

        Object o=null;
        o.toString();
        WebView mWebView = (WebView) findViewById(R.id.wb_log);
        WebSettings webSettings =   mWebView .getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        mWebView.loadData(SdCardManager.txt2Html("KLogs","CrashLogs.txt",getApplication()), "text/html", "UTF-8");
    }




}
