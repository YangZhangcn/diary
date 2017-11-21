package com.zy.diary.data.pref;

import com.zy.diary.data.network.model.WeatherResponse;

/**
 * Created by zhangyang on 2017/11/1.
 */

public interface PrefHelper {
    String PREF_NAME = "diary_pref";
    String LOCAL_CITY_NAME = "local_city_name";
    void saveLocalCity(String city);
    String getLocalCity();
}
