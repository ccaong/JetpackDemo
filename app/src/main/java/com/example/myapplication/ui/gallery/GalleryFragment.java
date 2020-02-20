package com.example.myapplication.ui.gallery;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentGalleryBinding;
import com.example.myapplication.requestbean.Data;
import com.example.myapplication.requestbean.ImageBean;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding mBinding;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false);
        init();
        return mBinding.getRoot();


//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(root).navigate(R.id.nav_slideshow);
//            }
//        });
    }

    private void init() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("加载中");

        galleryViewModel.getImage().observe(this, new Observer<Data<ImageBean.ImagesBean>>() {
            @Override
            public void onChanged(@Nullable Data<ImageBean.ImagesBean> imagesBeanData) {
                if (imagesBeanData.getErrorMsg() != null) {
                    Toast.makeText(getActivity(), imagesBeanData.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                    return;
                }
                mBinding.setImageBean(imagesBeanData.getData());
                mProgressDialog.dismiss();
            }
        });

        mBinding.setPresenter(new Presenter());

        mProgressDialog.show();
        galleryViewModel.loadImage();
    }

    public class Presenter {

        public void onClick(View view) {
            mProgressDialog.show();
            switch (view.getId()) {
                case R.id.btn_load:
                    galleryViewModel.loadImage();
                    break;
                case R.id.btn_previous:
                    galleryViewModel.previousImage();
                    break;
                case R.id.btn_next:
                    galleryViewModel.nextImage();
                    break;
                default:
                    break;
            }
        }

    }

}