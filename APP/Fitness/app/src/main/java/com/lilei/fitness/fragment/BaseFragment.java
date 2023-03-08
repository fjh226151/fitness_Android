package com.lilei.fitness.fragment;

import android.app.Fragment;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import tech.gujin.toast.ToastUtil;

public class BaseFragment extends Fragment {
    protected BasePopupView popupView;

    protected void showLoadingDialog() {
        if (popupView == null) {
            popupView = new XPopup.Builder(getContext()).asLoading("正在加载中").show();
        }
        popupView.show();
    }

    protected void showToast(String s) {
        ToastUtil.show(s);
    }

    protected void hideLoadingDialog() {
        if (popupView != null) {
            popupView.dismiss();
        }
    }
}
