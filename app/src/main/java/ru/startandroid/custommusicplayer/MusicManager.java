package ru.startandroid.custommusicplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.PowerManager;

public class MusicManager {

    public MediaPlayer player;
    private static final MusicManager ourInstance = new MusicManager();

    public static MusicManager getInstance() {
        return ourInstance;
    }

    private MusicManager() {
    }

    public void initalizeMediaPlayer(Context context, int musicId) {
        player = MediaPlayer.create(context, musicId);
        player.setWakeMode(context,
                PowerManager.PARTIAL_WAKE_LOCK);

    }

    public void startPlaying() {
        if (player != null) {
            player.start();
        }

    }

    public void stopPlaying() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }

    }

    public void pausePlaying() {
        if (player != null) {
            player.pause();
        }
    }
}
