package com.example.appmusic.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.appmusic.Activity.PlaySongActivity;
import com.example.appmusic.Objects.Song;
import com.example.appmusic.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PlaySongService extends Service{

    private final IBinder musicBind = new PlaySongBinder();
    public int mPosition;
    public MediaPlayer mediaPlayer;
    ArrayList<Song> arraySong;
    private  int TT=0;
    private String songTitle = "";
    private static final int NOTIFY_ID = 1;
    public final String ACTION_NOTIFICATION_BUTTON_CLICK = "btnClick";
    public final String EXTRA_BUTTON_CLICKED = "data";
    private static final String CHANNEL_ID = "TEST_CHANNEL";

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
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        createNotificationChannel();
        registerReceiver(receiver, new IntentFilter(
                ACTION_NOTIFICATION_BUTTON_CLICK));
    }

    public void setList(ArrayList<Song> Songs) {
        arraySong = Songs;
    }

    public void setTrangthai(int _TT) {
        TT = _TT;
    }

    public void playSong() {
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.pause();
        }


        Song playSong = arraySong.get(mPosition);
        songTitle = playSong.getTenBaiHat();

        if(TT==0){
            int currSong = getRawResIdByName(playSong.getLink());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), currSong);
        }
        else
        {
            String songLink=playSong.getLink();

            //mediaPlayer.setDataSource(songLink);
          /*  mediaPlayer.setOnPreparedListener(this.onPrepared(mediaPlayer));
            mediaPlayer.prepareAsync();*/

            new Player().execute(songLink);
        }


    }

    /*public void onPrepared(MediaPlayer player) {
        player.start();
    }*/

    public void playPrev() {
        if (mPosition == 0) mPosition = arraySong.size() - 1;
        else mPosition--;
        playSong();
        go();
        showNotification();
    }

    public void playNext() {
        if (mPosition == arraySong.size() - 1) mPosition = 0;
        else mPosition++;
        playSong();
        go();
        showNotification();
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
        showNotification();
    }

    public void seek(int posn) {
        mediaPlayer.seekTo(posn);
    }

    public void go() {
        mediaPlayer.start();
        showNotification();
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
        if (mediaPlayer.isPlaying()) {
            notificationLayout.setImageViewResource(R.id.btnPlay_noti, R.drawable.pause);
        } else {
            notificationLayout.setImageViewResource(R.id.btnPlay_noti, R.drawable.play);
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
                .setSmallIcon(R.drawable.iconlove)
                .setCustomContentView(notificationLayout)
                .setPriority(NotificationManager.IMPORTANCE_LOW)
                .build();
        NotificationManager notificationManager =
                (android.app.NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int id = intent.getIntExtra(EXTRA_BUTTON_CLICKED, -1);
            switch (id) {
                case R.id.btnPre_noti:
                    playPrev();
                    break;
                case R.id.btnPlay_noti:
                    if (mediaPlayer.isPlaying())
                        pausePlayer();
                    else
                        go();
                    break;
                case R.id.btnNext_noti:
                    playNext();
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
            channel.setImportance(NotificationManager.IMPORTANCE_LOW);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



    class Player extends AsyncTask<String,Void,Boolean> {


        @Override
        protected Boolean doInBackground(String... strings) {
            Boolean prepared = false;
            try {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
//                        initialStage=true;
//                        playPause=false;
//                        btn_play_pause.setImageResource(R.drawable.ic_play);
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }

                });
                mediaPlayer.prepare();
                prepared=true;

            } catch (Exception ex) {
                Log.e("MyAudioStreamApp", ex.getMessage());
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            super.onPostExecute(aBoolean);

            mediaPlayer.start();

//            initialStage=false;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


    }

}
