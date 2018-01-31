package com.magicbeans.xgate.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.helper.ToobarTansColorHelper;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.FocusUtil;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.L;
import com.ins.common.view.ObservableNestedScrollView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.Order;
import com.magicbeans.xgate.bean.user.User;
import com.magicbeans.xgate.common.AppData;
import com.magicbeans.xgate.databinding.FragmentMeBinding;
import com.magicbeans.xgate.ui.activity.FavoActivity;
import com.magicbeans.xgate.ui.activity.HistoryActivity;
import com.magicbeans.xgate.ui.activity.MeDetailActivity;
import com.magicbeans.xgate.ui.activity.MsgSettingActivity;
import com.magicbeans.xgate.ui.activity.OrderActivity;
import com.magicbeans.xgate.ui.activity.PayTestActivity;
import com.magicbeans.xgate.ui.activity.SettingActivity;
import com.magicbeans.xgate.ui.base.BaseFragment;
import com.magicbeans.xgate.ui.controller.CommonRecommendController;
import com.magicbeans.xgate.utils.CipherUtil;


/**
 * Created by liaoinstan
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {

    private int position;
    private View rootView;

    private FragmentMeBinding binding;
    private CommonRecommendController commonRecommendController;

    public static Fragment newInstance(int position) {
        MeFragment fragment = new MeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCommonEvent(EventBean event) {
        switch (event.getEvent()) {
            case EventBean.EVENT_LOGIN:
                setUserData();
                break;
            case EventBean.EVENT_LOGOUT:
                setUserData();
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.position = getArguments().getInt("position");
        registEventBus();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_me, container, false);
        rootView = binding.getRoot();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setToolbar(false);
        initBase();
        initView();
        initCtrl();
        initData();
        toolbar.bringToFront();
        FocusUtil.focusToTop(binding.getRoot());
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            if (getActivity() != null) StatusBarTextUtil.transparencyBar(getActivity());
//        }
//    }

    private void initBase() {
        commonRecommendController = new CommonRecommendController(binding.includeRecommend);
    }

    private void initView() {

        binding.layMeHeader.setOnClickListener(this);
        binding.btnRightSetting.setOnClickListener(this);
        binding.btnRightMsg.setOnClickListener(this);

        binding.textMeOrderUnpay.setOnClickListener(this);
        binding.textMeOrderUnin.setOnClickListener(this);
        binding.textMeOrderUneva.setOnClickListener(this);
        binding.textMeOrderAll.setOnClickListener(this);

        binding.layMeFavo.setOnClickListener(this);
        binding.layMeHistory.setOnClickListener(this);

    }

    private void initData() {
        setUserData();
    }

    private void initCtrl() {
        //设置scrollView滚动监听
        binding.scrollView.setOnScrollChangedListener(new ObservableNestedScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int x, int y, int oldx, int oldy) {
                //toolbar动态透明渐变
                ToobarTansColorHelper.with(binding.toolbar)
                        .initMaxHeight(DensityUtil.dp2px(100))
                        .initColorStart(ContextCompat.getColor(getContext(), R.color.st_red_none))
                        .initColorEnd(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                        .start(y);
            }
        });
    }

    private void setUserData() {
        User user = AppData.App.getUser();
        if (user != null) {
            //已登录
            GlideUtil.loadCircleImg(binding.imgMeHeader, R.drawable.header_default, user.getAvator());
            binding.textMeName.setText(!TextUtils.isEmpty(user.getNickname()) ? user.getNickname() : "欢迎");
        } else {
            //未登录
            binding.imgMeHeader.setImageResource(R.drawable.header_default);
            binding.textMeName.setText("登录/注册");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_me_header:
                MeDetailActivity.start(getActivity());
                break;
            case R.id.btn_right_msg:
                MsgSettingActivity.start(getActivity());
                break;
            case R.id.btn_right_setting:
                SettingActivity.start(getActivity());
                break;
            case R.id.text_me_order_unpay:
                OrderActivity.start(getActivity(), Order.STATUS_UNPAY);
                break;
            case R.id.text_me_order_unin:
                OrderActivity.start(getActivity(), Order.STATUS_UNIN);
                break;
            case R.id.text_me_order_uneva:
                OrderActivity.start(getActivity(), Order.STATUS_UNEVA);
                break;
            case R.id.text_me_order_all:
                OrderActivity.start(getActivity(), Order.STATUS_ALL);
                break;
            case R.id.lay_me_favo:
                FavoActivity.start(getActivity());
                break;
            case R.id.lay_me_history:
                HistoryActivity.start(getActivity());
                break;
        }
    }
}
