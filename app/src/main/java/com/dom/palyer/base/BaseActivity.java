package com.dom.palyer.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import player.dom.com.musicplayer.MainActivity;
import player.dom.com.musicplayer.SplashActivity;


/**
 * Created by chendom on 16-11-6.
 */

public abstract class BaseActivity extends Activity implements View.OnClickListener{
protected Handler handler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case 0x110:
                finish();
                break;
        }
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setView();
        initView();
        setListener();
    }
    public abstract void initData();

    public abstract void setView();

    public abstract void initView();

    public abstract  void setListener();

}
