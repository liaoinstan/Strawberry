package com.magicbeans.xgate.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.utils.viewutils.ViewPagerUtil;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.address.Address;
import com.magicbeans.xgate.bean.checkout.CheckoutWrap;
import com.magicbeans.xgate.bean.order.Order;
import com.magicbeans.xgate.bean.postbean.CreateOrderPost;
import com.magicbeans.xgate.common.AppVali;
import com.magicbeans.xgate.databinding.FragmentOrderBinding;
import com.magicbeans.xgate.databinding.FragmentOrderaddInputBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STFormatCallback;
import com.magicbeans.xgate.net.nethelper.NetOrderHelper;
import com.magicbeans.xgate.ui.activity.OrderAddActivity;
import com.magicbeans.xgate.ui.activity.OrderCouponActivity;
import com.magicbeans.xgate.ui.activity.OrderDetailActivity;
import com.magicbeans.xgate.ui.activity.OrderIdcardActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterOrder;
import com.magicbeans.xgate.ui.base.BaseFragment;
import com.magicbeans.xgate.ui.dialog.DialogSureCheckout;

import org.greenrobot.eventbus.EventBus;

import java.util.Iterator;
import java.util.List;

import okhttp3.RequestBody;

/**
 * Created by liaoinstan
 */
public class OrderAddInputFragment extends BaseFragment implements View.OnClickListener {

    private int position;
    private FragmentOrderaddInputBinding binding;
    private RecycleAdapterOrder adapter;
    private OrderAddActivity activity;

    public static Fragment newInstance(int position) {
        OrderAddInputFragment fragment = new OrderAddInputFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCommonEvent(EventBean event) {
        switch (event.getEvent()) {
            case EventBean.EVENT_ADD_COUPON:
                binding.textCoupon.setText((String) event.get("coupon"));
                break;
            case EventBean.EVENT_ADD_IDCARD:
                binding.textIdcard.setText((String) event.get("idcard"));
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registEventBus();
        this.position = getArguments().getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orderadd_input, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (OrderAddActivity) getActivity();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        binding.layCoupon.setOnClickListener(this);
        binding.layIdcard.setOnClickListener(this);
        binding.btnGo.setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_coupon:
                OrderCouponActivity.start(getActivity());
                break;
            case R.id.lay_idcard:
                OrderIdcardActivity.start(getActivity());
                break;
            case R.id.btn_go:
                Address address = activity.getAddress();
                String idcard = binding.textIdcard.getText().toString();
                String coupon = binding.textCoupon.getText().toString();
                String msg = AppVali.checkOut(idcard, address);
                if (msg != null) {
                    ToastUtil.showToastShort(msg);
                } else {
                    CreateOrderPost orderPost = activity.createOrderPost(coupon, idcard);
                    netCheckout(orderPost);
                }
                break;
        }
    }

    //checkout
    private void netCheckout(final CreateOrderPost post) {
        showLoadingDialog();
        RequestBody requestBody = NetParam.buildJsonRequestBody(post);
        NetApi.NI().netCheckout(requestBody).enqueue(new STFormatCallback<CheckoutWrap>(CheckoutWrap.class) {
            @Override
            public void onSuccess(int status, CheckoutWrap wrap, String msg) {
                dismissLoadingDialog();
                EventBean eventBean = new EventBean(EventBean.EVENT_ORDERADD_POSTBEAN);
                eventBean.put("post", post);
                eventBean.put("wrap", wrap);
                EventBus.getDefault().post(eventBean);
                ViewPagerUtil.next(activity.getBinding().pager);
            }

            @Override
            public void onError(int status, CheckoutWrap wrap, String msg) {
                ToastUtil.showToastShort(msg);
                dismissLoadingDialog();
            }
        });
    }
}
