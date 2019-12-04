package com.krish.app.playo.features.result;

import android.content.Context;
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
import com.krish.app.playo.utils.EndlessRecyclerViewScrollListener;
import com.krish.app.playo.utils.Utilities;

import java.util.List;

public class ResultFragment extends BaseCallbackFragment<ResultViewModel, IHomeActivityCallback> implements ResultAdapter.OnListItemClicked {

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
        if(mFragmentResultBinding==null){
            mFragmentResultBinding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_result, container, false);
            getAppComponent().inject(this);
            initViewModel(ResultViewModel.class);
            initActivityCallback(IHomeActivityCallback.class);
            initRecycler();
            observerResults();
        }
        return mFragmentResultBinding.getRoot();
    }

    private void observerResults() {
        Bundle bundle = getArguments();
        if(bundle!=null){
            String query = bundle.getString(ARG_QUERY);
            getViewModel().setQuery(query);
            mFragmentResultBinding.textResults.setText(String.format("Showing results for: %s", query));
            getViewModel().getSearchResult(query,0).observe(getViewLifecycleOwner(), resultData -> {
                if(resultData!=null){
                    List<Hit> hits = resultData.getHits();
                    if(!Utilities.isNullOrEmpty(hits)){
                        mFragmentResultBinding.recyclerView.setVisibility(View.VISIBLE);
                        getViewModel().setPage(resultData.getPage());
                        resultAdapter.addResult(hits);
                    }else {
                        mFragmentResultBinding.recyclerView.setVisibility(View.GONE);
                        mFragmentResultBinding.textNoData.setVisibility(View.VISIBLE);
                    }
                }else {
                    mFragmentResultBinding.textNoData.setVisibility(View.VISIBLE);
                }
                mFragmentResultBinding.progressBar.setVisibility(View.GONE);
            });
        }
    }

    private void initRecycler() {
        resultAdapter = new ResultAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mFragmentResultBinding.recyclerView.setLayoutManager(linearLayoutManager);
        mFragmentResultBinding.recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view){
            }

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view, int lastVisibleItem){
                Context context = getContext();
                if(context!=null){
                    if(Utilities.checkInternetConnection(context)){
                        int number = getViewModel().getPage()+1;
                        mFragmentResultBinding.progressBar.setVisibility(View.VISIBLE);
                        getViewModel().getSearchResult(getViewModel().getQuery(),number).observe(getViewLifecycleOwner(), resultData -> {
                            if(resultData!=null){
                                List<Hit> hits = resultData.getHits();
                                if(!Utilities.isNullOrEmpty(hits)) {
                                    getViewModel().setPage(resultData.getPage());
                                    resultAdapter.addNewResult(hits);
                                }
                            }
                            mFragmentResultBinding.progressBar.setVisibility(View.GONE);
                        });
                    }
                }

            }
        });

        mFragmentResultBinding.recyclerView.setAdapter(resultAdapter);
    }

    @Override
    public void openUrl(String url, String title) {
        getActivityCallback().moveToWebPage(url,title);
    }
}
