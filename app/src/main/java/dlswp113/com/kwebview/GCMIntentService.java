package dlswp113.com.kwebview;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;


/**
 * Created by shcho on 11/15/2015.
 */
/*
public class GCMIntentService extends GCMBaseIntentService {

    public GCMIntentService() {
        super(GCMManager.PROJECT_ID);
    }

    private static void generateNotification(Context context, String message) {
        int icon = R.drawable.ic_action_search;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        //Notification.Builder notification = new  Notification.Builder.addAction(icon, message, when);
        Notification.Builder notification = new  Notification.Builder();

        String title = context.getString(R.string.app_name);
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);
        notification.addAction(icon, message, intent);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);
    }
    @Override
    protected void onError(Context arg0, String arg1) {
    }
    @Override
    protected void onMessage(Context context, Intent intent) {
// 메세지를 받으면 이곳에서 처리
        String msg = intent.getStringExtra("message");
        Log.e("getmessage", "getmessage:" + msg);
        generateNotification(context, "push");
    }
    @Override
    protected void onRegistered(Context context, String reg_id) {
        Log.e("키등록", reg_id);
    }
    @Override
    protected void onUnregistered(Context arg0, String arg1) {
        Log.e("키제거", "---");
    }
}*/

public class GCMIntentService extends GCMBaseIntentService {

    public GCMIntentService() {
        super(GCMManager.PROJECT_ID);
    }

    @Override
    protected void onRegistered(Context arg0, String arg1) {
        GCMManager.M_REGIST_ID = arg1;
        GCMManager.insertMappingTables();
    }

    @Override
    protected void onUnregistered(Context arg0, String arg1) {

    }

    @Override
    protected void onMessage(Context arg0, Intent intent) {
        try {
            if (intent.getAction().equals(
                    "com.google.android.c2dm.intent.RECEIVE")) {
                String title = intent.getExtras().getString("title");
                String contents = intent.getExtras().getString("contents");
                sendNotification(title, contents);
            }
        } catch (Exception e) {
        }
    }


    @Override
    protected void onError(Context arg0, String arg1) {
    }


    @SuppressWarnings("deprecation")
    private void sendNotification(String args0, String args1) {

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mCompatBuilder = new NotificationCompat.Builder(this);
        mCompatBuilder.setSmallIcon(R.drawable.ic_small);
        mCompatBuilder.setTicker("큐시즘 공지");
        mCompatBuilder.setWhen(System.currentTimeMillis());
        mCompatBuilder.setNumber(1);
        mCompatBuilder.setContentTitle(args0);
        mCompatBuilder.setContentText(args1);
        mCompatBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        mCompatBuilder.setContentIntent(pendingIntent);
        mCompatBuilder.setAutoCancel(true);

        nm.notify(222, mCompatBuilder.build());
    }
}
