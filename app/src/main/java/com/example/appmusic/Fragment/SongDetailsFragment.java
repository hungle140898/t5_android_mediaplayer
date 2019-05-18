package com.example.appmusic.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.appmusic.Activity.PlaySongActivity;
import com.example.appmusic.R;

import java.text.SimpleDateFormat;


public class SongDetailsFragment extends Fragment{
    TextView txtTotalTime, txtCurrentTime, txtSongName, txtSingerName;
    ImageView img;
    SeekBar sbSong;
    Animation animation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_details, container, false);

        txtTotalTime = view.findViewById(R.id.txtTongThoiGian);
        txtCurrentTime =  view.findViewById(R.id.txtThoiGianHienTai);
        txtSongName = view.findViewById(R.id.txtTenBaiHat);
        txtSingerName = view.findViewById(R.id.txtTenCaSi);
        sbSong = view.findViewById(R.id.sbBaiHat);
        img = view.findViewById(R.id.imgAnhBaiHat);
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.disc_rotate);
        Handler progressBarHandler = new Handler();
        String isPlay = "0";

        txtSongName.setText(container.getTag(-16110400).toString());
        txtSingerName.setText(container.getTag(-16110401).toString());
        txtTotalTime.setText(container.getTag(-16110402).toString());
        sbSong.setMax(Integer.parseInt(container.getTag(-16110403).toString()));
        if(container.getTag(-16110404)!= null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
            txtCurrentTime.setText(dateFormat.format(((PlaySongActivity)getActivity()).mediaPlayer.getCurrentPosition()));
            progressBarHandler .post(new Runnable() {

                public void run() {
                    sbSong.setProgress(((PlaySongActivity)getActivity()).mediaPlayer.getCurrentPosition());
                }
            });
        }
        sbSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ((PlaySongActivity)getActivity()).mediaPlayer.seekTo(sbSong.getProgress());
            }
        });
        return view;
    }
}