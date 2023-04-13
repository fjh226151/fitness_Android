package com.lilei.fitness.fragment;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.lilei.fitness.R;
import com.lilei.fitness.bean.eatFoods;
import com.lilei.fitness.bean.goods;
import com.lilei.fitness.bean.user;
import com.lilei.fitness.utils.FeatureParser;
import com.lilei.fitness.utils.SensorUtil;
import com.lilei.fitness.utils.SystemUtil;
import com.lilei.fitness.utils.TimeUtila;
import com.lilei.fitness.utils.XiaoMiStep;
import com.lilei.fitness.view.BeforeDateCheckActivity;
import com.lilei.fitness.view.CommentsListActivity;
import com.lilei.fitness.view.FavorsListActivity;
import com.lilei.fitness.view.GoodsListActivity;
import com.lilei.fitness.view.HomepageActivity;
import com.lilei.fitness.view.LoginActivity;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.tencent.mmkv.MMKV;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import tech.gujin.toast.ToastUtil;

/**
 * Created by djzhao on 17/04/30.
 */

public class MeFragment extends BaseFragment implements View.OnClickListener {
    private Integer mStepDetector = 0;
    private Integer mStepCounter = 0;
    private LinearLayout homepage;
    private LinearLayout comment;
    private LinearLayout record;
    private LinearLayout favor;
    private TextView xiaohao;
    private TextView usernameTV;
    private TextView exerciseTimeTextView;
    private TextView recordDaysTextView;
    private TextView exit;
    private String userId = MMKV.defaultMMKV().decodeString("userId");
    private SensorManager mSensorManager;
    private View v;
    private LinearLayout me_eat_goods;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                getTodayDeplete();
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (null == v) {
            v = inflater.inflate(R.layout.fragment_me, null);
            findViewById(v);
            initView();
            if (SystemUtil.INSTANCE.getDeviceBrand().equals(SystemUtil.PHONE_XIAOMI)) {
                LinkedList<XiaoMiStep> xiaoMiSteps = FeatureParser.Companion.XiaoMiGetSteps(getContext());
                if (!xiaoMiSteps.isEmpty()) {
                    for (XiaoMiStep xiaoMiStep : xiaoMiSteps) {
                        if (xiaoMiStep.getMBeginTime() > TimeUtila.Companion.getSameDay0Point() && xiaoMiStep.getMEndTime() < TimeUtila.Companion.getSameDay1Point()) {
                            mStepCounter += xiaoMiStep.getMSteps();
                        }
                    }
                }
            } else {
                showToast("暂不支持该设备计步");
            }
            getTodayDeplete();
            handler.sendEmptyMessageDelayed(1, 1000 * 60);
        }
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getgUser();
        }
        getTodayDeplete();
    }

    private void updateData() {
        recordDaysTextView.setText(mStepCounter.toString());
        Log.e("test", "今日消耗:" + depleteTodayAll);
        exerciseTimeTextView.setText(SensorUtil.Companion.setMoney((mStepCounter + depleteTodayAll) * 0.04));
    }

    public void findViewById(View v) {
        homepage = v.findViewById(R.id.me_homepage);
        comment = v.findViewById(R.id.me_item_comment);
        record = v.findViewById(R.id.me_item_reord);
        favor = v.findViewById(R.id.me_item_favor);
        xiaohao = v.findViewById(R.id.xiaohao);
        usernameTV = v.findViewById(R.id.me_homepage_username);
        exerciseTimeTextView = v.findViewById(R.id.me_exercise_time);
        recordDaysTextView = v.findViewById(R.id.me_record_days);
        exit = v.findViewById(R.id.me_item_exit);
        me_eat_goods = v.findViewById(R.id.me_eat_goods);
    }

    public void initView() {
        showLoadingDialog();
        homepage.setOnClickListener(this);
        comment.setOnClickListener(this);
        record.setOnClickListener(this);
        favor.setOnClickListener(this);
        exit.setOnClickListener(this);
        me_eat_goods.setOnClickListener(this);
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
                //跳转食物消耗列表activtiy
                startActivity(new Intent(getActivity(), GoodsListActivity.class));
                break;
            case R.id.me_item_exit:
                MMKV.defaultMMKV().encode("userId", "");
                getActivity().finish();
                BmobUser.logOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.me_eat_goods: {
                new XPopup.Builder(getContext()).asInputConfirm("今天你吃了什么", "请输入食物名称",
                                text -> {
                                    showLoadingDialog();
                                    BmobQuery<goods> goodsBmobQuery = new BmobQuery<goods>();
                                    goodsBmobQuery.findObjects(new FindListener<goods>() {
                                        @Override
                                        public void done(List<goods> list, BmobException e) {
                                            hideLoadingDialog();
                                            if (e == null) {
                                                if (list.stream().anyMatch(a -> a.getGoodName().equals(text))) {
                                                    eatFoods userId = new eatFoods(text, MMKV.defaultMMKV().decodeString("userId"), String.valueOf(System.currentTimeMillis()));
                                                    userId.save(new SaveListener<String>() {
                                                        @Override
                                                        public void done(String s, BmobException e) {
                                                            showToast("已记录");
                                                            getTodayDeplete();
                                                        }
                                                    });
                                                } else {
                                                    showToast("已知食物中不包含该食物,请先添加");
                                                }
                                            } else {
                                                showToast("请联系管理员");
                                            }
                                        }
                                    });
                                })
                        .show();
                break;
            }
        }
    }

    Integer depleteTodayAll = 0;
    Boolean isFinishQueryeatFoods = false;
    Boolean isFinshQueryGoods = false;

    private void getTodayDeplete() {
        showLoadingDialog();
        ArrayList<eatFoods> foodsArrayList = new ArrayList<>();
        BmobQuery<eatFoods> eatFoodsBmobQuery = new BmobQuery<>();
        eatFoodsBmobQuery.findObjects(new FindListener<eatFoods>() {
            @Override
            public void done(List<eatFoods> list, BmobException e) {
                isFinishQueryeatFoods = true;
                if (isFinshQueryGoods && isFinishQueryeatFoods) {
                    hideLoadingDialog();
                }
                hideLoadingDialog();
                if (!list.isEmpty()) {
                    for (eatFoods foods : list) {
                        if (foods.getUserId().equals(MMKV.defaultMMKV().decodeString("userId"))) {
                            if (Long.parseLong(foods.getEatGoodTime()) > TimeUtila.Companion.getSameDay0Point() && Long.parseLong(foods.getEatGoodTime()) < TimeUtila.Companion.getSameDay1Point()) {
                                foodsArrayList.add(foods);
                            }
                        }
                    }
                }
                BmobQuery<goods> goodsBmobQuery = new BmobQuery<>();
                goodsBmobQuery.findObjects(new FindListener<goods>() {
                    @Override
                    public void done(List<goods> list, BmobException e) {
                        isFinshQueryGoods = true;
                        if (isFinshQueryGoods && isFinishQueryeatFoods) {
                            hideLoadingDialog();
                        }
                        if (!list.isEmpty()) {
                            for (goods goods : list) {
                                for (eatFoods eatFoods : foodsArrayList) {
                                    if (eatFoods.getEatFood().equals(goods.getGoodName())) {
                                        depleteTodayAll += Integer.parseInt(goods.getDepleteGoodsValue());
                                    }
                                }
                            }
                            Log.e("test", "value:" + depleteTodayAll);
                            updateData();
                            if (depleteTodayAll != 0) {
                                depleteTodayAll = 0;
                            }
                        }
                    }
                });
            }
        });
    }
}