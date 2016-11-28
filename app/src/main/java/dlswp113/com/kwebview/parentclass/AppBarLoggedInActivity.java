package dlswp113.com.kwebview.parentclass;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;

import com.naver.jsqim.unipair.LoginActivity;
import com.naver.jsqim.unipair.R;
import com.naver.jsqim.unipair.app.AppConfig;
import com.naver.jsqim.unipair.helper.SQLiteHandler;
import com.naver.jsqim.unipair.helper.SessionManager;
import com.naver.jsqim.unipair.manager.Communicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Kim on 16. 2. 3..
 */
public class AppBarLoggedInActivity extends AppBarActivity {

    private SQLiteHandler db;
    private SessionManager session;
    private String userEmail;
    private String encryptedPW;
    private int isVendor;
    public String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        setContentView(R.layout.activity_with_toolbar);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = new LoginFragment();
            manager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
        */

    }

    public String getUserEmail() {
        return userEmail;
    }
    public int getIsVendor() { return isVendor; }

    @Override
    protected void onResume() {
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        userEmail = user.get("email");
        encryptedPW = user.get("encrypted_password");
        uid = user.get("uid");
        isVendor = Integer.parseInt(user.get("is_vendor"));
        Log.i("isVendor??" , "" + isVendor);

        HashMap<String,String> postMap = new HashMap<>();
        postMap.put("email", userEmail);

        Communicator c = new Communicator();
        c.postHttp(AppConfig.URL_GET_USER_INFO, postMap, new Handler() {
            public void handleMessage(Message msg) {
                String jsonString = msg.getData().getString("jsonString");
                Log.i("dbEncrypt", "jsonString: " + jsonString);
                try {
                    Log.i("dbEncrypt", "4");
                    JSONObject dataObject = new JSONObject(jsonString);
                    Log.i("dbEncrypt", "3");
                    JSONArray jsonStoryList = dataObject.getJSONArray("encrypted_password");

                    Log.i("letssee", jsonStoryList.length() + "");
                    Log.i("dbEncrypt", "5");
                    JSONObject tempObject;
                    for (int i = 0; i < jsonStoryList.length(); i++) {
                        Log.i("dbEncrypt", "0");
                        tempObject = jsonStoryList.getJSONObject(i);
                        Log.i("dbEncrypt", "1");
                        String dbEncryptedPw = tempObject.getString("encrypted_password");
                        Log.i("dbEncrypt", dbEncryptedPw);

                        // 서버의 비번과 다르면 로그아웃시킨다.
                        if(encryptedPW == null) {
                            logoutUser();
                        }

                        else if (!encryptedPW.equals(dbEncryptedPw)) {
                            logoutUser();
                        }
                    }
                } catch (JSONException e) {
                    logoutUser();
                    Log.i("PRINTSTACKTRACE", e.toString());
                    e.printStackTrace();
                }
            }
        });

        //Toast.makeText(this, "email: " + userEmail + " pw: " + encryptedPW, Toast.LENGTH_LONG).show();
        super.onResume();
    }


    public void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
