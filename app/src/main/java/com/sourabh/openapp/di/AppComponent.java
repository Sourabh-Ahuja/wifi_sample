package com.sourabh.openapp.di;

import android.app.Application;

import com.sourabh.openapp.OpenAppApplication;
import com.sourabh.openapp.di.builder.ActivityBuilderModule;
import com.sourabh.openapp.di.module.AppModule;
import com.sourabh.openapp.di.module.NetworkModule;

import javax.inject.Singleton;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * dagger AppComponent
 * */
@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                AppModule.class,
                NetworkModule.class,
                ActivityBuilderModule.class,
        }
)
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }

    void inject(OpenAppApplication sampleMovieApp);

}
