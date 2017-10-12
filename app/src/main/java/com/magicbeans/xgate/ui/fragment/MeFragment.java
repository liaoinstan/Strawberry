package com.magicbeans.xgate.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.common.GridSpacingItemDecoration;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.DensityUtil;
import com.ins.common.utils.FocusUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.Order;
import com.magicbeans.xgate.bean.TestBean;
import com.magicbeans.xgate.databinding.FragmentMeBinding;
import com.magicbeans.xgate.ui.activity.AddressActivity;
import com.magicbeans.xgate.ui.activity.EvaActivity;
import com.magicbeans.xgate.ui.activity.FavoActivity;
import com.magicbeans.xgate.ui.activity.MeDetailActivity;
import com.magicbeans.xgate.ui.activity.MsgSettingActivity;
import com.magicbeans.xgate.ui.activity.OrderActivity;
import com.magicbeans.xgate.ui.activity.ProductDetailActivity;
import com.magicbeans.xgate.ui.activity.SettingActivity;
import com.magicbeans.xgate.ui.activity.SignActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterRecomment;
import com.magicbeans.xgate.ui.base.BaseFragment;


/**
 * Created by liaoinstan
 */
public class MeFragment extends BaseFragment implements View.OnClickListener, OnRecycleItemClickListener {

    private FragmentMeBinding binding;
    private int position;
    private View rootView;

    private RecycleAdapterRecomment adapter;

    public static Fragment newInstance(int position) {
        MeFragment fragment = new MeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.position = getArguments().getInt("position");
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
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
        toolbar.bringToFront();
        FocusUtil.focusToTop(binding.getRoot());
    }

    private void initBase() {
    }

    private void initView() {

        binding.layMeHeader.setOnClickListener(this);
        binding.btnRightSetting.setOnClickListener(this);
        binding.btnRightMsg.setOnClickListener(this);

        binding.textMeOrderUnpay.setOnClickListener(this);
        binding.textMeOrderUnin.setOnClickListener(this);
        binding.textMeOrderUneva.setOnClickListener(this);
        binding.textMeOrderAll.setOnClickListener(this);

        binding.layMeIntegral.setOnClickListener(this);
        binding.layMeCoupon.setOnClickListener(this);
        binding.layMeFavo.setOnClickListener(this);
        binding.layMeSign.setOnClickListener(this);
        binding.layMeHistory.setOnClickListener(this);

    }

    private void initData() {
        adapter.netGetRecommend();
    }

    private void initCtrl() {
        adapter = new RecycleAdapterRecomment(getContext());
        adapter.setOnItemClickListener(this);
        binding.recycleRecomment.setNestedScrollingEnabled(false);
        binding.recycleRecomment.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        binding.recycleRecomment.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtil.dp2px(4), GridLayoutManager.VERTICAL, false));
        binding.recycleRecomment.setAdapter(adapter);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
        ProductDetailActivity.start(getContext());
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
            case R.id.lay_me_integral:
                break;
            case R.id.lay_me_coupon:
                break;
            case R.id.lay_me_favo:
                FavoActivity.start(getActivity());
                break;
            case R.id.lay_me_sign:
                SignActivity.start(getActivity());
                break;
            case R.id.lay_me_history:
                break;
        }
    }
}
