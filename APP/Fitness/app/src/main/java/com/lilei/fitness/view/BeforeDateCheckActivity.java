package com.lilei.fitness.view;

import android.os.Bundle;
import android.os.SystemClock;

import com.lilei.fitness.R;
import com.lilei.fitness.utils.Constants;
import com.lilei.fitness.view.base.BaseActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by djzhao on 17/05/04.
 */

public class BeforeDateCheckActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_before_date_check);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {

    }

    @Override
    protected void initView() {
        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
            }
        }.start();
    }
}
