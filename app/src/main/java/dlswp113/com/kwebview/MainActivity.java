package dlswp113.com.kwebview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dlswp113.com.kwebview.R;
import dlswp113.com.kwebview.control.GCMManager;


//android Web view를 이용한 KUSITM 어플리케이션
public class MainActivity extends AppCompatActivity {

    private static String user_id = "";
    private GCMManager gcmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager telManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNum = telManager.getLine1Number();

        user_id = phoneNum;
        //registerGcm();
        gcmManager = new GCMManager(this, user_id);
        //gcmManager.sendUserPush(user_id, "test_title", "test_contents");

        String url = "http://dlswp113.dothome.co.kr/kusitms13/index.html"; //kusitm web address
        Log.i("URL", "Opening URL :" + url);

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient()); // 이걸 안해주면 새창이 뜸
        webView.loadUrl(url);
    }

}


