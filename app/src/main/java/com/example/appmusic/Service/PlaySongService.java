package com.example.appmusic.Service;

import android.app.Service;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.example.appmusic.Activity.MainActivity;
import com.example.appmusic.Objects.Song;
import com.example.appmusic.R;

import java.util.ArrayList;
import java.util.Random;

public class PlaySongService extends Service{

    private final IBinder musicBind = new PlaySongBinder();
    public int mPosition;
    public MediaPlayer mediaPlayer;
    ArrayList<Song> arraySong;
    private String songTitle = "";
    private boolean shuffle = false;
    private Random rand;

    public class PlaySongBinder extends Binder {

        public PlaySongService getService() {
            return PlaySongService.this;
        }

    }

    public PlaySongService() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        stopForeground(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPosition = 0;
        mediaPlayer = new MediaPlayer();
        rand = new Random();
    }

    public void setList(ArrayList<Song> Songs) {
        arraySong = Songs;
    }

    public void playSong() {
        mediaPlayer.pause();
        Song playSong = arraySong.get(mPosition);
        songTitle = playSong.getTenBaiHat();

        int currSong = getRawResIdByName(playSong.getLink());
        mediaPlayer = MediaPlayer.create(getApplicationContext(), currSong);
    }

    public void playPrev() {

        if (mPosition == 0) mPosition = arraySong.size() - 1;
        else mPosition--;
        playSong();
        go();
    }

    public void playNext() {
        if (shuffle) {
            int newSong = mPosition;
            while (newSong == mPosition) {
                newSong = rand.nextInt(arraySong.size());
            }
            mPosition = newSong;
        } else {
            if (mPosition == arraySong.size() - 1) mPosition = 0;
            else mPosition++;
        }
        playSong();
        go();
    }

    public void setSong(int songIndex) {
        mPosition = songIndex;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    public int getRawResIdByName(String resName) {
        String pkgName = this.getPackageName();
        // Return 0 if not found.
        // Trả về 0 nếu không tìm thấy.
        int resID = this.getResources().getIdentifier(resName, "raw", pkgName);
        return resID;
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public void pausePlayer() {
        mediaPlayer.pause();
    }

    public void seek(int posn) {
        mediaPlayer.seekTo(posn);
    }

    public void go() {
        mediaPlayer.start();
    }

    public void setShuffle() {
        if (shuffle) shuffle = false;
        else shuffle = true;
    }

}
