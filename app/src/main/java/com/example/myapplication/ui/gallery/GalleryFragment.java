package com.example.myapplication.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.entity.WeChatBean;
import com.example.myapplication.ui.adapter.CommonAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author devel
 */
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

        final View root = inflater.inflate(R.layout.fragment_share, container, false);
        mRecycleView = root.findViewById(R.id.recycle);

        galleryViewModel.loadWeChatList();

        return root;
    }

}