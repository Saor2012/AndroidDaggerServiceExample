package com.example.androiddaggerservice.data.di.component;

import com.example.androiddaggerservice.app.App;
import com.example.androiddaggerservice.data.di.modules.ApplicationModule;
import com.example.androiddaggerservice.data.di.modules.BuildersModuleActivity;
import com.example.androiddaggerservice.data.di.modules.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
    ApplicationModule.class,
    BuildersModuleActivity.class,
    ServiceModule.class})
public interface AppDaggerComponent extends AndroidInjector<App> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App>{}
}
