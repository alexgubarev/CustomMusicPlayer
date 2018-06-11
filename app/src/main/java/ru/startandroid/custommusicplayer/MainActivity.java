package ru.startandroid.custommusicplayer;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


import java.util.Objects;

public class MainActivity extends AppCompatActivity implements Constants, View.OnClickListener {

    ImageButton playPauseButton;
    ImageButton stopButton;
    Intent intent;
    BroadcastReceiver rec;


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

        if (!MusicManager.isPlayerInstanceLive()) {
            MusicManager.getInstance().initializeMediaPlayer(this, R.raw.music);
            onCompletionMethod();
        } else onCompletionMethod();

        intent = new Intent(this, MusicService.class);


        rec = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (MusicManager.isPlayerInstanceLive()) {
                    if (MusicManager.isPlaying()) {
                        setButtonInPlayState(false);
                    } else {
                        setButtonInPlayState(true);
                    }
                } else setButtonInPlayState(true);

            }
        };

        IntentFilter intentFilter = new IntentFilter(SET_BUTTON);
        registerReceiver(rec, intentFilter);
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (MusicManager.isPlayerInstanceLive()) {

            onCompletionMethod();
            if (MusicManager.isPlaying()) {
                setButtonInPlayState(false);
            } else {
                setButtonInPlayState(true);
            }
        } else {
            MusicManager.getInstance().initializeMediaPlayer(this, R.raw.music);
            onCompletionMethod();
            setButtonInPlayState(true);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(rec);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (MusicManager.isPlayerInstanceLive()) {
            if (MusicManager.isPlaying()) {
                NotificationClass.createNotification(this, true);
            }
        }


    }


    private void onCompletionMethod() {
        MusicManager.getInstance().player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                intent.setAction(STOP);
                startService(intent);
                setButtonInPlayState(true);


            }
        });
    }

    private void setButtonInPlayState(boolean setPlayState) {
        if (setPlayState) {
            playPauseButton.setActivated(false);
            playPauseButton.setImageResource(R.drawable.play);
        } else {
            playPauseButton.setActivated(true);
            playPauseButton.setImageResource(R.drawable.pause);
        }
    }


    public void play() {
        intent.setAction(PLAY);
        startService(intent);
    }


    public void stop() {
        intent.setAction(STOP);
        startService(intent);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.playPauseButton:
                if (!playPauseButton.isActivated()) {
                    play();
                    setButtonInPlayState(false);
                } else {
                    play();
                    setButtonInPlayState(true);
                }
                break;
            case R.id.stopButton:
                if (MusicManager.isPlaying()) {
                    stop();
                    setButtonInPlayState(true);
                    break;
                }

        }
    }
}


