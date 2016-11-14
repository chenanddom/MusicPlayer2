package player.dom.com.musicplayer;

import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.dom.player.adapter.SongsAdapter;
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
    private ImageView ivBack;
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
        ivBack=(ImageView)findViewById(R.id.ivBack);

    }

    @Override
    public void setListener() {
        songList.setOnItemClickListener(this);
        ivBack.setOnClickListener(this);
        adapter = new SongsAdapter(MusicsList.this, (ArrayList<Song>) list);
        songList.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent=new Intent(MusicsList.this,PlayerActivity.class);
        intent.putExtra("position",position);
        startActivity(intent);
        overridePendingTransition(R.anim.tran_in,R.anim.tran_out);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ivBack:
                finish();
                overridePendingTransition(R.anim.tran_in,R.anim.tran_out);
                break;
        }
    }
}
