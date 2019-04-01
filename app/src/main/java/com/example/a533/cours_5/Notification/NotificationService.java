package com.example.a533.cours_5.Notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.example.a533.cours_5.MainActivity;
import com.example.a533.cours_5.Notification.Model.ImportantMessageModel;
import com.example.a533.cours_5.Notification.Model.MessageModel;
import com.example.a533.cours_5.R;
import com.google.common.collect.Iterables;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collection;
import java.util.Iterator;

import static com.google.firebase.firestore.DocumentChange.Type.ADDED;

public class NotificationService extends Service {
    public static final String CHANNEL_ID = "NotificationService";
    NotificationManager notificationManager;
    FirebaseFirestore database;
    int idNotification = 2;

    @Override
    public void onCreate(){
        CreateNotificationChannel();
        database = FirebaseFirestore.getInstance();
        ListenForNotificationMessage();
        ListenForNotificationImportantMessage();
        super.onCreate();
    }

    private void CreateNotificationChannel() {
        CreateNotificationChannelService();
        CreateNotificationChannelMessage();
        CreateNotificationChannelImportantMessage();
    }

    private void CreateNotificationChannelService(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId = CHANNEL_ID;
            CharSequence channelName = "NotificationService";
            String channelDescription = "Notification service";
            int channelImportance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, channelImportance);
            channel.setDescription(channelDescription);
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void CreateNotificationChannelMessage(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId = "42";
            CharSequence channelName = "NotificationMessage";
            String channelDescription = "Notification de message";
            int channelImportance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, channelImportance);
            channel.setDescription(channelDescription);
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void CreateNotificationChannelImportantMessage(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId = "43";
            CharSequence channelName = "NotificationImportantMessage";
            String channelDescription = "Notification de message important";
            int channelImportance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, channelImportance);
            channel.setDescription(channelDescription);
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationServiceIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationServiceIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_star)
                .setContentTitle("Notification service")
                .setContentIntent(pendingIntent);
        Notification notification = builder.build();
        startForeground(1, notification);
        return START_STICKY;
    }

    private void ListenForNotificationMessage(){
        database.collection("Notification").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                    for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                        switch (dc.getType()) {
                            case ADDED:
                                MessageModel messageModel = dc.getDocument().toObject(MessageModel.class);
                                SendNotificationForMessage(messageModel);
                                break;
                        }
                    }
            }
        });
    }

    private void ListenForNotificationImportantMessage(){
        database.collection("ImportantMessage").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            ImportantMessageModel messageModel = dc.getDocument().toObject(ImportantMessageModel.class);
                            SendNotificationForImportantMessage(messageModel);
                            break;
                    }
                }
            }
        });
    }

    private void SendNotificationForMessage(MessageModel messageModel){
        Notification notification = NotificationCreator.CreateNotificationForMessage(this, messageModel);
        notificationManager.notify(idNotification, notification);
        idNotification++;
    }

    private void SendNotificationForImportantMessage(ImportantMessageModel messageModel){
        Notification notification = NotificationCreator.CreateNotificationForImportantMessage(this, messageModel);
        notificationManager.notify(idNotification, notification);
        idNotification++;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
