package com.idris.wakelockdemo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private boolean isPlaying = false;
    private PowerManager.WakeLock wakeLock;
    Button playBtn, pauseBtn, stopBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        playBtn = findViewById(R.id.playBtn);
        pauseBtn = findViewById(R.id.pause);
        stopBtn = findViewById(R.id.stop);
        playMusic();
    }
    private void acquireWakeLock() {
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "WakeLockDemo:MyWakeLockTag");
        wakeLock.acquire();
    }
    public void playMusic(){
        acquireWakeLock();
        if (!isPlaying) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.rickroll);
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(mp -> {
                isPlaying = false;
                wakeLock.release();
            });
        }
    }
}
