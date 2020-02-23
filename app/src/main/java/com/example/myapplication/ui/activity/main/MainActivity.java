package com.example.myapplication.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.http.bean.LoginBean;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.ui.view.CircleImageView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewModel() {

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {
        setSupportActionBar(mDataBinding.toolbar);

        drawer = mDataBinding.drawerLayout;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(mDataBinding.navView, navController);

        BottomNavigationView navView = findViewById(R.id.nav_view_bottom);
        NavigationUI.setupWithNavController(navView, navController);

        initUserData();

    }

    private void initUserData() {
        TextView tvName = mDataBinding.navView.getHeaderView(0).findViewById(R.id.tv_nike_name);
        TextView textView = mDataBinding.navView.getHeaderView(0).findViewById(R.id.textView);
        CircleImageView ivHeader = mDataBinding.navView.getHeaderView(0).findViewById(R.id.imageView);

        mViewModel.getUserBean().observe(this, new Observer<HttpBaseResponse<LoginBean>>() {
            @Override
            public void onChanged(HttpBaseResponse<LoginBean> bean) {
                if (bean.errorCode == 0) {
                    tvName.setText(bean.data.getNickname());
                    textView.setText(bean.data.getUsername());
                } else {
                    tvName.setText(getResources().getString(R.string.user_name));
                    textView.setText(getResources().getString(R.string.click_login));
                }
            }
        });


        mViewModel.getUserHeader().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Glide.with(MainActivity.this)
                        .load(s)
                        .into(ivHeader);
            }
        });

        mDataBinding.navView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewModel.getUserBean().getValue().errorCode != 0) {
                    Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.loginFragment);
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.mineFragment);
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });
    }

    private void setNavigationView(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
