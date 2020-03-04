package com.example.myapplication.ui.nav.update;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.R;
import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.bean.UpdatePlan;

import java.util.ArrayList;
import java.util.List;


public class UpdateViewModel extends BaseViewModel {

    private MutableLiveData<List<UpdatePlan>> updatePlanList;


    public UpdateViewModel() {
        updatePlanList = new MutableLiveData<>();
        initUpdatePlanList();
    }

    public LiveData<List<UpdatePlan>> getUpdateList() {
        return updatePlanList;
    }

    private void initUpdatePlanList() {
        List<UpdatePlan> mList = new ArrayList<>();
        mList.add(new UpdatePlan(getResources().getString(R.string.update_plan_1), false));
        mList.add(new UpdatePlan(getResources().getString(R.string.update_plan_2), false));
        mList.add(new UpdatePlan(getResources().getString(R.string.update_plan_3), false));
        mList.add(new UpdatePlan(getResources().getString(R.string.update_plan_4), false));
        mList.add(new UpdatePlan(getResources().getString(R.string.update_plan_5), false));
        mList.add(new UpdatePlan(getResources().getString(R.string.update_plan_6), false));

        updatePlanList.postValue(mList);
    }


}
