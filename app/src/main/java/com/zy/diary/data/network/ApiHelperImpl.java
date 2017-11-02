package com.zy.diary.data.network;

import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.zy.diary.data.network.model.WeatherResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by zhangyang on 2017/11/1.
 */
@Singleton
public class ApiHelperImpl implements ApiHelper {

    @Inject
    public ApiHelperImpl() {
    }

    @Override
    public Observable<WeatherResponse> doWeatherCall(String city) {
        return Rx2AndroidNetworking.get("http://www.sojson.com/open/api/weather/json.shtml")
                .addQueryParameter("city",city)
                .build()
                .getObjectObservable(WeatherResponse.class);
    }
}
