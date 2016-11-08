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
    private MediaPlayer mediaPlayer;//the object of music player
    private String path;//music resource path
    private int msg;
    private boolean isPause;//pause this music player
    private int mCurrentPosition;
    private int currentTime;
    private int duration;
    private ArrayList<Song> mp3Infos;
    //-----------------------------------------------------------------------
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                currentTime = mediaPlayer.getCurrentPosition();//get the position of position
                    Intent intent = new Intent();
                    intent.setAction(APPConst.PlayerMSG.MUSIC_CURRENT);
                    intent.putExtra("currentTime",currentTime);
                    sendBroadcast(intent);
                    handler.sendEmptyMessageDelayed(1,1000);
                    break;
                default:
                    break;
            }

        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.showLog("service","service create");
        mediaPlayer = new MediaPlayer();
        mp3Infos = MediaUtil.getAllSongs(MusicService.this);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mCurrentPosition++;
                LogUtil.showLog("Current",mCurrentPosition+"");
                if (mCurrentPosition<=mp3Infos.size()-1){
                    Intent sendIntent = new Intent(APPConst.PlayerMSG.UPDATE_ACTION);
                    sendIntent.putExtra("current",mCurrentPosition);
                    sendBroadcast(sendIntent);
                    path=mp3Infos.get(mCurrentPosition).getUrl();
                    play(0);
                }else {
                    mediaPlayer.seekTo(0);
                    mCurrentPosition=0;
                    Intent sendIntent = new Intent(APPConst.PlayerMSG.UPDATE_ACTION);
                    sendIntent.putExtra("current",mCurrentPosition);
                    sendBroadcast(sendIntent);
                }

            }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            stopSelf();
        } else {
            path = intent.getStringExtra("url");
            msg = intent.getIntExtra("MSG", 0);
            mCurrentPosition = intent.getIntExtra("position", -1);
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

        }
        return super.onStartCommand(intent,flags,startId);
    }

    /**
     * play music
     *
     * @param position
     */
    private void play(int position) {
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
                mediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void resume() {
        if (isPause) {
            mediaPlayer.start();
            isPause = false;
        }
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null)
            mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
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
            intent.setAction(APPConst.PlayerMSG.MUSICDURATION);
            duration = mediaPlayer.getDuration();
            intent.putExtra("duration", duration);
            sendBroadcast(intent);
        }
    }


}
