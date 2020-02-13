package com.example.androiddaggerservice.data.di.modules;

import android.content.Context;

import com.example.androiddaggerservice.app.App;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;


@Module
public abstract class ApplicationModule {


    @Singleton
    @Binds
    abstract Context provideContext(App app);


}