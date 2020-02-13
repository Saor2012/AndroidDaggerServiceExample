package com.example.androiddaggerservice.data.di.modules;

import com.example.androiddaggerservice.presentation.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModuleActivity {

    @ContributesAndroidInjector(modules = {MainModule.class})
    abstract MainActivity bindMainActivity();
}
