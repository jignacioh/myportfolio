package com.udacity.porfolioapp.dagger.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by clapj on 3/11/2016.
 */
@Module
public class AppModule {
    Application application;

    public AppModule(Application application) {
        this.application = application;
    }
    @Provides
    @Singleton
    public Application getApplication(){
        return application;
    }
}
