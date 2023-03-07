package com.lilei.fitness.view;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import okhttp3.Call;
import tech.gujin.toast.ToastUtil;

import android.content.Context;
import android.icu.lang.UScript;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lilei.fitness.R;
import com.lilei.fitness.bean.user;
import com.lilei.fitness.entity.User;
import com.lilei.fitness.utils.Constants;
import com.lilei.fitness.utils.MyDialogHandler;
import com.lilei.fitness.utils.SharedPreferencesUtils;
import com.lilei.fitness.view.base.BaseActivity;
import com.tencent.mmkv.MMKV;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

public class LoginActivity extends BaseActivity implements OnClickListener {
    private EditText et_username;
    private EditText et_password;

    private Button bt_login;
    private Button bt_register;
    private Context mContext;

    private MyDialogHandler uiFlusHandler;
    private String userId = MMKV.defaultMMKV().decodeString("userId");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        findViewById();
        initView();
    }

    private void login() {
        showLoadingDialog();
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        //d.判断用户名密码是否为空，不为空请求服务器（省略，默认请求成功）
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(mContext, "不可留空", Toast.LENGTH_SHORT).show();
            return;
        }
        user.loginByAccount(username, password, new LogInListener<user>() {
            @Override
            public void done(user user, BmobException e) {
                if (e == null) {
                    ToastUtil.show("登录成功");
                    MMKV.defaultMMKV().encode("userId", user.getObjectId());
                    openActivity(MainActivity.class);
                    finish();
                    return;
                } else {
                    ToastUtil.show("账号密码不正确或不存在用户");
                }
            }
        });
        /*        user user = new user();
        user.setUsername(username);
        user.setPassword(password);
        user.login(new SaveListener<user>() {
            @Override
            public void done(com.lilei.fitness.bean.user user, BmobException e) {
                hideLoadingDialog();
                if (e == null) {
                    ToastUtil.show("登录成功");
                    MMKV.defaultMMKV().encode("userId", user.getObjectId());
                    openActivity(MainActivity.class);
                    finish();
                    return;
                } else {
                    ToastUtil.show("账号密码不正确或不存在用户");
                }
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_bt_login:
                login();
                break;
            case R.id.login_bt_register:
                openActivity(RegisterActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            openActivity(ConfigActivity.class);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void findViewById() {
        et_username = $(R.id.login_et_username);
        et_password = $(R.id.login_et_password);

        bt_login = $(R.id.login_bt_login);
        bt_register = $(R.id.login_bt_register);
    }

    @Override
    protected void initView() {
        bt_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);
        if (TextUtils.isEmpty(userId)) {
            ToastUtil.show("请登录");
        } else {
            openActivity(MainActivity.class);
        }
    }

}
