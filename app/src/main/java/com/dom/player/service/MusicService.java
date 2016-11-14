package com.dom.player.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dom.player.bean.Song;
import com.dom.player.mconst.APPConst;
import com.dom.player.utils.LogUtil;
import com.dom.player.utils.MediaUtil;

import java.util.ArrayList;

/**
 * Created by chendom on 16-11-7.
 */

public class MusicService extends Service {
    private MediaPlayer mediaPlayer;
    private String path;
    private int msg;
    private boolean isPause;
    private int mCurrentPosition;
    private int currentTime;
    private int duration;
    private ArrayList<Song> mp3Infos;
    public static final String UPDATE_ACTION = "com.lzw.action.UPDATE_ACTION";
    public static final String CTL_ACTION = "com.lzw.action.CTL_ACTION";
    public static final String MUSIC_CURRENT = "com.lzw.action.MUSIC_CURRENT";
    public static final String MUSIC_DURATION = "com.lzw.action.MUSIC_DURATION";

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                if(mediaPlayer != null) {
                    currentTime = mediaPlayer.getCurrentPosition(); // 获取当前音乐播放的位置
                    Intent intent = new Intent();
                    intent.setAction(MUSIC_CURRENT);
                    intent.putExtra("currentTime", currentTime);
                    sendBroadcast(intent); // 给PlayerActivity发送广播
                    handler.sendEmptyMessageDelayed(1, 1000);
                }
            }
        };
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("service", "service created");
        mediaPlayer = new MediaPlayer();
        mp3Infos = MediaUtil.getAllSongs(MusicService.this);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mCurrentPosition++;
                Log.v("po", mCurrentPosition+"");
                if (mCurrentPosition <= mp3Infos.size() - 1) {
                    Intent sendIntent = new Intent(UPDATE_ACTION);
                    sendIntent.putExtra("current", mCurrentPosition);

                    sendBroadcast(sendIntent);
                    path = mp3Infos.get(mCurrentPosition).getUrl();
                    play(0);
                }else {
                    mediaPlayer.seekTo(0);
                    mCurrentPosition = 0;
                    Intent sendIntent = new Intent(UPDATE_ACTION);
                    sendIntent.putExtra("current", mCurrentPosition);

                    sendBroadcast(sendIntent);
                }



            }
        });
    }

    @Override
    public void onStart(Intent intent, int startId) {
        if(intent==null){
            stopSelf();
        }else{
            path = intent.getStringExtra("url");
            msg = intent.getIntExtra("MSG", 0);
            mCurrentPosition=intent.getIntExtra("position",-1);
            if (msg == APPConst.PlayerMSG.PLAYMSG) {
                play(0);
            } else if (msg == APPConst.PlayerMSG.PLAYPAUSE) {
                pause();
            } else if (msg == APPConst.PlayerMSG.PLAYESTOP) {
                stop();
            } else if (msg == APPConst.PlayerMSG.PLAYCONTINUEMSG) {
                resume();
            } else if (msg == APPConst.PlayerMSG.PROGRESSCHANGE) {
                currentTime = intent.getIntExtra("progress", -1);
                play(currentTime);
            } else if (msg == APPConst.PlayerMSG.PLAYINGMSG) {
                handler.sendEmptyMessage(1);
            }
            super.onStart(intent, startId);
        }
    }


    private void play(int currentTime) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new PreparedListener(currentTime));
            handler.sendEmptyMessage(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPause = true;
        }
    }

    private void resume() {
        if (isPause) {
            mediaPlayer.start();
            isPause = false;
        }
    }


    private void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            try {
                mediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private final class PreparedListener implements MediaPlayer.OnPreparedListener {
        private int currentTime;

        public PreparedListener(int currentTime) {
            this.currentTime = currentTime;
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            mediaPlayer.start();
            if (currentTime > 0) {
                mediaPlayer.seekTo(currentTime);
            }
            Intent intent = new Intent();
            intent.setAction(MUSIC_DURATION);
            duration = mediaPlayer.getDuration();
            intent.putExtra("duration", duration);
            sendBroadcast(intent);
        }
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}