package com.example.myapplication.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.config.Constants;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.navinterface.ScrollToTop;
import com.example.myapplication.ui.activity.login.LoginActivity;
import com.example.myapplication.ui.view.CircleImageView;
import com.example.myapplication.util.GlideUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//                                         ,s555SB@@&
//                                      :9H####@@@@@Xi
//                                     1@@@@@@@@@@@@@@8
//                                   ,8@@@@@@@@@B@@@@@@8
//                                  :B@@@@X3hi8Bs;B@@@@@Ah,
//             ,8i                  r@@@B:     1S ,M@@@@@@#8;
//            1AB35.i:               X@@8 .   SGhr ,A@@@@@@@@S
//            1@h31MX8                18Hhh3i .i3r ,A@@@@@@@@@5
//            ;@&i,58r5                 rGSS:     :B@@@@@@@@@@A
//             1#i  . 9i                 hX.  .: .5@@@@@@@@@@@1
//              sG1,  ,G53s.              9#Xi;hS5 3B@@@@@@@B1
//               .h8h.,A@@@MXSs,           #@H1:    3ssSSX@1
//               s ,@@@@@@@@@@@@Xhi,       r#@@X1s9M8    .GA981
//               ,. rS8H#@@@@@@@@@@#HG51;.  .h31i;9@r    .8@@@@BS;i;
//                .19AXXXAB@@@@@@@@@@@@@@#MHXG893hrX#XGGXM@@@@@@@@@@MS
//                s@@MM@@@hsX#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@&,
//              :GB@#3G@@Brs ,1GM@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@B,
//            .hM@@@#@@#MX 51  r;iSGAM@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@8
//          :3B@@@@@@@@@@@&9@h :Gs   .;sSXH@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:
//      s&HA#@@@@@@@@@@@@@@M89A;.8S.       ,r3@@@@@@@@@@@@@@@@@@@@@@@@@@@r
//   ,13B@@@@@@@@@@@@@@@@@@@5 5B3 ;.         ;@@@@@@@@@@@@@@@@@@@@@@@@@@@i
//  5#@@#&@@@@@@@@@@@@@@@@@@9  .39:          ;@@@@@@@@@@@@@@@@@@@@@@@@@@@;
//  9@@@X:MM@@@@@@@@@@@@@@@#;    ;31.         H@@@@@@@@@@@@@@@@@@@@@@@@@@:
//   SH#@B9.rM@@@@@@@@@@@@@B       :.         3@@@@@@@@@@@@@@@@@@@@@@@@@@5
//     ,:.   9@@@@@@@@@@@#HB5                 .M@@@@@@@@@@@@@@@@@@@@@@@@@B
//           ,ssirhSM@&1;i19911i,.             s@@@@@@@@@@@@@@@@@@@@@@@@@@S
//              ,,,rHAri1h1rh&@#353Sh:          8@@@@@@@@@@@@@@@@@@@@@@@@@#:
//            .A3hH@#5S553&@@#h   i:i9S          #@@@@@@@@@@@@@@@@@@@@@@@@@A.
//
//
//    又看代码，看你妹妹呀！
/**
 *
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private boolean isLogin;

    public static void start(Context context, Boolean isLogin) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Constants.ParamCode.PARAM1, isLogin);
        context.startActivity(intent);
    }

    @Override
    protected void handleIntent(Intent intent) {
        isLogin = intent.getBooleanExtra(Constants.ParamCode.PARAM1, false);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        isLogin = intent.getBooleanExtra(Constants.ParamCode.PARAM1, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.getUserData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {
        initView();
        initUserData();
        initFloatingActionButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        login();
    }

    private void login() {
        if (isLogin) {
            //已经登录成功了，直接从缓存中读取用户信息
            mViewModel.setUserBean();
        }
    }

    /**
     * 初始化侧边栏和底部状态栏
     */
    private void initView() {
        setSupportActionBar(mDataBinding.toolbar);
        drawer = mDataBinding.drawerLayout;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_square, R.id.nav_about, R.id.nav_update)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(mDataBinding.navView, navController);

        BottomNavigationView navView = findViewById(R.id.nav_view_bottom);
        NavigationUI.setupWithNavController(navView, navController);
    }

    /**
     * 初始化侧边栏用户信息
     */
    private void initUserData() {
        TextView tvName = mDataBinding.navView.getHeaderView(0).findViewById(R.id.tv_nike_name);
        TextView textView = mDataBinding.navView.getHeaderView(0).findViewById(R.id.textView);
        CircleImageView ivHeader = mDataBinding.navView.getHeaderView(0).findViewById(R.id.imageView);

        //加载用户昵称和账号
        mViewModel.getUserBean().observe(this, bean -> {
            if (bean != null) {
                tvName.setText(bean.getNickname());
                textView.setText(bean.getUsername());
            } else {
                tvName.setText(getResources().getString(R.string.user_name));
                textView.setText(getResources().getString(R.string.click_login));
            }
        });

        //加载用户头像
        mViewModel.getUserHeader().observe(this, s -> GlideUtil.loadImageWithDefault(ivHeader, s));


        //点击头部个人信息
        mDataBinding.navView.getHeaderView(0).setOnClickListener(v -> {
            if (mViewModel.getUserBean().getValue() == null) {
                //跳转到登录界面
                LoginActivity.start(MainActivity.this);
                drawer.closeDrawer(GravityCompat.START);
            } else {
                //跳转到mine界面
                Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.mineFragment);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
    }

    /**
     * FloatingActionButton点击事件
     * 回到顶部
     */
    private void initFloatingActionButton() {
        mDataBinding.fabTop.setOnClickListener(v -> {
            Fragment fragment = getFragment();
            if (fragment != null) {
                ((ScrollToTop) fragment).scrollToTop();
            }
        });
    }


    /**
     * 获取当前显示的Fragment实例
     */
    private Fragment getFragment() {
        //获取指定的fragment
        Fragment mMainNavFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        Fragment fragment = mMainNavFragment.getChildFragmentManager().getPrimaryNavigationFragment();
        if (fragment instanceof ScrollToTop) {
            return fragment;
        }
        return null;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (drawer == null) return;
        // 返回键: 侧滑开着就将其关闭, 关着则退出应用
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
