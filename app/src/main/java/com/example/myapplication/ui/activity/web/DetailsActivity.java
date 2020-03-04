package com.example.myapplication.ui.activity.web;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.config.Constants;
import com.example.myapplication.databinding.ActivityDetailsBinding;
import com.example.myapplication.ui.activity.main.MainActivity;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;

/**
 * 详情页
 * webView
 */
public class DetailsActivity extends BaseActivity<ActivityDetailsBinding, DetailsViewModel> {

    private AgentWeb mAgentWeb;

    private String mUrl;
    /**
     * 是否从Splash界面跳转过来
     */
    private boolean mFromSplash;

    public static void start(Context context, String url, boolean fromSplash) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(Constants.ParamCode.PARAM1, fromSplash);
        intent.putExtra(Constants.ParamCode.KEY_URL, url);
        context.startActivity(intent);
    }

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(Constants.ParamCode.KEY_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void handleIntent(Intent intent) {
        mUrl = intent.getStringExtra(Constants.ParamCode.KEY_URL);
        mFromSplash = intent.getBooleanExtra(Constants.ParamCode.PARAM1, false);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_details;
    }

    @Override
    protected void initViewModel() {
        mViewModel = new DetailsViewModel();
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {
        initToolbar();
        initWebView();
    }

    private void initToolbar() {
        setSupportActionBar(mDataBinding.tbToolbar);
    }

    private void initWebView() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mDataBinding.llRoot, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .createAgentWeb()
                .ready()
                .go(mUrl);
    }

    private WebChromeClient mWebChromeClient = new WebChromeClient() {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            mDataBinding.tbToolbar.setTitle(title);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (!mAgentWeb.back()) {
            if (mFromSplash) {
                //如果从Splash界面跳转来，就返回到主页
                MainActivity.start(DetailsActivity.this,false);
                finish();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
