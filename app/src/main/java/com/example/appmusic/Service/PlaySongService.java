package com.example.appmusic.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.example.appmusic.Objects.Song;

import java.util.ArrayList;

public class PlaySongService extends Service {

    private final IBinder musicBind = new PlaySongBinder();
    public int mPosition;
    public MediaPlayer mediaPlayer;
    ArrayList<Song> arraySong;
    private String songTitle = "";
    private static final int NOTIFY_ID = 1;

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
        if (mPosition == arraySong.size() - 1) mPosition = 0;
        else mPosition++;
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

}
