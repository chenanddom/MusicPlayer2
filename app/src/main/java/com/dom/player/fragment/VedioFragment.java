package com.dom.player.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dom.palyer.base.BaseFragment;

import player.dom.com.musicplayer.R;

/**
 * Created by chendom on 16-11-9.
 */

public class VedioFragment extends BaseFragment {
    private View view ;
    private static VedioFragment mvedioFragment;
    public static VedioFragment getInstance(){
        if (mvedioFragment==null){
            mvedioFragment=new VedioFragment();
        }
        return mvedioFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_vedio,null);
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public boolean onBackPressd() {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
