package com.zy.diary.mvp.base;

import com.androidnetworking.error.ANError;

/**
 * Created by zhangyang on 2017/10/30.
 */
public interface MvpPresenter<V extends MvpView> {
    void onAttach(V mvpView);

    void onDetach();

    void handleApiError(ANError error);
}
