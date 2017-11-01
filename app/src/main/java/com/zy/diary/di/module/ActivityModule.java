package com.zy.diary.di.module;

import android.app.Activity;
import android.content.Context;

import com.zy.diary.data.DataManager;
import com.zy.diary.data.DataManagerImpl;
import com.zy.diary.data.db.DbHelper;
import com.zy.diary.data.db.DbHelperImpl;
import com.zy.diary.data.network.ApiHelper;
import com.zy.diary.data.network.ApiHelperImpl;
import com.zy.diary.data.pref.PrefHelper;
import com.zy.diary.data.pref.PrefHelperImpl;
import com.zy.diary.di.ActivityContext;
import com.zy.diary.di.ActivityScope;
import com.zy.diary.di.ApplicationContext;
import com.zy.diary.mvp.main.MainPresenter;
import com.zy.diary.mvp.main.MainPresenterImpl;
import com.zy.diary.mvp.main.MainView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by zhangyang on 2017/11/1.
 */
@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    @ApplicationContext
    Context provideApplicationContext() {
        return mActivity.getApplicationContext();
    }

    @Provides
    CompositeDisposable provideCompositeDisposable(){
        return new CompositeDisposable();
    }

    @Provides
    @ActivityScope
    MainPresenter<MainView> provideMainPresenter(MainPresenterImpl<MainView> presenter) {
        return presenter;
    }

    @Provides
    DbHelper provideDbHelper(DbHelperImpl appDbHelper) {
        return appDbHelper;
    }

    @Provides
    ApiHelper provideApiHelper(ApiHelperImpl appDbHelper) {
        return appDbHelper;
    }

    @Provides
    DataManager provideDataManager(DataManagerImpl appDataManager) {
        return appDataManager;
    }

    @Provides
    PrefHelper providePrefHelper(PrefHelperImpl prefHelper){
        return prefHelper;
    }

}
