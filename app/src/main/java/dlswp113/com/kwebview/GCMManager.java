package dlswp113.com.kwebview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * Created by shcho on 11/15/2015.
 */
public class GCMManager {

    public static final String PROJECT_ID = "387399191051";
    public static String M_REGIST_ID;

    private static String M_USER_ID;
    private Context mContext;


    public GCMManager(Context context, String userId) {

        this.mContext = context;
        this.M_USER_ID = userId;

        initGCM();
    }

    public static void insertMappingTables() {

        if (M_REGIST_ID != null && !M_REGIST_ID.equals("")) {
            Communicator.getHttp("http://dlswp113.dothome.co.kr/kusitms13/gcm_tr.php?tr=1&user_id=" + M_USER_ID + "&regist_id=" + M_REGIST_ID, new Handler() {
                public void handleMessage(Message msg) {
                    String jsonString = msg.getData().getString("jsonString");
                    if (jsonString.equals("SUCCESS")) {
                        Log.i("sf", "9");
                        //call your method
                    }
                }
            });
        }
    }

    public static void sendUserPush(String id, String title, String contents) {
        try {
            Log.i("tf", "8");
            Communicator.getHttp("http://dlswp113.dothome.co.kr/kusitms13/gcm_tr.php?tr=2&user_id=" + id + "&send_title=" + URLEncoder.encode(title, "UTF-8") + "&send_contents=" + URLEncoder.encode(contents, "UTF-8"), new Handler() {
                public void handleMessage(Message msg) {
                    Log.i("tf", "6");
                    String jsonString = msg.getData().getString("jsonString");
                    if (jsonString.equals("SUCCESS")) {
                        Log.i("tf", "9");
                        //call your method
                    }
                }
            });
        } catch (UnsupportedEncodingException e) {
            Log.i("tf", "7");
            e.printStackTrace();

        }
    }

    public void initGCM() {

        // init
        GCMRegistrar.checkDevice(mContext);
        GCMRegistrar.checkManifest(mContext);

        M_REGIST_ID = GCMRegistrar.getRegistrationId(mContext);

        //check RegistId (insert or update)
        if (M_REGIST_ID.equals("")) {
            // insert : GCMIntentService.onRegistered() call;
            GCMRegistrar.register(mContext, PROJECT_ID);
        } else {
            // update
             GCMManager.insertMappingTables();
        }
    }
}
