package com.example.myapplication.ui.nav.about;

import com.example.myapplication.R;
import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.bean.GitHubProject;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author ccaong
 */
public class AboutViewModel extends BaseViewModel {

    private MutableLiveData<List<GitHubProject>> projectList;


    public AboutViewModel() {
        projectList = new MutableLiveData<>();
        initProjectList();
    }

    public LiveData<List<GitHubProject>> getProjectList() {
        return projectList;
    }


    private void initProjectList() {
        List<GitHubProject> mList = new ArrayList<>();

        mList.add(new GitHubProject(getResources().getString(R.string.thank_list_1), getResources().getString(R.string.thank_list_1_info), getResources().getString(R.string.thank_list_address)));
        mList.add(new GitHubProject(getResources().getString(R.string.thank_list_２), getResources().getString(R.string.thank_list_２_info), getResources().getString(R.string.thank_list_２_address)));
        mList.add(new GitHubProject(getResources().getString(R.string.thank_list_rxjava), getResources().getString(R.string.thank_list_rxjava_info), getResources().getString(R.string.thank_list_rxjava_address)));
        mList.add(new GitHubProject(getResources().getString(R.string.thank_list_retrofit), getResources().getString(R.string.thank_retrofit_info), getResources().getString(R.string.thank_retrofit_address)));
        mList.add(new GitHubProject(getResources().getString(R.string.thank_list_glide), getResources().getString(R.string.thank_list_glide_info), getResources().getString(R.string.thank_list_glide_address)));
        mList.add(new GitHubProject(getResources().getString(R.string.thank_glide_transformations), getResources().getString(R.string.thank_glide_transformations_info), getResources().getString(R.string.thank_glide_transformations_address)));
        mList.add(new GitHubProject(getResources().getString(R.string.thank_flexbox), getResources().getString(R.string.thank_flexbox_info), getResources().getString(R.string.thank_flexbox_address)));
        mList.add(new GitHubProject(getResources().getString(R.string.thank_hawk), getResources().getString(R.string.thank_hawk_info), getResources().getString(R.string.thank_hawk_address)));
        mList.add(new GitHubProject(getResources().getString(R.string.thank_qmui), getResources().getString(R.string.thank_qmui_info), getResources().getString(R.string.thank_qmui_address)));
        mList.add(new GitHubProject(getResources().getString(R.string.thank_AgentWeb), getResources().getString(R.string.thank_AgentWeb_info), getResources().getString(R.string.thank_AgentWeb_address)));
        mList.add(new GitHubProject(getResources().getString(R.string.thank_smartRefreshLayout), getResources().getString(R.string.thank_smartRefreshLayout_info), getResources().getString(R.string.thank_smartRefreshLayout_address)));
        mList.add(new GitHubProject(getResources().getString(R.string.thank_phoenix), getResources().getString(R.string.thank_phoenix_info), getResources().getString(R.string.thank_phoenix_address)));
        mList.add(new GitHubProject(getResources().getString(R.string.thank_VerticalTabLayout), getResources().getString(R.string.thank_VerticalTabLayout_info), getResources().getString(R.string.thank_VerticalTabLayout_address)));
        mList.add(new GitHubProject(getResources().getString(R.string.thank_banner), getResources().getString(R.string.thank_banner_info), getResources().getString(R.string.thank_banner_address)));
        mList.add(new GitHubProject(getResources().getString(R.string.thank_poptab), getResources().getString(R.string.thank_poptab_info), getResources().getString(R.string.thank_poptab_address)));
        projectList.postValue(mList);
    }
}