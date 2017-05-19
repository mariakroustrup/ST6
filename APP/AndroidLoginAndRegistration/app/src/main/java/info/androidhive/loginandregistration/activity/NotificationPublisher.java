package info.androidhive.loginandregistration.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class NotificationPublisher extends BroadcastReceiver {
    public static int NOTIFICATION_ID = 77;
    public static String NOTIFICATION = "notification";
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = NOTIFICATION_ID;
        notificationManager.notify(id, notification);
    }
}
