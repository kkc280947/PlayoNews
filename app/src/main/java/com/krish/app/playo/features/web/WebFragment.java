package com.krish.app.playo.features.web;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.krish.app.playo.R;
import com.krish.app.playo.databinding.FragmentWebBinding;
import com.krish.app.playo.features.application.base.BaseFragment;

public class WebFragment extends BaseFragment{
    FragmentWebBinding fragmentWebBinding;
    public static final String ARG_URL= "arg_url";

    public static WebFragment newInstance(String query) {
        WebFragment fragment = new WebFragment();
        Bundle bundle= new Bundle();
        bundle.putString(ARG_URL,query);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentWebBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_web, container, false);

        return fragmentWebBinding.getRoot();
    }

}
