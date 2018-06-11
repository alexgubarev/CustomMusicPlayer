package ru.startandroid.custommusicplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import java.util.Objects;

public class MusicService extends Service implements Constants {


    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        switch (Objects.requireNonNull(intent.getAction())) {
            case PLAY:
                if (MusicManager.getInstance().player == null) {
                    MusicManager.getInstance().initalizeMediaPlayer(this, R.raw.music);
                    MusicManager.getInstance().player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            Intent intent = new Intent(getApplicationContext(), MusicService.class);
                            intent.setAction(STOP);
                            startService(intent);
                        }
                    });
                }
                RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(),
                        R.layout.custom_notification);
                if (MusicManager.getInstance().player.isPlaying()) {
                    MusicManager.getInstance().pausePlaying();
                    remoteViews.setImageViewResource(R.id.pauseBtnNotif, R.drawable.play);
                    remoteViews.setImageViewResource(R.id.imageView, R.drawable.music);
                    remoteViews.setImageViewResource(R.id.stopBtnNotif, R.drawable.stop);
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
                } else {
                    MusicManager.getInstance().startPlaying();
                    remoteViews.setImageViewResource(R.id.pauseBtnNotif, R.drawable.pause);
                    remoteViews.setImageViewResource(R.id.imageView, R.drawable.music);
                    remoteViews.setImageViewResource(R.id.stopBtnNotif, R.drawable.stop);
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
                }

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                StatusBarNotification[] notifications = Objects.requireNonNull(notificationManager).getActiveNotifications();
                for (StatusBarNotification notification : notifications) {
                    if (notification.getId() == 1) {
                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(this)
                                        .setContent(remoteViews)
                                        .setSmallIcon(R.mipmap.ic_launcher);

                        Notification mynotification = builder.build();


                        Objects.requireNonNull(notificationManager).notify(1, mynotification);
                    }
                }


                break;
            case STOP:
                MusicManager.getInstance().stopPlaying();
                RemoteViews myRemoteViews = new RemoteViews(getApplicationContext().getPackageName(),
                        R.layout.custom_notification);
                myRemoteViews.setImageViewResource(R.id.pauseBtnNotif, R.drawable.play);
                myRemoteViews.setImageViewResource(R.id.imageView, R.drawable.music);
                myRemoteViews.setImageViewResource(R.id.stopBtnNotif, R.drawable.stop);


                Intent stopIntent = new Intent(this, MusicService.class);
                stopIntent.setAction(Constants.STOP);
                PendingIntent stopPendingIntent = PendingIntent.getService(this, 0, stopIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);


                Intent playIntent = new Intent(this, MusicService.class);
                playIntent.setAction(Constants.PLAY);
                PendingIntent playPendingIntent = PendingIntent.getService(this, 0, playIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                myRemoteViews.setOnClickPendingIntent(R.id.stopBtnNotif, stopPendingIntent);


                myRemoteViews.setOnClickPendingIntent(R.id.pauseBtnNotif, playPendingIntent);

                NotificationManager notificationManager1 =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                StatusBarNotification[] notifications1 = Objects.requireNonNull(notificationManager1).getActiveNotifications();
                for (StatusBarNotification notification : notifications1) {
                    if (notification.getId() == 1) {
                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(this)
                                        .setContent(myRemoteViews)
                                        .setSmallIcon(R.mipmap.ic_launcher);

                        Notification mynotification = builder.build();


                        Objects.requireNonNull(notificationManager1).notify(1, mynotification);
                    }
                }
                stopSelf();
                break;
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        MusicManager.getInstance().stopPlaying();
    }

    @Override
    public void onLowMemory() {
    }
}

