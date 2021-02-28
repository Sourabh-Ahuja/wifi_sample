package com.sourabh.openapp.di;

import android.app.Application;

import com.sourabh.openapp.OpenAppApplication;
import com.sourabh.openapp.di.builder.ActivityBuilderModule;
import com.sourabh.openapp.di.module.AppModule;

import javax.inject.Singleton;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;


@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                AppModule.class,
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
