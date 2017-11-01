package com.zy.diary.data;

import android.content.Context;

import com.zy.diary.data.db.DbHelper;
import com.zy.diary.data.db.Diary;
import com.zy.diary.data.network.ApiHelper;
import com.zy.diary.data.network.model.WeatherResponse;
import com.zy.diary.data.pref.PrefHelper;
import com.zy.diary.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by zhangyang on 2017/11/1.
 */
@Singleton
public class DataManagerImpl implements DataManager {

    private static final String TAG = "DataManagerImpl";

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final PrefHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    @Inject
    public DataManagerImpl(@ApplicationContext Context mContext, DbHelper mDbHelper, PrefHelper mPreferencesHelper, ApiHelper mApiHelper) {
        this.mContext = mContext;
        this.mDbHelper = mDbHelper;
        this.mPreferencesHelper = mPreferencesHelper;
        this.mApiHelper = mApiHelper;
    }


    @Override
    public Observable<WeatherResponse> doWeatherCall(String city) {
        return mApiHelper.doWeatherCall(city);
    }

    @Override
    public void saveDiary(Diary diary) {
        mDbHelper.saveDiary(diary);
    }
}
