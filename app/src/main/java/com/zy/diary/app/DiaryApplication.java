package com.zy.diary.app;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.zy.diary.data.DataManager;
import com.zy.diary.di.component.ApplicationComponent;
import com.zy.diary.di.component.DaggerApplicationComponent;
import com.zy.diary.di.module.ApplicationModule;

import javax.inject.Inject;

/**
 * Created by zhangyang on 2017/10/31.
 */

public class DiaryApplication extends Application {

    ApplicationComponent applicationComponent;

    @Inject
    DataManager mDataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(this);
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
