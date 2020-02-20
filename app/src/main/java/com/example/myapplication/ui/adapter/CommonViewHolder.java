package com.example.myapplication.ui.adapter;


import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author devel
 */
public class CommonViewHolder extends RecyclerView.ViewHolder {

    ViewDataBinding binding;

    public CommonViewHolder(@NonNull ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
