package com.krish.app.playo.features.result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.krish.app.playo.R;
import com.krish.app.playo.data.models.ResultData;
import com.krish.app.playo.databinding.FragmentResultBinding;
import com.krish.app.playo.features.application.base.BaseCallbackFragment;
import com.krish.app.playo.features.home.interfaces.IHomeActivityCallback;

public class ResultFragment extends BaseCallbackFragment<ResultViewModel, IHomeActivityCallback> {

    public static final String ARG_QUERY= "arg_query";

    FragmentResultBinding mFragmentResultBinding;

    public static ResultFragment newInstance(String query) {
        ResultFragment fragment = new ResultFragment();
        Bundle bundle= new Bundle();
        bundle.putString(ARG_QUERY,query);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentResultBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_result, container, false);
        getAppComponent().inject(this);
        initViewModel(ResultViewModel.class);
        initActivityCallback(IHomeActivityCallback.class);
        Bundle bundle = getArguments();

        if(bundle!=null){
            String query = bundle.getString(ARG_QUERY);
            getViewModel().getSearchResult(query).observe(getViewLifecycleOwner(), new Observer<ResultData>() {
                @Override
                public void onChanged(ResultData resultData) {
                    if(resultData!=null){

                    }
                }
            });

        }

        return mFragmentResultBinding.getRoot();
    }
}
