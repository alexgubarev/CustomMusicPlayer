package ru.startandroid.custommusicplayer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MusicService extends Service {


    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MusicManager.getInstance().initalizeMediaPlayer(this, R.raw.sephira);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MusicManager.getInstance().startPlaying();
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
