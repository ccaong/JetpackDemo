package com.example.myapplication.ui.nav.login;

import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.databinding.FragmentLoginBinding;
import com.example.myapplication.http.bean.LoginBean;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.ui.activity.main.MainViewModel;

public class LoginFragment extends BaseFragment<FragmentLoginBinding, MainViewModel> {

    private MainViewModel mViewModel;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
        mDataBinding.setPresenter(LoginFragment.this);
    }

    @Override
    protected void init() {

        mViewModel.getUserBean().observe(this, new Observer<HttpBaseResponse<LoginBean>>() {
            @Override
            public void onChanged(HttpBaseResponse<LoginBean> bean) {
                if (bean.errorCode == 0) {
                    Toast.makeText(getContext(), "登陆成功！", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(LoginFragment.this).navigateUp();
                } else if (bean.errorCode == -1) {
                    Toast.makeText(getContext(), "用户名或密码有误，请重试！", Toast.LENGTH_SHORT).show();

                } else if (bean.errorCode == 1) {
                    Toast.makeText(getContext(), "用户名和密码不能为空，请重试！", Toast.LENGTH_SHORT).show();

                } else if (bean.errorCode == 3) {
                } else {
                    Toast.makeText(getContext(), "登陆失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 注册
     */
    public void register() {
        NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.registerFragment);
    }
}
