package com.udacity.porfolioapp.dagger.component;

import com.udacity.porfolioapp.MainActivity;
import com.udacity.porfolioapp.dagger.module.AppModule;
import com.udacity.porfolioapp.dagger.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by clapj on 3/11/2016.
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(MainActivity activity);
}
