package com.krish.app.playo.dependencies.components;

import com.krish.app.playo.dependencies.modules.AppModule;
import com.krish.app.playo.dependencies.modules.NetworkModule;
import com.krish.app.playo.dependencies.modules.ViewModelModule;
import com.krish.app.playo.features.home.home.HomeFragment;
import com.krish.app.playo.features.result.ResultFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class,
        ViewModelModule.class})
public interface AppComponent {

    void inject(HomeFragment homeFragment);
    void inject(ResultFragment resultFragment);
}
