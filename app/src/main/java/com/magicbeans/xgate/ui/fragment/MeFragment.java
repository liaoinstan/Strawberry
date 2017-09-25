package com.magicbeans.xgate.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.Order;
import com.magicbeans.xgate.ui.activity.AddressActivity;
import com.magicbeans.xgate.ui.activity.LoginActivity;
import com.magicbeans.xgate.ui.activity.OrderActivity;
import com.magicbeans.xgate.ui.activity.SettingActivity;
import com.magicbeans.xgate.ui.base.BaseFragment;


/**
 * Created by liaoinstan
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {

    private int position;
    private View rootView;

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
        rootView = inflater.inflate(R.layout.fragment_me, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBase();
        initView();
        initData();
        initCtrl();
    }

    private void initBase() {
    }

    private void initView() {
        rootView.findViewById(R.id.text_me_order_unpay).setOnClickListener(this);
        rootView.findViewById(R.id.text_me_order_unout).setOnClickListener(this);
        rootView.findViewById(R.id.text_me_order_unin).setOnClickListener(this);
        rootView.findViewById(R.id.text_me_order_uneva).setOnClickListener(this);
        rootView.findViewById(R.id.lay_me_header).setOnClickListener(this);
        rootView.findViewById(R.id.text_me_address).setOnClickListener(this);
        rootView.findViewById(R.id.text_me_sign).setOnClickListener(this);
        rootView.findViewById(R.id.text_me_favo).setOnClickListener(this);
        rootView.findViewById(R.id.text_me_eva).setOnClickListener(this);
        rootView.findViewById(R.id.text_me_email).setOnClickListener(this);
        rootView.findViewById(R.id.text_me_setting).setOnClickListener(this);
    }

    private void initData() {
    }

    private void initCtrl() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_me_order_unpay:
                OrderActivity.start(getActivity(), Order.STATUS_UNPAY);
                break;
            case R.id.text_me_order_unout:
                OrderActivity.start(getActivity(), Order.STATUS_UNOUT);
                break;
            case R.id.text_me_order_unin:
                OrderActivity.start(getActivity(), Order.STATUS_UNIN);
                break;
            case R.id.text_me_order_uneva:
                OrderActivity.start(getActivity(), Order.STATUS_UNEVA);
                break;
            case R.id.lay_me_header:
                LoginActivity.start(getActivity());
                break;
            case R.id.text_me_address:
                AddressActivity.start(getActivity());
                break;
            case R.id.text_me_sign:
                break;
            case R.id.text_me_favo:
                break;
            case R.id.text_me_eva:
                break;
            case R.id.text_me_email:
                break;
            case R.id.text_me_setting:
                SettingActivity.start(getActivity());
                break;
        }
    }
}
