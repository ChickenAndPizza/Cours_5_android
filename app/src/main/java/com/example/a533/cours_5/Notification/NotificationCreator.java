package com.example.a533.cours_5.Notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.a533.cours_5.MainActivity;
import com.example.a533.cours_5.Notification.Model.ImportantMessageModel;
import com.example.a533.cours_5.Notification.Model.MessageModel;
import com.example.a533.cours_5.R;

public class NotificationCreator {
    public static Notification CreateNotificationForMessage(Context context, MessageModel message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "42")
                .setSmallIcon(R.drawable.logo_star)
                .setContentTitle(message.getSender())
                .setContentText(message.getMessage())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        return builder.build();
    }

    public static Notification CreateNotificationForImportantMessage(Context context, ImportantMessageModel message){
        Intent importantintent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0, importantintent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "43")
                .setSmallIcon(R.drawable.logo_star)
                .setContentTitle(message.getSender())
                .setContentText(message.getMessage())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.logo_star, "Marquer comme lu", pendingIntent);
        return builder.build();
    }
}
