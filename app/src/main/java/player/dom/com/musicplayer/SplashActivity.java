package player.dom.com.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.dom.palyer.base.BaseActivity;

/**
 * Created by chendom on 16-11-6.
 */

public class SplashActivity extends BaseActivity {
    private ImageView imageView;
    private Animation mAnimation = null;
    public Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x123:
                    overridePendingTransition(R.anim.tran_in,R.anim.tran_out);
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                    overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
                    break;
                case 0x124:
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void initData() {
    }

    @Override
    public void setView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
    }
    @Override
    public void initView() {
        imageView = (ImageView) findViewById(R.id.ivSplash);
        mAnimation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.splashanim);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mhandler.sendEmptyMessage(0x123);
                    }
                }).start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        imageView.startAnimation(mAnimation);

    }

    @Override
    public void setListener() {

    }
    @Override
    protected void onStop() {
        super.onStop();
        handler.sendEmptyMessage(0x110);
    }

    @Override
    public void onClick(View v) {

    }
}
