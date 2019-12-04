package com.krish.app.playo.api;

import com.krish.app.playo.data.models.ResultData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlayoApi {

    @GET("search")
    Call<ResultData> getSearchResults(@Query("query") String query,@Query("page") int page);
}
