package com.example.myapplication.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.databinding.ActivityLoginBinding;
import com.example.myapplication.bean.responsebean.LoginBean;
import com.example.myapplication.ui.activity.main.MainActivity;
import com.example.myapplication.ui.adapter.FmPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * @author : devel
 * @date : 2020/2/28 14:20
 * @desc :
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void init() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FragmentLogin());
        fragments.add(new FragmentRegister());

        FmPagerAdapter adapter = new FmPagerAdapter(getSupportFragmentManager(), fragments);
        mDataBinding.viewPager.setAdapter(adapter);

        initData();
    }

    public void initData() {
        mViewModel.getUserBean().observe(this, new Observer<LoginBean>() {
            @Override
            public void onChanged(LoginBean bean) {
                if (bean != null) {
                    //登录成功，跳转到首页
                    MainActivity.start(LoginActivity.this, true);
                    finish();
                }
            }
        });

        mViewModel.getRegisterStatus().observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object register) {
                //注册成功,返回到登录界面
                Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                mViewModel.clearUserPwd();
                goToLogin();
            }
        });
    }

    /**
     * 跳转到登录界面
     */
    public void goToLogin() {
        mDataBinding.viewPager.setCurrentItem(0);
    }


    /**
     * 跳转到注册界面
     */
    public void goToRegister() {
        mViewModel.clearUserPwd();
        mDataBinding.viewPager.setCurrentItem(1);
    }
}
