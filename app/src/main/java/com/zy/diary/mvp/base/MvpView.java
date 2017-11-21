package com.zy.diary.mvp.base;

import android.content.Context;
import android.support.annotation.StringRes;

/**
 * Created by zhangyang on 2017/10/30.
 * View接口
 */
public interface MvpView {
    void showLoading();

    void hideLoading();

    void onError(@StringRes int resId);

    void onError(String message);

    boolean isNetworkConnected();

    void hideKeyboard();

    boolean hasPermission(String permission);

    void requestPermissionsSafely(String[] permissions, int requestCode);

    Context getContext();


}
