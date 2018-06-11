package ru.startandroid.custommusicplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.PowerManager;

public class MusicManager implements Constants {

    public MediaPlayer player;
    private static final MusicManager ourInstance = new MusicManager();

    public static MusicManager getInstance() {
        return ourInstance;
    }

    private MusicManager() {
    }

    public static boolean isPlayerInstanceLive() {
        return MusicManager.getInstance().player != null;
    }

    public static boolean isPlaying() {
        return MusicManager.getInstance().player.isPlaying();
    }

    public void initalizeMediaPlayer(final Context context, int musicId) {
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
