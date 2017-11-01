package com.zy.diary.data.network;

import com.zy.diary.data.network.model.WeatherResponse;

import io.reactivex.Observable;

/**
 * Created by zhangyang on 2017/10/31.
 */
public interface ApiHelper {

    Observable<WeatherResponse> doWeatherCall(String city);

}
