package com.dom.player.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.dom.palyer.base.BaseFragment;
import com.dom.player.adapter.AmusementAdapter;
import com.dom.player.bean.AmusementNews;
import com.dom.player.chache.ImageLoader;
import com.dom.player.mconst.APPConst;
import com.dom.player.utils.JsonUtils;
import com.dom.player.utils.LogUtil;
import com.dom.player.utils.NetUtils;

import java.util.ArrayList;
import java.util.List;

import player.dom.com.musicplayer.R;
import player.dom.com.musicplayer.ScanActivity;

/**
 * Created by chendom on 16-11-9.
 */

public class AmusementFragment extends BaseFragment implements ListView.OnItemClickListener{
    private View view;
    private static AmusementFragment amusementFragment;
    private static ListView lvNewsList;
    private static ProgressBar progressBar;
    private int page=1;
    private String path=APPConst.AppApi.path+"&page="+page;
    private int lastItem;
    private ArrayList<AmusementNews> list = new ArrayList<AmusementNews>();
    private AmusementAdapter amusementAdapter;
    private boolean hadIntercept;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x001:
                    for (AmusementNews ls:(ArrayList<AmusementNews>) JsonUtils.getJson((String) msg.obj)){
                        list.add(ls);
                        LogUtil.showLog("ls",""+ls.toString());
                    }
                    amusementAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
                default:

                    break;
            }
        }
    };



    public static AmusementFragment getInstance(){
        if (amusementFragment==null){
            amusementFragment = new AmusementFragment();
        }
        return amusementFragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initData();
        initView();
        setListener();
        return view;
    }

    @Override
    public void initData() {
        getData();
    }
    @Override
    public void initView() {
        view=View.inflate(getActivity(), R.layout.fragment_amusement,null);
        lvNewsList=(ListView)view.findViewById(R.id.lvAmusementList);
        progressBar=(ProgressBar)view.findViewById(R.id.downLoadProgress);

    }

    @Override
    public void setListener() {
        lvNewsList.setOnItemClickListener(this);
        amusementAdapter = new AmusementAdapter(getActivity(),list);
        lvNewsList.setAdapter(amusementAdapter);
        lvNewsList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (lastItem == list.size() - 1) {
                    page++;
                    path = APPConst.AppApi.path+"&page="+page;
                    getData();
                    progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    lastItem=lvNewsList.getLastVisiblePosition();
            }
        });

    }

    @Override
    public boolean onBackPressd() {
        if (hadIntercept) {
            return false;
        } else {
            ImageLoader mImageLoader = amusementAdapter.getImageLoader();
            if (mImageLoader != null) {
                mImageLoader.clearCache();
            }
            hadIntercept = true;
            return true;
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ScanActivity.class);
        intent.putExtra("title",list.get(position).getTitle());
        intent.putExtra("url",list.get(position).getUrl());
        startActivity(intent);
    }
    public void getData(){

        new Thread(){
            @Override
            public void run() {
                String result = NetUtils.amusementRequest(path);
                LogUtil.showLog("test",""+result);
                Message msg = Message.obtain();
                LogUtil.showLog("result",""+result);
                msg.obj=result;
                msg.what=0x001;
                handler.sendMessage(msg);
            }
        }.start();
    }
}
