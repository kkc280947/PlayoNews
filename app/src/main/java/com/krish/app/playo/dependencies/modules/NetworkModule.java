package com.krish.app.playo.dependencies.modules;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.krish.app.playo.BuildConfig;
import com.krish.app.playo.api.PlayoApi;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {


    private final long CACHE_SIZE = 10L * 1024 * 1024;
    private final long REQUEST_TIMEOUT = 30L;

    @Singleton
    @Provides
    Gson providesGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();

        return gsonBuilder.create();
    }

    @Singleton
    @Provides
    Retrofit providesRetrofitClient(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl("https://api.medicinescomplete.io/api/v2/")
                .build();
    }

    @Singleton
    @Provides
    OkHttpClient providesOkHttpClient(Context context, HttpLoggingInterceptor httpLoggingInterceptor) {

        OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder()
                        .cache(new Cache(context.getCacheDir(), CACHE_SIZE))
                        .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(httpLoggingInterceptor);
        }

        return httpClient.build();
    }

    @Singleton
    @Provides
    PlayoApi provideAPIServices(Retrofit retrofit) {
        return retrofit.create(PlayoApi.class);
    }

    @Singleton
    @Provides
    HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor();
    }

}
