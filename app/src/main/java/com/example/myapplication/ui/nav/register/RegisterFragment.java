package com.example.myapplication.ui.nav.register;

import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.databinding.FragmentRegisterBinding;
import com.example.myapplication.http.data.HttpBaseResponse;

public class RegisterFragment extends BaseFragment<FragmentRegisterBinding, RegisterViewModel> {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {

        mViewModel.getRegisterStatus().observe(this, new Observer<HttpBaseResponse<Object>>() {
            @Override
            public void onChanged(HttpBaseResponse<Object> bean) {
                if (bean.errorCode == 0) {
                    Toast.makeText(getContext(), getResources().getString(R.string.register_success), Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(RegisterFragment.this).navigateUp();
                } else {
                    Toast.makeText(getContext(), bean.errorMsg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
