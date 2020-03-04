package com.example.myapplication.ui.nav.todo.addtodo;

import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.databinding.FragmentToDoAddBinding;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

/**
 * @author devel
 */
public class AddToDoFragment extends BaseFragment<FragmentToDoAddBinding, AddToDoViewModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_to_do_add;
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(AddToDoViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected void init() {

        mViewModel.getAdddata().observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object object) {
                Toast.makeText(getActivity(), "新增待办事项成功！", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(AddToDoFragment.this).navigateUp();
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
