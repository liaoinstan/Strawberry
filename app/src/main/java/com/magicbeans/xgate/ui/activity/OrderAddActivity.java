package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.ListUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.utils.viewutils.ViewPagerUtil;
import com.ins.common.view.bundleimgview.BundleImgEntity;
import com.ins.common.view.bundleimgview.BundleImgView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.address.Address;
import com.magicbeans.xgate.bean.address.AddressWrap;
import com.magicbeans.xgate.bean.checkout.CheckoutWrap;
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
import com.magicbeans.xgate.ui.adapter.PagerAdapterOrder;
import com.magicbeans.xgate.ui.adapter.PagerAdapterOrderAdd;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.controller.OrderAddAddressController;
import com.magicbeans.xgate.ui.controller.OrderAddPriceDetailController;
import com.magicbeans.xgate.ui.controller.OrderAddProductsController;
import com.magicbeans.xgate.ui.controller.ShopCartContentController;
import com.magicbeans.xgate.ui.dialog.DialogSureCheckout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

public class OrderAddActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private ActivityOrderaddBinding binding;
    private OrderAddAddressController addressController;
    private OrderAddProductsController productsController;
//    private OrderAddPriceDetailController priceDetailController;

    private String[] titles = new String[]{"填写资料", "提交"};

    private PagerAdapterOrderAdd adapterPager;

    private ShopCartInfo shopCartInfo;

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
//            case EventBean.EVENT_ADD_COUPON:
//                binding.textCoupon.setText((String) event.get("coupon"));
//                break;
//            case EventBean.EVENT_ADD_IDCARD:
//                binding.textIdcard.setText((String) event.get("idcard"));
//                break;
            case EventBean.EVENT_GET_ADDRESS:
                Address address = (Address) event.get("address");
                addressController.setAddress(address);
                break;
            case EventBean.EVENT_REFRESH_ORDERADD_ADDRESS:
                addressController.netGetDefaultAddress(true);
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
        List<ShopCart> goods = (List<ShopCart>) getIntent().getSerializableExtra("goods");
        shopCartInfo = (ShopCartInfo) getIntent().getSerializableExtra("shopCartInfo");

        addressController = new OrderAddAddressController(binding.includeAddress);
        productsController = new OrderAddProductsController(binding.includeProducts);
        productsController.setGoodsData(goods);
//        priceDetailController = new OrderAddPriceDetailController(binding.includePricedetail);
//        priceDetailController.setShopCartInfo(shopCartInfo);
    }

    private void initView() {

//        binding.layCoupon.setOnClickListener(this);
//        binding.layIdcard.setOnClickListener(this);
    }

    private void initCtrl() {

//        binding.textPayPrice.setText("应付：" + shopCartInfo.getTotalPrice());
        adapterPager = new PagerAdapterOrderAdd(getSupportFragmentManager(), titles);
        binding.pager.setAdapter(adapterPager);
    }

    private void initData() {
        addressController.netGetDefaultAddress(true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.lay_coupon:
//                OrderCouponActivity.start(this);
//                break;
//            case R.id.lay_idcard:
//                OrderIdcardActivity.start(this);
//                break;
//            case R.id.btn_go:
//                if (addressController.getAddress() != null) {
//                    String idcard = binding.textIdcard.getText().toString();
//                    String coupon = binding.textCoupon.getText().toString();
//                    String msg = AppVali.checkOut(idcard);
//                    if (msg != null) {
//                        ToastUtil.showToastShort(msg);
//                    } else {
//                        CreateOrderPost orderPost = createOrderPost(coupon, idcard);
//                        netCheckout(orderPost);
//                    }
//                } else {
//                    ToastUtil.showToastShort("请先填写送货地址");
//                }
//                break;
        }
    }

    public ShopCartInfo getShopCartInfo() {
        return shopCartInfo;
    }

    public ActivityOrderaddBinding getBinding() {
        return binding;
    }

    public Address getAddress() {
        return addressController.getAddress();
    }

    /////////////////////////////////

    public CreateOrderPost createOrderPost(String coupon, String idcard) {
        //创建Post对象，进行赋值
        CreateOrderPost post = new CreateOrderPost();
        post.setCart(new Cart(CreateOrderPost.tansProdList(productsController.getGoods())));
        post.setCurrId("CNY");
        post.setRegion("CN");
        //Customer
        Customer customer = new Customer();
        customer.setEmail(AppData.App.getUser().getEmail());
        customer.setIDCardNumber(idcard); //TODO:idcard应该在地址管理中录入，等待服务器修改接口
        customer.setToken(Token.getLocalToken());
        customer.setOpenId(AppData.App.getOpenId());
        //Customer
        customer.setBillAddr(addressController.getAddress().transToAddr());
        customer.setShipAddr(addressController.getAddress().transToAddr());
        post.setCustomer(customer);
        //Payment
        post.setPayment(new Payment("Wechat AY"));
        //Promotion
        post.setPromotion(new Promotion(coupon));

        return post;
    }

    @Override
    public void onBackPressed() {
        if (binding.pager.getCurrentItem() != 0) {
            ViewPagerUtil.last(binding.pager);
        } else {
            super.onBackPressed();
        }
    }
}
