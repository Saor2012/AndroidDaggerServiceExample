package com.example.androiddaggerservice.data.di.modules;

import com.example.androiddaggerservice.presentation.IMainPresenter;
import com.example.androiddaggerservice.presentation.MainActivity;
import com.example.androiddaggerservice.presentation.MainPresenter;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MainModule {
    @Binds
    abstract IMainPresenter.View bindMainView(MainActivity view);

    @Binds
    abstract IMainPresenter.Presenter bindMainPresenter(MainPresenter presenter);
}
