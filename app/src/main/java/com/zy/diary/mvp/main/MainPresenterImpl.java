package com.zy.diary.mvp.main;

import android.Manifest;
import android.content.Context;
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
import com.zy.diary.data.network.model.WeatherResponse;
import com.zy.diary.mvp.base.BasePresenter;

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

    public void getLocalAddress() {
        final LocationManager lm = (LocationManager) getMvpView().getContext().getSystemService(Context.LOCATION_SERVICE);
        String bestProvider = lm.getBestProvider(getCriteria(), false);
        if (ActivityCompat.checkSelfPermission(getMvpView().getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getMvpView().getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getMvpView().onError("权限缺失");
            return;
        }
        lm.requestLocationUpdates(bestProvider, 5000, 8, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String addressByGeoPoint = getAddressByGeoPoint(location.getLatitude(), location.getLongitude());
                getLocation(addressByGeoPoint);
                lm.removeUpdates(this);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });
    }

    //从经纬度取得Address
    public String getAddressByGeoPoint(double Latitude, double Longitude) {
        String strReturn = "";
        try {
	      /* 创建Geocoder对象，用于获得指定地点的地址 */
            Geocoder gc = new Geocoder(getMvpView().getContext(), Locale.getDefault());

	      /* 自经纬度取得地址（可能有多行）*/
            List<Address> lstAddress = gc.getFromLocation(Latitude, Longitude, 1);
            StringBuilder sb = new StringBuilder();

	      /* 判断地址是否为多行 */
            if (lstAddress.size() > 0) {
                Address adsLocation = lstAddress.get(0);
                sb.append(adsLocation.getLocality());  //当前经纬度所在的城市（市）
            }
            strReturn = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strReturn;
    }

    private Criteria getCriteria() {
        // TODO Auto-generated method stub
        Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_COARSE);
        c.setSpeedRequired(false);
        c.setCostAllowed(false);
        c.setBearingRequired(false);
        c.setAltitudeRequired(false);
        c.setPowerRequirement(Criteria.POWER_LOW);
        return c;
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
