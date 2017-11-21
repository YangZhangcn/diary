package com.zy.diary.mvp.base;

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

import com.androidnetworking.common.ANConstants;
import com.androidnetworking.error.ANError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.zy.diary.R;
import com.zy.diary.data.DataManager;
import com.zy.diary.data.network.model.ApiError;
import com.zy.diary.utils.AppConstants;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by zhangyang on 2017/10/31.
 */

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private static final String TAG = "BasePresenter";

    private V mMvpView;

    private final DataManager mDataManager;

    private final CompositeDisposable mCompositeDisposable;

    @Inject
    public BasePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        this.mDataManager = dataManager;
        this.mCompositeDisposable = compositeDisposable;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    @Override
    public void onDetach() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }


    @Override
    public void handleApiError(ANError error) {
        //没有拿到错误信息  提示默认信息
        if (error == null || error.getErrorBody() == null) {
            getMvpView().onError(R.string.api_default_error);
            return;
        }
        //连接错误
        if (error.getErrorCode() == AppConstants.API_STATUS_CODE_LOCAL_ERROR
                && error.getErrorDetail().equals(ANConstants.CONNECTION_ERROR)) {
            getMvpView().onError(R.string.connection_error);
            return;
        }
        //连接中断
        if (error.getErrorCode() == AppConstants.API_STATUS_CODE_LOCAL_ERROR
                && error.getErrorDetail().equals(ANConstants.REQUEST_CANCELLED_ERROR)) {
            getMvpView().onError(R.string.api_retry_error);
            return;
        }

        final GsonBuilder builder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
        final Gson gson = builder.create();

        try {
            ApiError apiError = gson.fromJson(error.getErrorBody(), ApiError.class);

            if (apiError == null || apiError.getMessage() == null) {
                getMvpView().onError(R.string.api_default_error);
                return;
            }

            switch (error.getErrorCode()) {
//                case HttpsURLConnection.HTTP_UNAUTHORIZED:
//                case HttpsURLConnection.HTTP_FORBIDDEN:
//                    setUserAsLoggedOut();
//                    getMvpView().openActivityOnTokenExpire();
                case HttpsURLConnection.HTTP_INTERNAL_ERROR:
                case HttpsURLConnection.HTTP_NOT_FOUND:
                default:
                    getMvpView().onError(apiError.getMessage());
            }
        } catch (JsonSyntaxException | NullPointerException e) {
            Log.e(TAG, "handleApiError", e);
            getMvpView().onError(R.string.api_default_error);
        }

    }

    /**获取本地地址 如过能获取到货回调 #onLocationChange()
     *
     */
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
                onLocationChange(addressByGeoPoint);
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

    public void onLocationChange(String city){}

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


}
