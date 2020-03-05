package com.example.androiddaggerservice.data.di.modules;

import com.example.androiddaggerservice.data.ServicePos;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServiceModule {
    @ContributesAndroidInjector()
    abstract ServicePos bindServicePos();
}
