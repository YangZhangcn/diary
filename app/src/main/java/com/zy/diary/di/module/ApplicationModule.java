package com.zy.diary.di.module;

import android.app.Application;
import android.content.Context;

import com.zy.diary.data.DataManager;
import com.zy.diary.data.DataManagerImpl;
import com.zy.diary.data.db.DbHelper;
import com.zy.diary.data.db.DbHelperImpl;
import com.zy.diary.data.network.ApiHelper;
import com.zy.diary.data.network.ApiHelperImpl;
import com.zy.diary.data.pref.PrefHelper;
import com.zy.diary.data.pref.PrefHelperImpl;
import com.zy.diary.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by zhangyang on 2017/11/1.
 */
@Module
public class ApplicationModule {

    private Application mApplication;

    public ApplicationModule(Application application) {
        this.mApplication = application;
    }

//    @Provides
//    @ApplicationContext
//    Context provideContext() {
//        return mApplication;
//    }

}


