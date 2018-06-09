package ru.startandroid.custommusicplayer;

import android.app.Service;
import android.content.Intent;
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

        switch (Objects.requireNonNull(intent.getAction())) {
            case PLAY:
                if (MusicManager.getInstance().player == null) {
                    MusicManager.getInstance().initalizeMediaPlayer(this, R.raw.sephira);
                }
                MusicManager.getInstance().startPlaying();
                break;
            case STOP:
                MusicManager.getInstance().stopPlaying();
                stopSelf();
                break;
            case PAUSE:
                MusicManager.getInstance().pausePlaying();
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

