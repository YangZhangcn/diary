package com.zy.diary.data;

import com.zy.diary.data.db.DbHelper;
import com.zy.diary.data.network.ApiHelper;
import com.zy.diary.data.pref.PrefHelper;

/**
 * Created by zhangyang on 2017/10/31.
 * 数据管理接口
 */
public interface DataManager extends ApiHelper,DbHelper,PrefHelper {



}
