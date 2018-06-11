package ru.startandroid.custommusicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

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

        Intent broadcastIntent = new Intent(SET_BUTTON);
        sendBroadcast(broadcastIntent);

        switch (Objects.requireNonNull(intent.getAction())) {
            case PLAY:
                if (!MusicManager.isPlayerInstanceLive()) {
                    MusicManager.getInstance().initializeMediaPlayer(this, R.raw.music);
                    MusicManager.getInstance().player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            Intent intent = new Intent(getApplicationContext(), MusicService.class);
                            intent.setAction(STOP);
                            startService(intent);

                        }
                    });
                }

                if (MusicManager.isPlaying()) {
                    MusicManager.getInstance().pausePlaying();
                    NotificationClass.createNotification(this, false);
                } else {
                    MusicManager.getInstance().startPlaying();
                    NotificationClass.createNotification(this, true);
                }
                break;
            case STOP:
                MusicManager.getInstance().stopPlaying();
                NotificationClass.createNotification(this, false);
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

