package ru.startandroid.custommusicplayer;

import android.app.ActionBar;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RemoteViews;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements Constants, View.OnClickListener {

    ImageButton playPauseButton;
    ImageButton stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playPauseButton = (ImageButton) findViewById(R.id.playPauseButton);
        stopButton = (ImageButton) findViewById(R.id.stopButton);
        playPauseButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);

        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MusicManager.getInstance().player != null) {
            if (MusicManager.getInstance().player.isPlaying()) {
                playPauseButton.setActivated(true);
                playPauseButton.setImageResource(R.drawable.pause);
            } else {
                playPauseButton.setActivated(false);
                playPauseButton.setImageResource(R.drawable.play);
            }
        } else {
            playPauseButton.setActivated(false);
            playPauseButton.setImageResource(R.drawable.play);
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        StatusBarNotification[] notifications = Objects.requireNonNull(notificationManager).getActiveNotifications();
        for (StatusBarNotification notification : notifications) {
            if (notification.getId() == 1) {
                notificationManager.cancelAll();
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (MusicManager.getInstance().player != null) {
            if (MusicManager.getInstance().player.isPlaying()) {
                RemoteViews remoteViews = new RemoteViews(getPackageName(),
                        R.layout.custom_notification);
                remoteViews.setImageViewResource(R.id.imageView, R.drawable.music);
                remoteViews.setImageViewResource(R.id.stopBtnNotif, R.drawable.stop);
                remoteViews.setImageViewResource(R.id.pauseBtnNotif, R.drawable.pause);


                Intent notificationIntent = new Intent(this, MainActivity.class);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                        notificationIntent, 0);

                Intent stopIntent = new Intent(this, MusicService.class);
                stopIntent.setAction(Constants.STOP);
                PendingIntent stopPendingIntent = PendingIntent.getService(this, 0, stopIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);


                Intent playIntent = new Intent(this, MusicService.class);
                playIntent.setAction(Constants.PLAY);
                PendingIntent playPendingIntent = PendingIntent.getService(this, 0, playIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                remoteViews.setOnClickPendingIntent(R.id.stopBtnNotif, stopPendingIntent);


                remoteViews.setOnClickPendingIntent(R.id.pauseBtnNotif, playPendingIntent);
                remoteViews.setImageViewResource(R.id.pauseBtnNotif, R.drawable.pause);

                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(this)
                                .setContent(remoteViews)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentIntent(pendingIntent);

                Notification notification = builder.build();

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Objects.requireNonNull(notificationManager).notify(1, notification);
            }
        }


    }

    public void play() {
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(PLAY);
        startService(intent);
    }

    public void pause() {
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(PLAY);
        startService(intent);
    }


    public void stop() {
        Intent intent = new Intent(this, MusicService.class);
        intent.setAction(STOP);
        startService(intent);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.playPauseButton:
                if (!playPauseButton.isActivated()) {
                    play();
                    playPauseButton.setActivated(true);
                    playPauseButton.setImageResource(R.drawable.pause);
                } else {
                    pause();
                    playPauseButton.setActivated(false);
                    playPauseButton.setImageResource(R.drawable.play);
                }
                break;
            case R.id.stopButton:
                stop();
                playPauseButton.setActivated(false);
                playPauseButton.setImageResource(R.drawable.play);
                break;
        }
    }
}


