package com.zy.diary.app;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

import butterknife.ButterKnife;

/**
 * Created by zhangyang on 2017/10/31.
 */

public class DiaryApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(this);
    }
}
