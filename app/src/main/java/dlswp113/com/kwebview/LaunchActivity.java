package dlswp113.com.kwebview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by USER on 2016-04-28.
 */
public class LaunchActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        Handler h = new Handler();

        h.postDelayed(new splashhandler(), 3000);
//        h.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//             Intent i = new Intent(LaunchActivity.this, MainActivity.class);
//                startActivity(i);
//                finish(); }   }, 3000);

    }

    class splashhandler implements Runnable {
        public void run(){

            startActivity(
                    new Intent(getApplication(), MainActivity.class));
            LaunchActivity.this.finish();
        }
    }
}
