package com.zy.diary.mvp.main;

import android.util.Log;

import com.zy.diary.data.DataManager;
import com.zy.diary.data.network.model.WeatherResponse;
import com.zy.diary.mvp.base.BasePresenter;

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
    public void getLocation(String city) {
        Log.i("main,getLocation",city);
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
