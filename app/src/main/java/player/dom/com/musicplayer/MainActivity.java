package player.dom.com.musicplayer;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dom.palyer.base.BaseActivity;
import com.dom.palyer.base.BaseFragment;
import com.dom.player.fragment.AmusementFragment;
import com.dom.player.fragment.BackHandledInterface;
import com.dom.player.fragment.MusicFragment;
import com.dom.player.fragment.VedioFragment;
import com.dom.player.utils.LogUtil;
import com.dom.player.utils.MediaUtil;
import com.dom.player.view.MSlidingMenu;

public class MainActivity extends BaseActivity implements BackHandledInterface{
    private MSlidingMenu mSliding;
    private ImageView mToggle;
    private LinearLayout changeColor,nightMode,privateSpace,searchMusics,musicsResource;
    private TextView mSetting,mExit;
    private RadioButton rbMusic,rbAmusement,rbVedio;
    private FragmentManager fragmentManager;
    private MusicFragment musicFragment;
    private AmusementFragment amusementFragment;
    private VedioFragment vedioFragment;
    private BaseFragment mBaseFragment;
    private long exitTime = 0;

    @Override
    public void initData() {
        fragmentManager = getFragmentManager();
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
        rbMusic=(RadioButton)findViewById(R.id.mMusic);
        rbAmusement=(RadioButton)findViewById(R.id.mAmusement);
        rbVedio=(RadioButton)findViewById(R.id.mVedio);

        if (musicFragment==null){
            musicFragment = MusicFragment.getInstance();
        }
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        // 把内容显示至真布局
        fragmentTransaction.replace(R.id.container, musicFragment);
        // 提交事务
        fragmentTransaction.commit();
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
        rbMusic.setOnClickListener(this);
        rbAmusement.setOnClickListener(this);
        rbVedio.setOnClickListener(this);
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
            case R.id.mMusic:
                if (musicFragment==null){
                    musicFragment = MusicFragment.getInstance();
                }
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();
                // 把内容显示至真布局
                fragmentTransaction.replace(R.id.container, musicFragment);
                // 提交事务
                fragmentTransaction.commit();
                break;
            case R.id.mAmusement:
                if (amusementFragment==null){
                    amusementFragment=AmusementFragment.getInstance();
                }
                FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                fragmentTransaction1.replace(R.id.container,amusementFragment);
                fragmentTransaction1.commit();
                break;
            case R.id.mVedio:
                if (vedioFragment==null){
                vedioFragment=VedioFragment.getInstance();
                }
                FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
                fragmentTransaction2.replace(R.id.container,vedioFragment);
                fragmentTransaction2.commit();
                break;
            default:
                break;
        }
    }

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {
        this.mBaseFragment=selectedFragment;
    }

    @Override
    public void onBackPressed() {
        exit();
    }
    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            if (mBaseFragment == null ||!mBaseFragment.onBackPressd()) {
                if (getFragmentManager().getBackStackEntryCount() == 0) {
                    super.onBackPressed();
                } else {
                    getFragmentManager().popBackStack();
                }
            }
            finish();
            System.exit(0);
        }
    }
}
