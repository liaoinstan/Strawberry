package com.magicbeans.xgate.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.checkout.CheckoutWrap;
import com.magicbeans.xgate.bean.order.Order;
import com.magicbeans.xgate.bean.postbean.CreateOrderPost;
import com.magicbeans.xgate.bean.postbean.FreeGift;
import com.magicbeans.xgate.common.AppVali;
import com.magicbeans.xgate.databinding.DialogGiftsBinding;
import com.magicbeans.xgate.databinding.FragmentOrderaddCommitBinding;
import com.magicbeans.xgate.databinding.FragmentOrderaddInputBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STFormatCallback;
import com.magicbeans.xgate.ui.activity.OrderAddActivity;
import com.magicbeans.xgate.ui.activity.PayTestPaypalActivity;
import com.magicbeans.xgate.ui.adapter.RecycleAdapterOrder;
import com.magicbeans.xgate.ui.base.BaseFragment;
import com.magicbeans.xgate.ui.controller.OrderAddPriceDetailController;
import com.magicbeans.xgate.ui.dialog.DialogBottomGifts;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.RequestBody;

/**
 * Created by liaoinstan
 */
public class OrderAddCommitFragment extends BaseFragment implements View.OnClickListener {

    private int position;
    private FragmentOrderaddCommitBinding binding;
    private OrderAddActivity activity;

    private OrderAddPriceDetailController priceDetailController;

    private DialogBottomGifts dialogBottomGifts;

    private CreateOrderPost post;
    private CheckoutWrap wrap;

    public static Fragment newInstance(int position) {
        OrderAddCommitFragment fragment = new OrderAddCommitFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCommonEvent(EventBean event) {
        switch (event.getEvent()) {
            case EventBean.EVENT_ORDERADD_POSTBEAN:
                post = (CreateOrderPost) event.get("post");
                wrap = (CheckoutWrap) event.get("wrap");
                setWarpData(wrap);
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orderadd_commit, container, false);
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
        priceDetailController = new OrderAddPriceDetailController(binding.includePricedetail);
        priceDetailController.setShopCartInfo(activity.getShopCartInfo());
        dialogBottomGifts = new DialogBottomGifts(getActivity());
        dialogBottomGifts.setGiftSelectListener(new DialogBottomGifts.GiftSelectListener() {
            @Override
            public void onSelect(FreeGift freeGift) {
                binding.textGiftName.setText(freeGift.getName());
                post.getPromotion().addSelectGift(freeGift);
            }
        });
    }

    private void initView() {
        binding.textGift.setOnClickListener(this);
    }

    private void initCtrl() {
        binding.btnGo.setOnClickListener(this);
        binding.textPayPrice.setText("应付：" + activity.getShopCartInfo().getTotalPrice());
    }

    private void initData() {
    }

    private void setWarpData(CheckoutWrap wrap) {
        if (wrap != null) {
            binding.textNotice.setText(wrap.getImportantNoticeStr());
            if (wrap.hasGift()) {
                binding.textGift.setVisibility(View.VISIBLE);
                binding.textGiftName.setVisibility(View.VISIBLE);
            } else {
                binding.textGift.setVisibility(View.GONE);
                binding.textGiftName.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go:
                if (post != null) {
                    netAddOrder(post);
                } else {
                    ToastUtil.showToastShort("错误：数据为空");
                }
                break;
            case R.id.text_gift:
                dialogBottomGifts.setData(wrap.getGiftItems());
                dialogBottomGifts.show();
                break;
        }
    }

    //下单
    private void netAddOrder(CreateOrderPost post) {
        showLoadingDialog();
        RequestBody requestBody = NetParam.buildJsonRequestBody(post);
        NetApi.NI().netAddOrder(requestBody).enqueue(new STFormatCallback<Order>(Order.class) {
            @Override
            public void onSuccess(int status, Order order, String msg) {
                ToastUtil.showToastShort("下单成功");
                //刷新购物车
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_REFRESH_SHOPCART_REMOTE));
                dismissLoadingDialog();
                getActivity().finish();
                PayTestPaypalActivity.start(getActivity(), order.getSOID());
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort("下单失败：" + msg);
                dismissLoadingDialog();
            }
        });
    }
}
