package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONReader;
import com.ins.common.helper.JsonReadHelper;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.L;
import com.ins.common.utils.ListUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.view.bundleimgview.BundleImgEntity;
import com.ins.common.view.bundleimgview.BundleImgView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.address.Address;
import com.magicbeans.xgate.bean.address.AddressWrap;
import com.magicbeans.xgate.bean.checkout.CheckoutWrap;
import com.magicbeans.xgate.bean.common.CommonEntity;
import com.magicbeans.xgate.bean.order.Order;
import com.magicbeans.xgate.bean.postbean.Cart;
import com.magicbeans.xgate.bean.postbean.CreateOrderPost;
import com.magicbeans.xgate.bean.postbean.Customer;
import com.magicbeans.xgate.bean.postbean.Payment;
import com.magicbeans.xgate.bean.postbean.Promotion;
import com.magicbeans.xgate.bean.shopcart.ShopCart;
import com.magicbeans.xgate.bean.shopcart.ShopCartInfo;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.common.AppData;
import com.magicbeans.xgate.common.AppVali;
import com.magicbeans.xgate.databinding.ActivityOrderaddBinding;
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STFormatCallback;
import com.magicbeans.xgate.net.nethelper.NetAddressHelper;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.controller.ShopCartContentController;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

public class OrderAddActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private ActivityOrderaddBinding binding;
    private List<ShopCart> goods;
    private ShopCartInfo shopCartInfo;
    private Address address;

    public static void start(Context context, List<ShopCart> goods, ShopCartInfo shopCartInfo) {
        if (AppHelper.User.isLogin()) {
            Intent intent = new Intent(context, OrderAddActivity.class);
            intent.putExtra("goods", ListUtil.transArrayList(goods));
            intent.putExtra("shopCartInfo", shopCartInfo);
            context.startActivity(intent);
        } else {
            LoginActivity.start(context);
        }
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
            case EventBean.EVENT_GET_ADDRESS:
                Address address = (Address) event.get("address");
                setAddress(address);
                break;
            case EventBean.EVENT_REFRESH_ORDERADD_ADDRESS:
                netGetDefaultAddress(true);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_orderadd);
        setToolbar();
        registEventBus();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        goods = (List<ShopCart>) getIntent().getSerializableExtra("goods");
        shopCartInfo = (ShopCartInfo) getIntent().getSerializableExtra("shopCartInfo");
    }

    private void initView() {
        binding.layBundle.setOnClickListener(this);
        binding.layCoupon.setOnClickListener(this);
        binding.layIdcard.setOnClickListener(this);
    }

    private void initCtrl() {
        binding.bundleview.setOnBundleLoadImgListener(new BundleImgView.OnBundleLoadImgListener() {
            @Override
            public void onloadImg(ImageView imageView, String imgurl, int defaultSrc) {
                GlideUtil.loadImg(imageView, imgurl);
            }
        });
        binding.textCount.setText("总计：" + ShopCartContentController.calcuCount(goods));
        binding.textTotalPrice.setText(shopCartInfo.getTotalPrice());
        binding.textTransName.setText(shopCartInfo.getShipmentName());
        binding.textPayPrice.setText("应付：" + shopCartInfo.getTotalPrice());
        binding.layAddressAdd.setOnClickListener(this);
        binding.layAddressSelect.setOnClickListener(this);
    }

    private void initData() {
        //设置商品图片列表数据
        ArrayList<BundleImgEntity> bundles = new ArrayList<>();
        for (ShopCart good : goods) {
            bundles.add(new BundleImgEntity(good.getHeaderImg()));
        }
        binding.bundleview.setPhotos(bundles);

        netGetDefaultAddress(true);
    }

    //0：加载状态  1：新增状态  2：已选择状态
    private void setVisibleType(int type) {
        switch (type) {
            case 0:
                binding.progress.setVisibility(View.VISIBLE);
                binding.layAddressAdd.setVisibility(View.GONE);
                binding.layAddressSelect.setVisibility(View.GONE);
                break;
            case 1:
                binding.progress.setVisibility(View.GONE);
                binding.layAddressAdd.setVisibility(View.VISIBLE);
                binding.layAddressSelect.setVisibility(View.GONE);
                break;
            case 2:
                binding.progress.setVisibility(View.GONE);
                binding.layAddressAdd.setVisibility(View.GONE);
                binding.layAddressSelect.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setAddress(Address address) {
        this.address = address;
        if (address == null) {
            setVisibleType(1);
        } else {
            setVisibleType(2);
            binding.textAddressName.setText(address.getAddressNickName() + " " + address.getTelEncry());
            binding.textAddressDetail.setText(address.getAddress());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_address_add:
                AddressAddActivity.start(this);
                break;
            case R.id.lay_address_select:
                AddressActivity.startForResult(this);
                break;
            case R.id.lay_bundle:
                OrderProductActivity.start(this, goods);
                break;
            case R.id.lay_coupon:
                OrderCouponActivity.start(this);
                break;
            case R.id.lay_idcard:
                OrderIdcardActivity.start(this);
                break;
            case R.id.btn_go:
                if (address != null) {
                    String idcard = binding.textIdcard.getText().toString();
                    String coupon = binding.textCoupon.getText().toString();
                    String msg = AppVali.checkOut(idcard);
                    if (msg != null) {
                        ToastUtil.showToastShort(msg);
                    } else {
                        CreateOrderPost orderPost = createOrderPost(coupon, idcard);
                        netCheckout(orderPost);
                    }
                } else {
                    ToastUtil.showToastShort("请先填写送货地址");
                }
                break;
        }
    }

    /////////////////////////////////

    private CreateOrderPost createOrderPost(String coupon, String idcard) {
        //创建Post对象，进行赋值
        CreateOrderPost post = new CreateOrderPost();
        post.setCart(new Cart(CreateOrderPost.tansProdList(goods)));
        post.setCurrId("CNY");
        post.setRegion("CN");
        //Customer
        Customer customer = new Customer();
        customer.setEmail(AppData.App.getUser().getEmail());
        customer.setIDCardNumber(idcard);
        customer.setToken(Token.getLocalToken());
        customer.setOpenId(AppData.App.getOpenId());
        //Customer
        customer.setBillAddr(address.transToAddr());
        customer.setShipAddr(address.transToAddr());
        post.setCustomer(customer);
        //Payment
        post.setPayment(new Payment("Wechat AY"));
        //Promotion
        post.setPromotion(new Promotion(coupon));

        return post;
    }

    //获取默认地址
    public void netGetDefaultAddress(boolean isBillAddr) {
        setVisibleType(0);
        NetAddressHelper.getInstance().netGetAddressList(isBillAddr, new NetAddressHelper.OnAddressListCallback() {
            @Override
            public void onSuccess(List<Address> addressList) {
                Address defaultAddress = AddressWrap.getDefaultAddressEx(addressList);
                setAddress(defaultAddress);
                if (defaultAddress == null) ToastUtil.showToastShort("您还没有设置配送地址，请先添加地址");
            }

            @Override
            public void onError(String msg) {
                ToastUtil.showToastShort(msg);
                setVisibleType(1);
            }
        });
    }

    //checkout
    private void netCheckout(final CreateOrderPost post) {
        showLoadingDialog();
        RequestBody requestBody = NetParam.buildJsonRequestBody(post);
        NetApi.NI().netCheckout(requestBody).enqueue(new STFormatCallback<CheckoutWrap>(CheckoutWrap.class) {
            @Override
            public void onSuccess(int status, CheckoutWrap wrap, String msg) {
                //ToastUtil.showToastShort("check out 成功");
                netAddOrder(post);
            }

            @Override
            public void onError(int status, CheckoutWrap wrap, String msg) {
                ToastUtil.showToastShort("check out 失败：" + msg);
                dismissLoadingDialog();

//                JsonReadHelper.newInstance().read(com, new JsonReadHelper.JsonCallback() {
//                    @Override
//                    public void onRead(String key, Object object) {
//                    }
//                });
            }
        });
    }

    //下单
    private void netAddOrder(CreateOrderPost post) {
        RequestBody requestBody = NetParam.buildJsonRequestBody(post);
        NetApi.NI().netAddOrder(requestBody).enqueue(new STFormatCallback<Order>(Order.class) {
            @Override
            public void onSuccess(int status, Order order, String msg) {
                ToastUtil.showToastShort("下单成功");
                //刷新购物车
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_REFRESH_SHOPCART_REMOTE));
                dismissLoadingDialog();
                finish();
                PayTestPaypalActivity.start(OrderAddActivity.this, order.getSOID());
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort("下单失败：" + msg);
                dismissLoadingDialog();
            }
        });
    }
}
