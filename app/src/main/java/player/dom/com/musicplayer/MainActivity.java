package player.dom.com.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dom.palyer.base.BaseActivity;
import com.dom.player.utils.LogUtil;
import com.dom.player.utils.MediaUtil;
import com.dom.player.view.MSlidingMenu;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private MSlidingMenu mSliding;
    private ImageView mToggle;
    private LinearLayout changeColor,nightMode,privateSpace,searchMusics,musicsResource;
    private TextView mSetting,mExit;

    @Override
    public void initData() {

    }

    @Override
    public void setView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void initView() {
        mSliding=(MSlidingMenu)findViewById(R.id.mSliding);
        mToggle=(ImageView)findViewById(R.id.menu_toggle);
        changeColor=(LinearLayout)findViewById(R.id.changeColor);
        nightMode = (LinearLayout)findViewById(R.id.nightmode);
        privateSpace=(LinearLayout)findViewById(R.id.privateSpace);
        searchMusics=(LinearLayout)findViewById(R.id.searchMusics);
        musicsResource=(LinearLayout)findViewById(R.id.musicResource);
        mSetting=(TextView) findViewById(R.id.mSetting);
        mExit=(TextView)findViewById(R.id.mexit);
    }

    @Override
    public void setListener() {
        mToggle.setOnClickListener(this);
        changeColor.setOnClickListener(this);
        nightMode.setOnClickListener(this);
        privateSpace.setOnClickListener(this);
        searchMusics.setOnClickListener(this);
        musicsResource.setOnClickListener(this);
        mSetting.setOnClickListener(this);
        mExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_toggle:
                mSliding.toggleMenu();
                break;
            case R.id.changeColor:
                LogUtil.showLog("changeColor",""+v.getId());
                startActivity(new Intent(MainActivity.this,MusicsList.class));
                overridePendingTransition(R.anim.tran_in,R.anim.tran_out);
                break;
            case R.id.nightmode:

                break;
            case R.id.privateSpace:

                break;
            case R.id.searchMusics:

                break;
            case R.id.musicResource:

                break;
            case R.id.mSetting:

                break;
            case R.id.mexit:

                break;
            default:
                break;
        }
    }
}
