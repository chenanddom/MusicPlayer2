package com.dom.player.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.dom.player.mconst.APPConst;

/**
 * Created by chendom on 16-11-7.
 */

public class MusicService extends Service {
    private MediaPlayer mediaPlayer = new MediaPlayer();//the object of music player
    private String path;//music resource path
    private boolean isPause;//pause this music player

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mediaPlayer.isPlaying()) {
            stop();
        }
        path = intent.getStringExtra("url");
        int msg = intent.getIntExtra("MSG", 0);
        if (msg == APPConst.PlayerMSG.PLAYMSG) {
            play(0);
        } else if (msg == APPConst.PlayerMSG.PLAYPAUSE) {
            pause();
        } else if (msg == APPConst.PlayerMSG.PLAYESTOP) {
            stop();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * play music
     *
     * @param position
     */
    private void play(int position) {
        try {
            mediaPlayer.reset();//take kinds of parameter into init state
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();//put into chache
            mediaPlayer.setOnPreparedListener(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * pause the playing music
     */
    private void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPause = true;
        }
    }

    /**
     * stop the playing music
     */
    private void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare();// 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null)
            mediaPlayer.stop();
        mediaPlayer.release();
    }

    private final class PreparedListener implements MediaPlayer.OnPreparedListener {
        private int position;

        public PreparedListener(int position) {
            this.position = position;
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            mediaPlayer.start();//开始播放
            if (position > 0) {//如果音乐不是从头播放
                mediaPlayer.seekTo(position);
            }
        }
    }


}
