package com.back_ground_operations.utils;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.IOException;

public class MyService extends Service {

    private MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AssetFileDescriptor path = null;
        player = new MediaPlayer();
        try {
            path = getApplicationContext().getResources().getAssets().openFd("enrique_iglesias.mp3");
            player.setDataSource(path.getFileDescriptor(), path.getStartOffset(), path.getLength());
            path.close();
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }
}