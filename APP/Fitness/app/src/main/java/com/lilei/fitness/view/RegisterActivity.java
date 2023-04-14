package com.lilei.fitness.view;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import okhttp3.Call;
import tech.gujin.toast.ToastUtil;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lilei.fitness.R;
import com.lilei.fitness.bean.user;
import com.lilei.fitness.entity.User;
import com.lilei.fitness.utils.AppManager;
import com.lilei.fitness.utils.Constants;
import com.lilei.fitness.utils.MyDialogHandler;
import com.lilei.fitness.utils.SharedPreferencesUtils;
import com.lilei.fitness.view.base.BaseActivity;
import com.tencent.mmkv.MMKV;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Objects;

public class RegisterActivity extends BaseActivity implements OnClickListener {
    private String TITLE_NAME = "注册";
    private View title_back;
    private TextView titleText;

    private Context mContext;
    private EditText et_username;
    private EditText et_password;
    private EditText et_repassword;
    private EditText et_height;
    private EditText et_weight;
    private Button register_login;
    private RadioGroup radio_sex;

    private MyDialogHandler uiFlusHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById();
        initView();
    }

    @Override
    protected void initView() {
        mContext = this;
        this.title_back.setOnClickListener(this);
        this.titleText.setText(TITLE_NAME);
        this.register_login.setOnClickListener(this);

        uiFlusHandler = new MyDialogHandler(mContext, "正在注册...");

    }

    @Override
    protected void findViewById() {
        this.title_back = $(R.id.title_back);
        this.titleText = $(R.id.titleText);

        et_username = $(R.id.reg_et_username);
        et_password = $(R.id.reg_et_password);
        et_repassword = $(R.id.reg_et_repassword);
        et_height = $(R.id.reg_et_height);
        et_weight = $(R.id.reg_et_weight);

        this.radio_sex = (RadioGroup) findViewById(R.id.radio_sex);
        this.register_login = (Button) findViewById(R.id.reg_btn_register);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back: {
                this.finish();
            }
            break;
            case R.id.reg_btn_register:
                register();
                break;
        }
    }

    private void register() {

        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String repassword = et_repassword.getText().toString().trim();
        String height = et_height.getText().toString().trim();
        String weight = et_weight.getText().toString().trim();
        String sex = "女";
        if (radio_sex.getCheckedRadioButtonId() == R.id.reg_rd_male) {
            sex = "男";
        }

        // 判断两次密码
        if (!password.equals(repassword)) {
            ToastUtil.show("两次密码输入不一致");
            return;
        }

        if (TextUtils.isEmpty(username)) {
            ToastUtil.show("用户名不可为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.show("密码不可为空");
            return;
        }
        if (TextUtils.isEmpty(repassword)) {
            ToastUtil.show("两次密码不一致");
            return;
        }
        if (TextUtils.isEmpty(height)) {
            ToastUtil.show("身高不可为空");
            return;
        }
        if (TextUtils.isEmpty(weight)) {
            ToastUtil.show("体重不可为空");
            return;
        }
        showLoadingDialog();
        user userData = new user(height, weight, sex);
        userData.setUsername(username);
        userData.setPassword(password);
        userData.signUp(new SaveListener<user>() {
            @Override
            public void done(user user, BmobException e) {
                hideLoadingDialog();
                if (e == null) {
                    Objects.requireNonNull(MMKV.defaultMMKV()).encode("userId", user.getObjectId());
                    openActivity(LoginActivity.class);
                } else {
                    if (e.getMessage().contains("already taken.")) {
                        ToastUtil.show("该用户名已存在");
                    } else {
                        ToastUtil.show("创建数据失败：" + e.getMessage());
                    }
                }
            }
        });
    }


}
