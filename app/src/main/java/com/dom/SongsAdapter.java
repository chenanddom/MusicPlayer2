package com.dom;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.dom.player.bean.Song;
import java.util.ArrayList;

import player.dom.com.musicplayer.R;

/**
 * Created by chendom on 16-11-8.
 */

public class SongsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Song> list;

    public SongsAdapter(Context context, ArrayList<Song> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_songlist, null);
            viewHolder.tvSongTitle = (TextView) convertView.findViewById(R.id.tvSongTitle);
            viewHolder.tvSingerName = (TextView) convertView.findViewById(R.id.tvSongerName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String songTitle = list.get(position).getTitle();
        String singerName = list.get(position).getArtist();
        if (songTitle.trim().equals("<unknown>")|| TextUtils.isEmpty(songTitle)) {
            viewHolder.tvSongTitle.setText("未知");
        }else {
            viewHolder.tvSongTitle.setText(songTitle);
        }
        if (singerName.trim().equals("<unknown>")||TextUtils.isEmpty(singerName)) {
            viewHolder.tvSingerName.setText("未知");
        }else {
            viewHolder.tvSingerName.setText(singerName);
        }

        return convertView;
    }
    public class ViewHolder {
        private TextView tvSongTitle;
        private TextView tvSingerName;
    }
}
