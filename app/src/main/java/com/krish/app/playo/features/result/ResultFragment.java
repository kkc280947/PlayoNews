package com.krish.app.playo.features.result;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.krish.app.playo.R;
import com.krish.app.playo.data.models.Hit;
import com.krish.app.playo.databinding.FragmentResultBinding;
import com.krish.app.playo.features.application.base.BaseCallbackFragment;
import com.krish.app.playo.features.home.interfaces.IHomeActivityCallback;
import com.krish.app.playo.features.web.EndlessRecyclerViewScrollListener;
import com.krish.app.playo.utils.Utilities;

import java.util.List;

public class ResultFragment extends BaseCallbackFragment<ResultViewModel, IHomeActivityCallback> {

    public static final String ARG_QUERY= "arg_query";

    FragmentResultBinding mFragmentResultBinding;

    private ResultAdapter resultAdapter;

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
        initRecycler();
        observerResults();
        return mFragmentResultBinding.getRoot();
    }

    private void observerResults() {
        Bundle bundle = getArguments();
        if(bundle!=null){
            String query = bundle.getString(ARG_QUERY);
            getViewModel().setQuery(query);
            getViewModel().getSearchResult(query,0).observe(getViewLifecycleOwner(), resultData -> {
                if(resultData!=null){
                    List<Hit> hits = resultData.getHits();
                    if(!Utilities.isNullOrEmpty(hits)){
                        getViewModel().setPage(resultData.getPage());
                        resultAdapter.addResult(hits);
                    }
                }
            });
        }
    }

    private void initRecycler() {
        resultAdapter = new ResultAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mFragmentResultBinding.recyclerView.setLayoutManager(linearLayoutManager);
        mFragmentResultBinding.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view){
            }

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view, int lastVisibleItem){
                int number = getViewModel().getPage()+1;
                getViewModel().getSearchResult(getViewModel().getQuery(),number).observe(getViewLifecycleOwner(), resultData -> {
                    if(resultData!=null){
                        List<Hit> hits = resultData.getHits();
                        if(!Utilities.isNullOrEmpty(hits)){
                            getViewModel().setPage(resultData.getPage());
                            resultAdapter.addNewResult(hits);
                        }
                    }
                });
            }
        });

        mFragmentResultBinding.recyclerView.setAdapter(resultAdapter);
    }
}
