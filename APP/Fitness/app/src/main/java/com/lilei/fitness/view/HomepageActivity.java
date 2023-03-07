package com.lilei.fitness.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lilei.fitness.R;
import com.lilei.fitness.bean.user;
import com.lilei.fitness.utils.Constants;
import com.lilei.fitness.utils.MyDialogHandler;
import com.lilei.fitness.utils.SharedPreferencesUtils;
import com.lilei.fitness.view.base.BaseActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import okhttp3.Call;
import tech.gujin.toast.ToastUtil;

/**
 * Created by djzhao on 17/05/01.
 */

public class HomepageActivity extends BaseActivity implements View.OnClickListener {

    private String TITLE_NAME = "关于我";
    private View title_back;
    private TextView titleText;

    private Context mContext;
    private TextView username;
    private RadioGroup sexGroup;
    private RadioButton maleRadio;
    private RadioButton femaleRadio;

    private EditText height;
    private EditText weight;

    private Button update;

    private String heightStr;
    private String weightStr;
    private String sex;

    private MyDialogHandler uiFlusHandler;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_homepage);
        findViewById();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getgUser();
    }

    @Override
    protected void findViewById() {
        this.title_back = $(R.id.title_back);
        this.titleText = $(R.id.titleText);
        sexGroup = $(R.id.homepage_radio_sex);
        update = $(R.id.homepage_btn_update);
        height = $(R.id.homepage_et_height);
        weight = $(R.id.homepage_et_weight);
        username = $(R.id.homepage_tv_username);

        maleRadio = $(R.id.homepage_rd_male);
        femaleRadio = $(R.id.homepage_rd_female);
    }

    private void getgUser() {
        showLoadingDialog();
        if (!TextUtils.isEmpty(userId)) {
            Log.e("test", "value:" + userId);
            BmobQuery<user> userDataBmobQuery = new BmobQuery<>();
            userDataBmobQuery.getObject(userId.trim(), new QueryListener<user>() {
                @Override
                public void done(user userData, BmobException e) {
                    hideLoadingDialog();
                    if (e == null) {
                        Log.e("test", "user:" + userData.toString());
                        height.setText(userData.getShengao());
                        weight.setText(userData.getTizhong());
                        String sex = userData.getSex();
                        switch (sex) {
                            case "男": {
                                maleRadio.setChecked(true);
                                femaleRadio.setChecked(false);
                                break;
                            }
                            case "女": {
                                maleRadio.setChecked(false);
                                femaleRadio.setChecked(true);
                                break;
                            }
                        }
                    } else {
                        ToastUtil.show("请联系管理员");
                    }
                }
            });
        }
    }

    @Override
    protected void initView() {
        mContext = this;
        this.titleText.setText(TITLE_NAME);

        this.title_back.setOnClickListener(this);
        update.setOnClickListener(this);
        uiFlusHandler = new MyDialogHandler(mContext, "更新中...");
        //echo();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back: {
                this.finish();
            }
            break;
            case R.id.homepage_btn_update:
                updateInfo();
                break;
        }
    }

    private void updateInfo() {
        showLoadingDialog();
        String h = height.getText().toString().trim();
        String w = weight.getText().toString().trim();
        String sex = "";
        if (maleRadio.isChecked()) {
            sex = "男";
        } else if (femaleRadio.isChecked()) {
            sex = "女";
        }
        Log.e("test", "value:" + userId);
        BmobUser currentUser = BmobUser.getCurrentUser();
        currentUser.setValue("shengao", h);
        currentUser.setValue("tizhong", w);
        currentUser.setValue("sex", sex);
        currentUser.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                hideLoadingDialog();
                if (e == null) {
                    ToastUtil.show("更新用户信息成功");
                    finish();
                } else {
                    ToastUtil.show("更新用户信息失败");
                }
            }
        });
    }
}
