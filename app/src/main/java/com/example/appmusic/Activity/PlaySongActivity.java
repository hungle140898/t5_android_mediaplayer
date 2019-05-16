/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.appmusic.Activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.example.appmusic.Adapter.ViewPagerAdapter;
import com.example.appmusic.Fragment.SongDetailsFragment;
import com.example.appmusic.Fragment.SongsListFragment;
import com.example.appmusic.Objects.Song;
import com.example.appmusic.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import static com.example.appmusic.Activity.MainActivity.database;


public class PlaySongActivity extends AppCompatActivity {

    public final String ACTION_NOTIFICATION_BUTTON_CLICK = "btnClick";
    public final String EXTRA_BUTTON_CLICKED = "data";
    private static final String CHANNEL_ID = "TEST_CHANNEL";
    TextView txtSongName;
    ImageButton btnReplay, btnPrev, btnNext, btnPlay, btnShuffle;
    ArrayList<Song> arraySong;
    public int mPosition = 0;
    public MediaPlayer mediaPlayer;
    boolean isReplay = false, isReplayOne = false, isShuffle = false;
    Animation animation;
    LinearLayout sliderDotspanel;
    private ImageView[] dots;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playsong);

        mapping();
        addSong();
        setLayout();
        createMediaPlayer();
        setTotalTime();
        createNotificationChannel();
        registerReceiver(receiver,new IntentFilter(
                ACTION_NOTIFICATION_BUTTON_CLICK));

        viewPager.setTag(-16110110, arraySong);
        viewPager.setTag(-16110111, mPosition);
    }

    private void addSong() {
        Intent intent = this.getIntent();
        int key = intent.getIntExtra("key", 0);
        int id = intent.getIntExtra("id", 0);
        arraySong = new ArrayList<>();
        switch (key){
            case 1: {
                getAllSong();
                break;
            }
            case 2:{
                getAllSongInPlaylist(id);
                break;
            }
            case 3:{
                getAllSongBySinger(id);
                break;
            }
            case 4:{
                getAllSongInAlbum(id);
                break;
            }
            case 5:{
                getAllSongOfType(id);
                break;
            }
        }
    }

    private void mapping() {
        viewPager = findViewById(R.id.viewPager);
        btnPlay = findViewById(R.id.btnPlay);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrevious);
        btnReplay = findViewById(R.id.btnLapLai);
        btnShuffle = findViewById(R.id.btnNgauNhien);
        sliderDotspanel = findViewById(R.id.SliderDots);

    }

    public void createMediaPlayer() {
        mediaPlayer = MediaPlayer.create(PlaySongActivity.this, getRawResIdByName(arraySong.get(mPosition).getLink()));
        viewPager.setTag(-16110400, arraySong.get(mPosition).getTenBaiHat());
        viewPager.setTag(-16110401, arraySong.get(mPosition).getTenCaSi());
    }

    public void setTotalTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        viewPager.setTag(-16110402, dateFormat.format(mediaPlayer.getDuration()));
        viewPager.setTag(-16110403, mediaPlayer.getDuration());
    }

    private void updateCurrentTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                viewPager.setTag(-16110404, mediaPlayer.getCurrentPosition());
                viewPager.setTag(-16110111, mPosition);
                viewPager.getAdapter().notifyDataSetChanged();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (!isReplayOne)
                            mPosition++;
                        if (mPosition > arraySong.size() - 1) {
                            if(!isReplay) {
                                mediaPlayer.stop();
                                btnPlay.setImageResource(R.drawable.play);
                                return;
                            }else {
                                mPosition = 0;
                            }
                        }
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }
                        createMediaPlayer();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pause);
                        setTotalTime();
                        updateCurrentTime();
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 100);
    }

    public void onPlay(View v) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            btnPlay.setImageResource(R.drawable.play);

        } else {
            mediaPlayer.start();
            btnPlay.setImageResource(R.drawable.pause);

        }
        setTotalTime();
        updateCurrentTime();
        showNotification();
    }

    public void onNext(View v) {
        mPosition++;
        if (mPosition > arraySong.size() - 1) {
            mPosition = 0;
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        createMediaPlayer();
        mediaPlayer.start();
        btnPlay.setImageResource(R.drawable.pause);
        viewPager.setTag(-16110111, mPosition);
        viewPager.getAdapter().notifyDataSetChanged();
        setTotalTime();
        updateCurrentTime();
        showNotification();
    }

    public void onPrev(View v) {
        mPosition--;
        if (mPosition < 0) {
            mPosition = arraySong.size() - 1;
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        createMediaPlayer();
        mediaPlayer.start();
        btnPlay.setImageResource(R.drawable.pause);
        viewPager.setTag(-16110111, mPosition);
        viewPager.getAdapter().notifyDataSetChanged();
        setTotalTime();
        updateCurrentTime();
        showNotification();
    }

    public void onRepeat(View v) {
        if (!isReplay && !isReplayOne) {
            isReplay = true;
            btnReplay.setImageResource(R.drawable.repeat);
        } else if (isReplay && !isReplayOne) {
            isReplay = false;
            isReplayOne = true;
            btnReplay.setImageResource(R.drawable.repeat_one);
        } else {
            isReplayOne = false;
            isReplay = false;
            btnReplay.setImageResource(R.drawable.no_repeat);
        }
    }

    public void onShuffle(View v) {
        if (!isShuffle) {
            isShuffle = true;
            btnShuffle.setImageResource(R.drawable.shuffle);
            Collections.shuffle(arraySong);
        } else {
            isShuffle = false;
            btnShuffle.setImageResource(R.drawable.no_shuffle);
            addSong();
        }
    }
    private PendingIntent onButtonNotificationClick(@IdRes int id) {
        Intent intent = new Intent(ACTION_NOTIFICATION_BUTTON_CLICK);
        intent.putExtra(EXTRA_BUTTON_CLICKED, id);
        return PendingIntent.getBroadcast(this, id, intent, 0);
    }

    private void showNotification() {

        RemoteViews notificationLayout =
                new RemoteViews(getPackageName(), R.layout.notification_custom);

        notificationLayout.setTextViewText(R.id.tenbaihat, arraySong.get(mPosition).getTenBaiHat());
        if(mediaPlayer.isPlaying()){
            notificationLayout.setImageViewResource(R.id.btnPlay_noti,R.drawable.pause);
        }
        else {
            notificationLayout.setImageViewResource(R.id.btnPlay_noti,R.drawable.play);
        }

        notificationLayout.setOnClickPendingIntent(R.id.btnPre_noti,
                onButtonNotificationClick(R.id.btnPre_noti));
        notificationLayout.setOnClickPendingIntent(R.id.btnPlay_noti,
                onButtonNotificationClick(R.id.btnPlay_noti));
        notificationLayout.setOnClickPendingIntent(R.id.btnNext_noti,
                onButtonNotificationClick(R.id.btnNext_noti));

        Notification
                notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCustomContentView(notificationLayout)
                .build();
        NotificationManager notificationManager =
                (android.app.NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent) {
            int id = intent.getIntExtra(EXTRA_BUTTON_CLICKED, -1);
            switch (id) {
                case R.id.btnPre_noti:
                    onPrev(btnPrev);
                    updateCurrentTime();
                    break;
                case R.id.btnPlay_noti:
                    onPlay(btnPlay);
                    break;
                case R.id.btnNext_noti:
                    onNext(btnNext);
                    updateCurrentTime();
                    break;
            }
        }
    };

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "abc";
            String description = "def";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void setLayout(){

        dots = new ImageView[2];
        for (int i = 0; i < 2; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);
        }
        dots[1].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new SongsListFragment());
        viewPagerAdapter.addFragment(new SongDetailsFragment());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(1);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i< 2; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        animation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);
    }

    public void getAllSong(){
        Cursor data = database.getData("SELECT BaiHat.IDBaiHat,BaiHat.TenBaiHat,CaSi.TenCaSi,Album.HinhAlbum, BaiHat.LinkBaiHat FROM BaiHat,CaSi,Album WHERE BaiHat.IDCaSi=CaSi.IDCaSi AND BaiHat.IDAlbum=Album.IDAlbum");
        arraySong.clear();
        while (data.moveToNext())
        {
            com.example.appmusic.Objects.Song song = new com.example.appmusic.Objects.Song();
            song.setIDBaiHat(data.getInt(0));
            song.setTenBaiHat(data.getString(1));
            song.setTenCaSi(data.getString(2));
            song.setHinh(data.getString(3));
            song.setLink(data.getString(4));
            arraySong.add(song);
        }
    }

    public void getAllSongInPlaylist(int id){
        Cursor data = database.getData("SELECT BaiHat.IDBaiHat,BaiHat.TenBaiHat,CaSi.TenCaSi,BaiHat.LinkBaiHat FROM Playlist_BaiHat INNER JOIN BaiHat ON Playlist_BaiHat.IDBaiHat=BaiHat.IDBaiHat INNER JOIN CaSi ON CaSi.IDCaSi=BaiHat.IDCaSi WHERE Playlist_BaiHat.IDPlayList=" + id);
        arraySong.clear();
        while (data.moveToNext())
        {
            com.example.appmusic.Objects.Song song = new com.example.appmusic.Objects.Song();
            song.setIDBaiHat(data.getInt(0));
            song.setTenBaiHat(data.getString(1));
            song.setTenCaSi(data.getString(2));
            song.setHinh(data.getString(3));
            song.setLink(data.getString(4));
            arraySong.add(song);
        }
    }

    public void getAllSongBySinger(int id){
        Cursor data = database.getData("SELECT BaiHat.IDBaiHat,BaiHat.TenBaiHat,CaSi.TenCaSi,BaiHat.LinkBaiHat FROM BaiHat INNER JOIN CaSi ON BaiHat.IDCaSi=CaSi.IDCaSi WHERE CaSi.IDCaSi=" + id);
        arraySong.clear();
        while (data.moveToNext())
        {
            com.example.appmusic.Objects.Song song = new com.example.appmusic.Objects.Song();
            song.setIDBaiHat(data.getInt(0));
            song.setTenBaiHat(data.getString(1));
            song.setTenCaSi(data.getString(2));
            song.setLink(data.getString(3));
            arraySong.add(song);
        }
    }

    public void getAllSongInAlbum(int id){
        Cursor data = database.getData("SELECT BaiHat.IDBaiHat,BaiHat.TenBaiHat,CaSi.TenCaSi,BaiHat.LinkBaiHat  FROM BaiHat INNER JOIN Album ON BaiHat.IDAlbum=Album.IDAlbum INNER JOIN CaSi ON CaSi.IDCaSi=BaiHat.IDCaSi WHERE Album.IDAlbum=" + id);
        arraySong.clear();
        while (data.moveToNext())
        {
            com.example.appmusic.Objects.Song song = new com.example.appmusic.Objects.Song();
            song.setIDBaiHat(data.getInt(0));
            song.setTenBaiHat(data.getString(1));
            song.setTenCaSi(data.getString(2));
            song.setLink(data.getString(3));
            arraySong.add(song);
        }
    }

    public void getAllSongOfType(int id){
        Cursor data = database.getData("SELECT BaiHat.IDBaiHat,BaiHat.TenBaiHat,CaSi.TenCaSi,BaiHat.LinkBaiHat  FROM BaiHat INNER JOIN TheLoai ON BaiHat.IDTheLoai =TheLoai.IDTheLoai INNER JOIN CaSi ON CaSi.IDCaSi=BaiHat.IDCaSi WHERE TheLoai.IDTheLoai=" + id);
        arraySong.clear();
        while (data.moveToNext())
        {
            com.example.appmusic.Objects.Song song = new com.example.appmusic.Objects.Song();
            song.setIDBaiHat(data.getInt(0));
            song.setTenBaiHat(data.getString(1));
            song.setTenCaSi(data.getString(2));
            song.setLink(data.getString(3));
            arraySong.add(song);
        }
    }

    public int getRawResIdByName(String resName)  {
        String pkgName = this.getPackageName();
        // Return 0 if not found.
        // Trả về 0 nếu không tìm thấy.
        int resID = this.getResources().getIdentifier(resName, "raw", pkgName);
        return resID;
    }
}
