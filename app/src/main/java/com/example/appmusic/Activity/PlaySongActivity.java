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

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.appmusic.Adapter.ViewPagerAdapter;
import com.example.appmusic.Fragment.SongDetailsFragment;
import com.example.appmusic.Fragment.SongsListFragment;
import com.example.appmusic.Model.Song;
import com.example.appmusic.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;


public class PlaySongActivity extends AppCompatActivity {

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

        viewPager.setTag(-16110110, arraySong);
        viewPager.setTag(-16110111, mPosition);
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
        viewPager = findViewById(R.id.viewPager);
        btnPlay = findViewById(R.id.btnPlay);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrevious);
        btnReplay = findViewById(R.id.btnLapLai);
        btnShuffle = findViewById(R.id.btnNgauNhien);
        sliderDotspanel = findViewById(R.id.SliderDots);
    }

    public void createMediaPlayer() {
        mediaPlayer = MediaPlayer.create(PlaySongActivity.this, arraySong.get(mPosition).getFile());
        viewPager.setTag(-16110400, arraySong.get(mPosition).getTitle());
        viewPager.setTag(-16110401, arraySong.get(mPosition).getSinger());
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
            //imgSong.clearAnimation();
        } else {
            mediaPlayer.start();
            btnPlay.setImageResource(R.drawable.pause);
            //imgSong.startAnimation(animation);
        }
        setTotalTime();
        updateCurrentTime();
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
}
