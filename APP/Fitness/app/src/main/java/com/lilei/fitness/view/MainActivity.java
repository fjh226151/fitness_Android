package com.lilei.fitness.view;


import static java.security.AccessController.getContext;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.lilei.fitness.R;
import com.lilei.fitness.bean.goods;
import com.lilei.fitness.fragment.FoundFragment;
import com.lilei.fitness.fragment.MeFragment;
import com.lilei.fitness.fragment.TrainingFragment;
import com.lilei.fitness.utils.AppManager;
import com.lilei.fitness.view.base.BaseActivity;
import com.lilei.fitness.view.test.VideoPlayer;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.tencent.mmkv.MMKV;

import org.w3c.dom.Text;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout tabTrain;
    private LinearLayout tabFound;
    private LinearLayout tabMe;

    private ImageView btnCheck;
    private ImageView btnAddNews;
    private ImageView icoTrain;
    private ImageView icoFound;
    private ImageView icoMe;

    private TextView txtTrain;
    private TextView txtFound;
    private TextView txtMe;
    private TextView txtTitle;

    private Fragment trainingFragment;
    private Fragment foundFragment;
    private Fragment meFragment;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        tabTrain = $(R.id.bottom_train);
        tabFound = $(R.id.bottom_found);
        tabMe = $(R.id.bottom_me);
        btnCheck = $(R.id.up_btn_check);
        btnAddNews = $(R.id.found_new_add);

        icoTrain = $(R.id.bottom_ico_train);
        icoFound = $(R.id.bottom_ico_found);
        icoMe = $(R.id.bottom_ico_me);

        txtTrain = $(R.id.bottom_txt_train);
        txtFound = $(R.id.bottom_txt_found);
        txtMe = $(R.id.bottom_txt_me);
        txtTitle = $(R.id.titleText);
    }

    @Override
    protected void initView() {
        tabTrain.setOnClickListener(this);
        tabFound.setOnClickListener(this);
        tabMe.setOnClickListener(this);
        btnCheck.setOnClickListener(this);
        btnAddNews.setOnClickListener(this);
        if (trainingFragment == null) {
            trainingFragment = new TrainingFragment();
        }
        if (foundFragment == null) {
            foundFragment = new FoundFragment();
        }
        if (meFragment == null) {
            meFragment = new MeFragment();
        }
        refreashFragment(R.id.bottom_train);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_train:
                changeTabState(R.id.bottom_train);
                changeTitle(R.string.title_train);
                topButtonChange(R.id.up_btn_check);
                refreashFragment(R.id.bottom_train);
                break;
            case R.id.bottom_found:
                topButtonChange(R.id.found_new_add);
                changeTabState(R.id.bottom_found);
                changeTitle(R.string.title_found);
                refreashFragment(R.id.bottom_found);
                break;
            case R.id.bottom_me:
                topButtonChange(0);
                changeTabState(R.id.bottom_me);
                changeTitle(R.string.title_me);
                refreashFragment(R.id.bottom_me);
                break;
            case R.id.up_btn_check:
                openActivity(BeforeDateCheckActivity.class);
                break;
            case R.id.found_new_add:
                new XPopup.Builder(this).asCustom(new BasePopupView(this) {
                    private EditText goodsName;
                    private EditText goodsValue;
                    private TextView ok;
                    private TextView cancel;

                    @Override
                    protected int getPopupLayoutId() {
                        return R.layout.dialog_confrim_cancel;
                    }

                    @Override
                    protected void onCreate() {
                        super.onCreate();
                        goodsName = findViewById(R.id.goods);
                        goodsValue = findViewById(R.id.depleteGoodsValue);
                        ok = findViewById(R.id.ok);
                        cancel = findViewById(R.id.cancel);
                        ok.setOnClickListener(v -> {
                            if (TextUtils.isEmpty(goodsName.getText().toString())) {
                                showToast("请补充食物名称");
                                return;
                            }
                            if (TextUtils.isEmpty(goodsValue.getText().toString())) {
                                showToast("请补充食物消耗的能量");
                                return;
                            }
                            showLoadingDialog();
                            goods goods = new goods(MMKV.defaultMMKV().decodeString("userId"), goodsName.getText().toString(), goodsValue.getText().toString());
                            goods.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    hideLoadingDialog();
                                    if (e == null) {
                                        showToast("数据添加成功");

                                    } else {
                                        showToast("添加数据失败,请联系管理员");
                                    }
                                    dismiss();
                                }
                            });
                        });
                        cancel.setOnClickListener(v -> {
                            dismiss();
                        });
                    }
                }).show();
                break;
        }
    }

    public void topButtonChange(int id) {
        if (id == R.id.up_btn_check) {
            btnCheck.setVisibility(View.VISIBLE);
            btnAddNews.setVisibility(View.GONE);
        } else if (id == R.id.found_new_add) {
            btnCheck.setVisibility(View.GONE);
            btnAddNews.setVisibility(View.VISIBLE);
        } else {
            btnCheck.setVisibility(View.GONE);
            btnAddNews.setVisibility(View.GONE);
        }
    }

    private void changeTitle(int stringId) {
        // txtTitle.setText(getResources().getString(stringId));
    }

    /**
     * 切换Fragment
     *
     * @param btnId
     */
    private void refreashFragment(int btnId) {
        FragmentManager fragmentManager = getFragmentManager();
        //  FragmentTransaction transaction = manager.beginTransaction();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (btnId) {
            case R.id.bottom_train:
                fragmentTransaction.replace(R.id.fragment_container, trainingFragment);
                break;
            case R.id.bottom_found:
                fragmentTransaction.replace(R.id.fragment_container, foundFragment);
                break;
            case R.id.bottom_me:
                fragmentTransaction.replace(R.id.fragment_container, meFragment);
                break;
        }
        fragmentTransaction.commit();
    }

    private void changeTabState(int tabId) {
        if (tabId == R.id.bottom_train) {
            icoTrain.setImageResource(R.drawable.icon_train_pressed);
            txtTrain.setTextColor(getResources().getColor(R.color.bottom_tab_pressed));
        } else {
            icoTrain.setImageResource(R.drawable.icon_train_unpressed);
            txtTrain.setTextColor(getResources().getColor(R.color.bottom_tab_normal));
        }
        if (tabId == R.id.bottom_found) {
            icoFound.setImageResource(R.drawable.icon_found_pressed);
            txtFound.setTextColor(getResources().getColor(R.color.bottom_tab_pressed));
        } else {
            icoFound.setImageResource(R.drawable.icon_found_unpressed);
            txtFound.setTextColor(getResources().getColor(R.color.bottom_tab_normal));
        }
        if (tabId == R.id.bottom_me) {
            icoMe.setImageResource(R.drawable.icon_me_pressed);
            txtMe.setTextColor(getResources().getColor(R.color.bottom_tab_pressed));
        } else {
            icoMe.setImageResource(R.drawable.icon_me_unpressed);
            txtMe.setTextColor(getResources().getColor(R.color.bottom_tab_normal));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 800) {
                DisplayToast("再按一次退出肌撕裂者");
                mExitTime = System.currentTimeMillis();
                return true;
            } else {
                AppManager.getInstance().killAllActivity();
                AppManager.getInstance().AppExit(this);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void openTestVideo(View v) {

        openActivity(VideoPlayer.class);
    }
}
