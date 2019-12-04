package com.krish.app.playo.dependencies.modules;

import androidx.lifecycle.ViewModel;

import com.krish.app.playo.features.home.HomeViewModel;
import com.krish.app.playo.features.result.ResultViewModel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dagger.Binds;
import dagger.MapKey;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Documented
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    private @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ResultViewModel.class)
    abstract ViewModel bindResultViewModel(ResultViewModel resultViewModel);
}
