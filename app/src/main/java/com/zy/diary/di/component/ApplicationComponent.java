package com.zy.diary.di.component;

import android.app.Application;
import android.content.Context;

import com.zy.diary.app.DiaryApplication;
import com.zy.diary.data.DataManager;
import com.zy.diary.di.ApplicationContext;
import com.zy.diary.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by zhangyang on 2017/11/1.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(DiaryApplication app);

    @ApplicationContext
    Context context();

    Application application();

    //需要将获取DataManager的方法通过接口暴露给activityComponent
    DataManager getDataManager();

}
