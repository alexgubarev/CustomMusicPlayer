package ru.startandroid.custommusicplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import java.util.Objects;

public class NotificationClass {

    public static void createNotification(Context context, boolean isPlayState) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.custom_notification);

        remoteViews.setImageViewResource(R.id.imageView, R.drawable.music);
        remoteViews.setImageViewResource(R.id.stopBtnNotif, R.drawable.stop);
        if (isPlayState) {
            remoteViews.setImageViewResource(R.id.pauseBtnNotif, R.drawable.pause);
        } else {
            remoteViews.setImageViewResource(R.id.pauseBtnNotif, R.drawable.play);
        }

        Intent stopIntent = new Intent(context, MusicService.class);
        stopIntent.setAction(Constants.STOP);
        PendingIntent stopPendingIntent = PendingIntent.getService(context, 0, stopIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Intent playIntent = new Intent(context, MusicService.class);
        playIntent.setAction(Constants.PLAY);
        PendingIntent playPendingIntent = PendingIntent.getService(context, 0, playIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews.setOnClickPendingIntent(R.id.stopBtnNotif, stopPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.pauseBtnNotif, playPendingIntent);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setContent(remoteViews)
                        .setSmallIcon(R.mipmap.ic_launcher);

        Notification notification = builder.build();
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).notify(1, notification);

    }

}
