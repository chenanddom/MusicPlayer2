package player.dom.com.musicplayer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dom.palyer.base.BaseActivity;
import com.dom.player.bean.Song;
import com.dom.player.mconst.APPConst;
import com.dom.player.service.MusicService;
import com.dom.player.utils.LogUtil;
import com.dom.player.utils.MediaUtil;
import com.dom.player.view.AlwaysMarqueeTextView;

import java.util.ArrayList;

/**
 * Created by chendom on 16-11-10.
 */

public class PlayerActivity extends BaseActivity {

    private AlwaysMarqueeTextView amtvInfo;
    private ImageView ivBack;
    private ImageView ivPrev;
    private ImageView ivPlayOrPause;
    private ImageView ivNext;
    private TextView tvCurrentTime;
    private TextView tvTotalTime;
    private SeekBar sbPrograss;

    private Intent intent;
    private static int mCurrentPosition = -1;
    private Boolean flag;
    private String url;
    private Song mSongBean;
    private ArrayList<Song> musicInfos;
    private boolean isPlaying;
    private boolean isPause;
    private int currentTime;
    private PlayerReceiver playerReceiver;
    public static final String UPDATE_ACTION = "com.lzw.action.UPDATE_ACTION";
    public static final String CTL_ACTION = "com.lzw.action.CTL_ACTION";
    public static final String MUSIC_CURRENT = "com.lzw.action.MUSIC_CURRENT";
    public static final String MUSIC_DURATION = "com.lzw.action.MUSIC_DURATION";
    public static final String MUSIC_PLAYING = "com.lzw.action.MUSIC_PLAYING";


    @Override
    public void initData() {
        intent = getIntent();
        int position = intent.getIntExtra("position", -1);
        if (mCurrentPosition != position) {
            mCurrentPosition = position;
            flag = true;
            isPause = false;
            isPlaying = true;
        } else {
            flag = false;
            isPlaying = true;
            isPause = false;
        }
        musicInfos = MediaUtil.getAllSongs(PlayerActivity.this);
        mSongBean = musicInfos.get(mCurrentPosition);
        LogUtil.showLog("test", "-----*******" + musicInfos.toString());
        playerReceiver = new PlayerReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPDATE_ACTION);
        filter.addAction(MUSIC_CURRENT);
        filter.addAction(MUSIC_DURATION);
        registerReceiver(playerReceiver, filter);
    }

    @Override
    public void setView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_player);

    }

    @Override
    public void initView() {
        amtvInfo = (AlwaysMarqueeTextView) findViewById(R.id.avInfo);
        ivBack = (ImageView) findViewById(R.id.ivPlayerBack);
        ivPrev = (ImageView) findViewById(R.id.ivPlayerPrev);
        ivPlayOrPause = (ImageView) findViewById(R.id.ivPlayerpp);
        ivNext = (ImageView) findViewById(R.id.ivPlayerNext);
        sbPrograss = (SeekBar) findViewById(R.id.sbPlayer);
        tvCurrentTime = (TextView) findViewById(R.id.tvCurrentTime);
        tvTotalTime = (TextView) findViewById(R.id.tvTotalTime);
        if (isPause) {
            ivPlayOrPause.setSelected(true);

        } else {
            ivPlayOrPause.setSelected(false);
        }
        sbPrograss.setMax((int) mSongBean.getDuration());
        amtvInfo.setText(mSongBean.getTitle() + mSongBean.getArtist());
        tvTotalTime.setText(MediaUtil.formatTimer(mSongBean.getDuration()) + "");


    }

    @Override
    public void setListener() {
        ivBack.setOnClickListener(this);
        ivPrev.setOnClickListener(this);
        ivPlayOrPause.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        sbPrograss.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void play() {
        Intent intent = new Intent();
        intent.setAction("com.lzw.media.MUSIC_SERVICE");
        intent.setClass(this, MusicService.class);
        intent.putExtra("url", mSongBean.getUrl());
        intent.putExtra("position", mCurrentPosition);
        intent.putExtra("MSG", APPConst.PlayerMSG.PLAYMSG);
        startService(intent);
        ivPlayOrPause.setSelected(true);
        isPlaying = true;
        isPause = false;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivPlayerBack:
                finish();
                overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
                break;
            case R.id.ivPlayerPrev:

                break;
            case R.id.ivPlayerpp:
                if (!ivPlayOrPause.isSelected()) {
                    ivPlayOrPause.setSelected(true);
                    if (flag) {
                        play();
                        isPlaying = true;
                        isPause = false;
                    } else {
                        Intent intent2 = new Intent(PlayerActivity.this, MusicService.class);
                        intent2.putExtra("MSG", APPConst.PlayerMSG.PLAYINGMSG);
                        intent2.putExtra("position", mCurrentPosition);
                        intent2.setAction("com.dom.musicservice");
                        startService(intent2);
                        isPlaying = true;
                        isPause = false;
                    }
                } else {
                    ivPlayOrPause.setSelected(false);
                }
                break;
            case R.id.ivPlayerNext:

                break;
            default:
                break;


        }
    }

    public class PlayerReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case MUSIC_CURRENT:
                    currentTime = intent.getIntExtra("currentTime", -1);
                    tvCurrentTime.setText(MediaUtil.formatTimer(currentTime));
                    sbPrograss.setProgress(currentTime);
                    break;
                case MUSIC_DURATION:

                    break;
                case UPDATE_ACTION:
                    int duration = intent.getIntExtra("duration", -1);
                    sbPrograss.setMax(duration);


                default:
                    break;

            }
        }
    }


}
