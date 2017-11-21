package com.zy.diary.mvp.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.zy.diary.data.DataManager;
import com.zy.diary.data.db.model.Diary;
import com.zy.diary.data.network.model.WeatherResponse;
import com.zy.diary.mvp.base.BasePresenter;
import com.zy.diary.mvp.edit.EditDiaryActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangyang on 2017/11/1.
 */

public class MainPresenterImpl<V extends MainView>extends BasePresenter<V> implements MainPresenter<V> {

    @Inject
    public MainPresenterImpl(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
        getDiaries();
        if(getMvpView().hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION) && getMvpView().hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)){
            getLocalAddress();
        }else {
            getMvpView().requestPermissionsSafely(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},200);
        }
    }

    @Override
    public void onPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (getMvpView().hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION) && getMvpView().hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            getMvpView().onError("权限缺失");
            return;
        }
        getLocalAddress();
    }

    @Override
    public void onLocationChange(String city) {
        getDataManager().saveLocalCity(city);
//        getWeather(city);
    }

    @Override
    public List<Diary> getDiaries() {
        List<Diary> diaries = new ArrayList<>();
        for (int i=0;i<5;i++){
            //long id, String date, String weather, String content, String tags,String title
            diaries.add(new Diary(i,"20171121","晴","内容","标签","标题"));
        }
        if (diaries != null && diaries.size()>0){
            getMvpView().hideEmptyTip();
            getMvpView().showDiaries(diaries);
        }else {
            getMvpView().showEmptyTip("还没有写过日记哦");
        }
        return diaries;
    }

    @Override
    public void gotoEditPage(Diary diary) {
        getMvpView().getContext().startActivity(new Intent(getMvpView().getContext(), EditDiaryActivity.class));
    }

    @Override
    public List<Diary> searchDairy(String info, int type) {
        return null;
    }

    @Override
    public void getWeather(String city) {
        Log.i("main,getWeather",city);
        getCompositeDisposable().add(getDataManager()
                .doWeatherCall(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResponse>() {
                    @Override
                    public void accept(WeatherResponse weatherResponse) throws Exception {
                        Log.i("mainPresenter",weatherResponse.getMessage());
                        getMvpView().onError(weatherResponse.getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }));
    }

}
