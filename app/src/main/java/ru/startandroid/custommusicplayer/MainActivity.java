package ru.startandroid.custommusicplayer;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void play (View v){
        Intent intent = new Intent(this, MusicService.class);
        startService(intent);

    }

    public void pause (View v){
        if (MusicManager.getInstance() != null)
            MusicManager.getInstance().pausePlaying();


    }


    public void stop (View v){
        Intent intent = new Intent(this, MusicService.class);
        stopService(intent);


    }



}
