package com.krish.app.playo.features.home.home;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krish.app.playo.R;
import com.krish.app.playo.databinding.FragmentHomeBinding;
import com.krish.app.playo.features.application.base.BaseCallbackFragment;
import com.krish.app.playo.features.home.HomeViewModel;
import com.krish.app.playo.features.home.interfaces.IHomeActivityCallback;

public class HomeFragment extends BaseCallbackFragment<HomeViewModel, IHomeActivityCallback> {

    private FragmentHomeBinding mFragmentHomeBinding;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentHomeBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false);

        getAppComponent().inject(this);

        initViewModel(HomeViewModel.class);
        initActivityCallback(IHomeActivityCallback.class);
        return mFragmentHomeBinding.getRoot();
    }
}
