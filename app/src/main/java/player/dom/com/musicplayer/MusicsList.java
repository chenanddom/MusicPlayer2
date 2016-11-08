package player.dom.com.musicplayer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dom.SongsAdapter;
import com.dom.palyer.base.BaseActivity;
import com.dom.player.bean.Song;
import com.dom.player.utils.LogUtil;
import com.dom.player.utils.MediaUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chendom on 16-11-8.
 */

public class MusicsList extends BaseActivity implements ListView.OnItemClickListener{
private ListView songList;
    private List<Song> list;
    private SongsAdapter adapter;
    @Override
    public void initData() {
    list = MediaUtil.getAllSongs(MusicsList.this);
        LogUtil.showLog("musics","------------------"+list.toString());

    }

    @Override
    public void setView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_songlist);
    }

    @Override
    public void initView() {
        songList=(ListView)findViewById(R.id.lvSongs);

    }

    @Override
    public void setListener() {
        songList.setOnItemClickListener(this);
        adapter = new SongsAdapter(MusicsList.this, (ArrayList<Song>) list);
        songList.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
