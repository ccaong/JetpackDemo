package com.example.myapplication.ui.nav.todo.todocontent;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.config.Constants;
import com.example.myapplication.databinding.FragmentToDoContentBinding;
import com.example.myapplication.bean.responsebean.ToDoListBean;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

/**
 * @author devel
 */
public class ToDoContentFragment extends BaseFragment<FragmentToDoContentBinding, ToDoContentViewModel> {

    private ToDoListBean.ToDoData data;

    @Override
    protected void handleArguments(Bundle args) {
        super.handleArguments(args);
        data = (ToDoListBean.ToDoData) args.getSerializable(Constants.ParamCode.PARAM1);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_to_do_content;
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(ToDoContentViewModel.class);
        mViewModel.setToDoData(data);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {
        initData();

        initViewChange();

        initDataChange();
    }

    private void initData() {
        //设置初始值
        mViewModel.getData().observe(this, toDoData -> {
            switch (toDoData.getType()) {
                case 1:
                    mDataBinding.radioGroup.check(R.id.btn1);
                    break;
                case 2:
                    mDataBinding.radioGroup.check(R.id.btn2);
                    break;
                case 3:
                    mDataBinding.radioGroup.check(R.id.btn3);
                    break;
                case 4:
                    mDataBinding.radioGroup.check(R.id.btn4);
                    break;
                default:
                    break;
            }
        });
    }

    private void initViewChange() {

        //是否完成
        mDataBinding.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mViewModel.status.postValue(1);
            } else {
                mViewModel.status.postValue(0);
            }
        });


        mDataBinding.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.btn1:
                    mViewModel.type.postValue(1);
                    break;
                case R.id.btn2:
                    mViewModel.type.postValue(2);
                    break;
                case R.id.btn3:
                    mViewModel.type.postValue(3);
                    break;
                case R.id.btn4:
                    mViewModel.type.postValue(4);
                    break;
                default:
                    break;
            }
        });
    }

    private void initDataChange() {
        mViewModel.getToDoChangeStatus().observe(this, object -> {
            if (object != null) {
                //数据更新成功或数据删除成功
                NavHostFragment.findNavController(ToDoContentFragment.this).navigateUp();
            }
        });
    }
}
