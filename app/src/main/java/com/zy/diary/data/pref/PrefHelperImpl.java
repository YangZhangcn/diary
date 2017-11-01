package com.zy.diary.data.pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.zy.diary.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by zhangyang on 2017/11/1.
 */
@Singleton
public class PrefHelperImpl implements PrefHelper {

    private final SharedPreferences mPrefs;

    @Inject
    public PrefHelperImpl(@ApplicationContext Context context) {
        mPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
}
