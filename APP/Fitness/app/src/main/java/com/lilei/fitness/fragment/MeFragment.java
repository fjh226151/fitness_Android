package com.lilei.fitness.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lilei.fitness.R;
import com.lilei.fitness.bean.user;
import com.lilei.fitness.entity.User;
import com.lilei.fitness.utils.AppManager;
import com.lilei.fitness.utils.Constants;
import com.lilei.fitness.utils.SharedPreferencesUtils;
import com.lilei.fitness.view.BeforeDateCheckActivity;
import com.lilei.fitness.view.CommentsListActivity;
import com.lilei.fitness.view.FavorsListActivity;
import com.lilei.fitness.view.HomepageActivity;
import com.lilei.fitness.view.LoginActivity;
import com.lilei.fitness.view.base.BaseActivity;
import com.tencent.mmkv.MMKV;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jetbrains.annotations.Nullable;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import okhttp3.Call;
import tech.gujin.toast.ToastUtil;

/**
 * Created by djzhao on 17/04/30.
 */

public class MeFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout homepage;
    private LinearLayout comment;
    private LinearLayout record;
    private LinearLayout favor;

    private TextView usernameTV;
    private TextView exerciseTimeTextView;
    private TextView recordDaysTextView;
    private TextView exit;
    private String userId = MMKV.defaultMMKV().decodeString("userId");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_me, null);
        findViewById(v);
        initView();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getgUser();
    }

    public void findViewById(View v) {
        homepage = (LinearLayout) v.findViewById(R.id.me_homepage);
        comment = (LinearLayout) v.findViewById(R.id.me_item_comment);
        record = (LinearLayout) v.findViewById(R.id.me_item_reord);
        favor = (LinearLayout) v.findViewById(R.id.me_item_favor);

        usernameTV = (TextView) v.findViewById(R.id.me_homepage_username);
        exerciseTimeTextView = (TextView) v.findViewById(R.id.me_exercise_time);
        recordDaysTextView = (TextView) v.findViewById(R.id.me_record_days);
        exit = (TextView) v.findViewById(R.id.me_item_exit);
    }

    public void initView() {
        showLoadingDialog();
        homepage.setOnClickListener(this);
        comment.setOnClickListener(this);
        record.setOnClickListener(this);
        favor.setOnClickListener(this);
        exit.setOnClickListener(this);

    }

    private void getgUser() {
        if (!TextUtils.isEmpty(userId)) {
            BmobQuery<user> userDataBmobQuery = new BmobQuery<>();
            userDataBmobQuery.getObject(userId.trim(), new QueryListener<user>() {
                @Override
                public void done(user userData, BmobException e) {
                    hideLoadingDialog();
                    if (e == null) {
                        usernameTV.setText(userData.getUsername());
                    } else {
                        ToastUtil.show("请联系管理员");
                    }
                }
            });
        }
    }

    /**
     * 回显
     */


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.me_homepage:
                startActivity(new Intent(getActivity(), HomepageActivity.class));
                break;
            case R.id.me_item_comment:
                startActivity(new Intent(getActivity(), CommentsListActivity.class));
                break;
            case R.id.me_item_favor:
                startActivity(new Intent(getActivity(), FavorsListActivity.class));
                break;
            case R.id.me_item_reord:
                startActivity(new Intent(getActivity(), BeforeDateCheckActivity.class));
                break;
            case R.id.me_item_exit:
                MMKV.defaultMMKV().encode("userId", "");
                getActivity().finish();
                BmobUser.logOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }


}
