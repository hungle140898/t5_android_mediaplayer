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
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.NotificationCompat;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import com.example.appmusic.Model.Song;
import com.example.appmusic.R;

public class PlaySongActivity extends AppCompatActivity {

    public final String ACTION_NOTIFICATION_BUTTON_CLICK = "btnClick";
    public final String EXTRA_BUTTON_CLICKED = "data";
    private static final String CHANNEL_ID = "TEST_CHANNEL";
    TextView txtTotalTime, txtCurrentTime, txtSongName, txtSingerName;
    ImageView imgSong;
    ImageButton btnReplay, btnPrev, btnNext, btnPlay, btnShuffle;
    SeekBar sbSong;
    ArrayList<Song> arraySong;
    int mPosition = 0;
    MediaPlayer mediaPlayer;
    boolean isReplay = false, isReplayOne = false, isShuffle = false;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playsong);

        mapping();
        addSong();

        animation = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);

        createMediaPlayer();
        createNotificationChannel();
        registerReceiver(receiver,new IntentFilter(
                ACTION_NOTIFICATION_BUTTON_CLICK));

        sbSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(sbSong.getProgress());
            }
        });
    }

    private void addSong() {
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Bom(나만 봄)", "Bolbbalgan4", R.raw.bom));
        arraySong.add(new Song("Một đêm say", "Thịnh Suy", R.raw.mot_dem_say));
        arraySong.add(new Song("comethru", "Jeremy Zucker", R.raw.comethru));
        arraySong.add(new Song("Lạc Trôi", "Sơn Tùng M-TP", R.raw.lac_troi));
        arraySong.add(new Song("Love the way you lie ft. Rihanna", "Eminem", R.raw.love_the_way_you_lie));
        arraySong.add(new Song("Nơi này có anh", "Sơn Tùng M-TP", R.raw.noi_nay_co_anh));
        arraySong.add(new Song("Older", "Sasha Sloan", R.raw.older));
    }


    private void mapping() {
        txtCurrentTime = findViewById(R.id.txtThoiGianHienTai);
        txtTotalTime = findViewById(R.id.txtTongThoiGian);
        txtSingerName = findViewById(R.id.txtTenCaSi);
        txtSongName = findViewById(R.id.txtTenBaiHat);
        imgSong = findViewById(R.id.imgAnhBaiHat);
        btnPlay = findViewById(R.id.btnPlay);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrevious);
        btnReplay = findViewById(R.id.btnLapLai);
        btnShuffle = findViewById(R.id.btnNgauNhien);
        sbSong = findViewById(R.id.sbBaiHat);
    }

    private void createMediaPlayer() {
        mediaPlayer = MediaPlayer.create(PlaySongActivity.this, arraySong.get(mPosition).getFile());
        txtSongName.setText(arraySong.get(mPosition).getTitle());
        txtSingerName.setText(arraySong.get(mPosition).getSinger());
    }

    private void setTotalTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        txtTotalTime.setText(dateFormat.format(mediaPlayer.getDuration()));
        sbSong.setMax(mediaPlayer.getDuration());
    }

    private void updateCurrentTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
                txtCurrentTime.setText(dateFormat.format(mediaPlayer.getCurrentPosition()));
                sbSong.setProgress(mediaPlayer.getCurrentPosition());
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
            imgSong.clearAnimation();
        } else {
            mediaPlayer.start();
            btnPlay.setImageResource(R.drawable.pause);
            imgSong.startAnimation(animation);
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

        notificationLayout.setTextViewText(R.id.tenbaihat,txtSongName.getText());
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
}
