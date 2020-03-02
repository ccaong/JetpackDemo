package com.example.myapplication.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.base.ScrollToTop;
import com.example.myapplication.common.Code;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.http.bean.LoginBean;
import com.example.myapplication.ui.activity.login.LoginActivity;
import com.example.myapplication.ui.view.CircleImageView;
import com.example.myapplication.util.GlideUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private boolean isLogin;

    public static void start(Context context, Boolean isLogin) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(Code.ParamCode.PARAM1, isLogin);
        context.startActivity(intent);
    }

    @Override
    protected void handleIntent(Intent intent) {
        isLogin = intent.getBooleanExtra(Code.ParamCode.PARAM1, false);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        isLogin = intent.getBooleanExtra(Code.ParamCode.PARAM1, false);
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
                R.id.nav_home, R.id.nav_square, R.id.nav_send)
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
        mViewModel.getUserBean().observe(this, new Observer<LoginBean>() {
            @Override
            public void onChanged(LoginBean bean) {
                if (bean != null) {
                    tvName.setText(bean.getNickname());
                    textView.setText(bean.getUsername());
                } else {
                    tvName.setText(getResources().getString(R.string.user_name));
                    textView.setText(getResources().getString(R.string.click_login));
                }
            }
        });

        //加载用户头像
        mViewModel.getUserHeader().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                GlideUtil.loadImageWithDefault(ivHeader, s);
            }
        });


        //点击头部个人信息
        mDataBinding.navView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewModel.getUserBean().getValue() == null) {
                    //跳转到登录界面
                    LoginActivity.start(MainActivity.this);
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    //跳转到mine界面
                    Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.mineFragment);
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });
    }

    /**
     * FloatingActionButton点击事件
     * 回到顶部
     */
    private void initFloatingActionButton() {
        mDataBinding.fabTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getFragment();
                if (fragment != null) {
                    ((ScrollToTop) fragment).scrollToTop();
                }
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

    private void setNavigationView(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_home);
                        break;
                    case R.id.nav_collect:
                        Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_collect);
                        break;
                    case R.id.toDoFragment:
                        Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.toDoFragment);
                        break;
                    case R.id.nav_square:
                        Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.nav_square);
                        break;
                    case R.id.nav_share:
                        Toast.makeText(MainActivity.this, "nav_share", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_send:
                        Toast.makeText(MainActivity.this, "nav_send", Toast.LENGTH_SHORT).show();
                        break;
                    default:

                        break;
                }
                return false;
            }
        });

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
