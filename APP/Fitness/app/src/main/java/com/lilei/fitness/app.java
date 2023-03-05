package com.lilei.fitness;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.tencent.mmkv.MMKV;

import cn.bmob.v3.Bmob;
import tech.gujin.toast.ToastUtil;

public class app extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MMKV.initialize(this);
        ToastUtil.initialize(this);
        Bmob.initialize(this,"4c60ef9fa4d8ab126c81eb95f3e0bde3");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
