package ru.startandroid.custommusicplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements Constants {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onStop() {
        super.onStop();

        Intent resultIntent = new Intent(this, MusicService.class);
        resultIntent.setAction(Constants.STOP);
        PendingIntent resultPendingIntent = PendingIntent.getService(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Title")
                        .setContentText("Notification text")
                        .setContentIntent(resultPendingIntent);

        Notification notification = builder.build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    public void play(View v) {
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(PLAY);
        startService(intent);
    }

    public void pause(View v) {
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(PAUSE);
        startService(intent);
    }


    public void stop(View v) {
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(STOP);
        startService(intent);
    }


}
