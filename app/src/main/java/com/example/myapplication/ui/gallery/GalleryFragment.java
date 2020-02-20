package com.example.myapplication.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.entity.WeChatBean;
import com.example.myapplication.entity.WeChatListEntity;
import com.example.myapplication.ui.adapter.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GalleryFragment extends Fragment {

    private RecyclerView mRecycleView;
    private CommonAdapter<WeChatBean> mAdapter;
    private List<WeChatBean> list;
    private GalleryViewModel galleryViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_list, container, false);
        mRecycleView = root.findViewById(R.id.recycle);

        galleryViewModel.loadWeChatList();
        galleryViewModel.getWechatList().observe(this, new Observer<WeChatListEntity>() {
            @Override
            public void onChanged(WeChatListEntity wechatListEntity) {
                mAdapter.onItemDatasChanged(wechatListEntity.getData());
            }
        });

        initRecycleView();

        return root;
    }

    private void initRecycleView() {
        list = new ArrayList<>();
        mAdapter = new CommonAdapter<WeChatBean>(
                list, R.layout.item_wechat_list, com.example.myapplication.BR.wechat) {
            @Override
            public void addListener(View root, WeChatBean itemData, int position) {
                super.addListener(root, itemData, position);
                root.findViewById(R.id.ll_wechat).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        };
        mRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycleView.setAdapter(mAdapter);
    }
}