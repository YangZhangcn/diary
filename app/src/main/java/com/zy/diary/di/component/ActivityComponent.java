package com.zy.diary.di.component;

import com.zy.diary.app.DiaryApplication;
import com.zy.diary.di.ActivityScope;
import com.zy.diary.di.module.ActivityModule;
import com.zy.diary.di.module.ApplicationModule;
import com.zy.diary.mvp.main.DiaryItemHolder;
import com.zy.diary.mvp.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by zhangyang on 2017/11/1.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class , modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity act);
}
