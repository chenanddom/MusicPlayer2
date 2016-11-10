package com.dom.player.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dom.palyer.base.BaseFragment;
import com.dom.player.utils.LogUtil;

import player.dom.com.musicplayer.MusicsList;
import player.dom.com.musicplayer.R;

/**
 * Created by chendom on 16-11-9.
 */

public class MusicFragment extends BaseFragment{
    private static MusicFragment mfragment;
    private View view;
    private LinearLayout itemLocalMusic,itemHistoryMusic,itemUploadMusic,itemPrivateMusic;
    public static MusicFragment getInstance(){
        if (mfragment==null){
            mfragment=new MusicFragment();
        }
        return mfragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initData();
        initView();
        setListener();
        return view;
    }
    @Override
    public void  initData(){

    }

    @Override
    public void initView() {
        view=View.inflate(getActivity(), R.layout.fagment_music,null);
        itemLocalMusic=(LinearLayout)view.findViewById(R.id.localMusic);
        itemHistoryMusic=(LinearLayout)view.findViewById(R.id.musicHistory);
        itemUploadMusic=(LinearLayout)view.findViewById(R.id.musicUpload);
        itemPrivateMusic=(LinearLayout)view.findViewById(R.id.musicPrivate);
    }

    @Override
    public void setListener() {
        itemLocalMusic.setOnClickListener(this);
        itemHistoryMusic.setOnClickListener(this);
        itemUploadMusic.setOnClickListener(this);
        itemPrivateMusic.setOnClickListener(this);
    }

    @Override
    public boolean onBackPressd() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.localMusic:
//                LogUtil.showLog("localMusic","localMusic");
                startActivity(new Intent(getActivity(), MusicsList.class));
                break;
            case R.id.musicHistory:
                LogUtil.showLog("musicHistory","musicHistory");
                break;
            case R.id.musicUpload:
                LogUtil.showLog("musicUpload","musicUpload");
                break;
            case R.id.musicPrivate:
                LogUtil.showLog("musicPrivate","musicPrivate");
                break;
            default:
                break;
        }
    }

}
