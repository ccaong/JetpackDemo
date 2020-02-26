package com.example.myapplication.ui.nav.todo.todocontent;

import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.databinding.FragmentToDoContentBinding;
import com.example.myapplication.http.bean.ToDoListBean;
import com.example.myapplication.ui.nav.todo.ToDoViewModel;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * @author devel
 */
public class ToDoContentFragment extends BaseFragment<FragmentToDoContentBinding, ToDoViewModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_to_do_content;
    }

    @Override
    protected void initViewModel() {

        mViewModel = ViewModelProviders.of(getActivity()).get(ToDoViewModel.class);
    }

    @Override
    protected void bindViewModel() {

        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {
        initData();

        initViewChange();
    }

    private void initData() {
        //设置初始值
        mViewModel.getData().observe(this, new Observer<ToDoListBean.ToDoData>() {
            @Override
            public void onChanged(ToDoListBean.ToDoData toDoData) {
                Log.e("TODO", "数据改变");
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
            }
        });
    }


    private void initViewChange() {

        //是否完成
        mDataBinding.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mViewModel.status.postValue(1);
                } else {
                    mViewModel.status.postValue(0);
                }
            }
        });


        mDataBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
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
            }
        });
    }
}
