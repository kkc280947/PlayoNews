package com.krish.app.playo.features.result;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.krish.app.playo.api.PlayoApi;
import com.krish.app.playo.data.models.ResultData;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Response;
import timber.log.Timber;

public class ResultViewModel extends ViewModel {

    @Inject
    PlayoApi playoApi;

    @Inject
    public ResultViewModel(){

    }

    public MutableLiveData<ResultData> getSearchResult(String query) {
        MutableLiveData<ResultData> resultList = new MutableLiveData<>();
        new Thread(() -> {

            Response<ResultData> updateData;
            try {
                updateData = playoApi.getSearchResults(query,0).execute();
                if (updateData.isSuccessful()) {
                    resultList.postValue(updateData.body());
                    Timber.d("success in api");
                } else {
                    Timber.d("error in api");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Timber.d("error in api");
            }
        }).start();
        return resultList;
    }
}
