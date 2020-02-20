package com.example.myapplication.ui.webview;

import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.databinding.FragmentWebViewBinding;
import com.just.agentweb.AgentWeb;

import androidx.lifecycle.ViewModelProviders;

/**
 * @author devel
 */
public class WebViewFragment extends BaseFragment<FragmentWebViewBinding, WebViewViewModel> {

    private WebViewViewModel mViewModel;
    private AgentWeb mAgentWeb;
    private String mUrl;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_web_view;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(WebViewViewModel.class);

    }

    @Override
    protected void bindViewModel() {

        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {

        initWebView();
    }


    private void initWebView() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mDataBinding.llRoot, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()
                .setWebChromeClient((com.just.agentweb.WebChromeClient) mWebChromeClient)
                .createAgentWeb()
                .ready()
                .go(mUrl);
    }

    private WebChromeClient mWebChromeClient = new WebChromeClient() {

        @Override
        public void onReceivedTitle(WebView view, String title) {

        }
    };
}
