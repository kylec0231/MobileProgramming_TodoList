package com.csce4623.ahnelson.todolist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "MyBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {

        String TodoTitle = intent.getStringExtra(DisplayTodolistUpdateActivity.Extra_Text_Title);
        String TodoContent = intent.getStringExtra(DisplayTodolistUpdateActivity.Extra_Text_Content);

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannel1Notification(TodoTitle, TodoContent);
        notificationHelper.getManager().notify(1, nb.build());


//
//        StringBuilder sb = new StringBuilder();
//        sb.append("Action: " + intent.getAction() + "\n");
//        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
//        String log = sb.toString();
//        Log.d(TAG, log);
//        Toast.makeText(context, log, Toast.LENGTH_LONG).show();
    }
}