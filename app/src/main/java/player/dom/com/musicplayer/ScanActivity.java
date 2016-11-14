package player.dom.com.musicplayer;

import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.dom.palyer.base.BaseActivity;

/**
 * Created by chendom on 16-11-10.
 */

public class ScanActivity extends BaseActivity {
    private Intent intent;
    private String title;
    private String url;
    private ImageView ivBack;
    private ImageView ivShare;
    private RadioButton rbCollect;
    private WebView webView;
    private ImageView ivPrev;
    private ImageView ivNext;
    @Override
    public void initData() {
        intent = getIntent();
        title = intent.getStringExtra("title");
        url = intent.getStringExtra("url");


    }

    @Override
    public void setView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scan);
    }

    @Override
    public void initView() {
        ivBack = (ImageView) findViewById(R.id.ivScanBack);
        ivShare = (ImageView) findViewById(R.id.ivShare);
        rbCollect = (RadioButton) findViewById(R.id.rbCollect);
        webView = (WebView) findViewById(R.id.wvView);
        ivPrev=(ImageView)findViewById(R.id.ivPrev);
        ivNext=(ImageView)findViewById(R.id.ivNext);
    }

    @Override
    public void setListener() {
        webViewSetting(webView);
        webView.loadUrl(url);
        ivBack.setOnClickListener(this);
        rbCollect.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        ivPrev.setOnClickListener(this);
        ivNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivScanBack:
                finish();
                overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
                break;
            case R.id.rbCollect:
                if (rbCollect.isSelected()){
                    rbCollect.setSelected(false);
                }else {
                    rbCollect.setSelected(true);
                }
                break;
            case R.id.ivShare:
                shareAmusementNews();
                break;
            case R.id.ivPrev:
                if (webView.canGoBack()){
                    webView.goBack();
                }
                break;
            case R.id.ivNext:
                if (webView.canGoForward()){
                    webView.goForward();
                }
                break;
            default:
                break;

        }
    }
    public void webViewSetting(WebView mWebView) {
        // 设置webview可以伸缩
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        // 设置此属性，可任意比例缩放
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        // webSettings.setJavaScriptEnabled(true); //支持js
        // 优先使用缓存：
        mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.clearCache(true);
        mWebView.destroyDrawingCache();
    }
    public void shareAmusementNews(){
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, url);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getTitle()));
    }
}
