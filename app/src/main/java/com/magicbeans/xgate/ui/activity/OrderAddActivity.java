package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.L;
import com.ins.common.utils.ListUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.view.bundleimgview.BundleImgEntity;
import com.ins.common.view.bundleimgview.BundleImgView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.address.Address;
import com.magicbeans.xgate.bean.address.AddressWrap;
import com.magicbeans.xgate.bean.common.CommonEntity;
import com.magicbeans.xgate.bean.postbean.Addr;
import com.magicbeans.xgate.bean.postbean.Cart;
import com.magicbeans.xgate.bean.postbean.CreateAccountPost;
import com.magicbeans.xgate.bean.postbean.CreateOrderPost;
import com.magicbeans.xgate.bean.postbean.Customer;
import com.magicbeans.xgate.bean.postbean.FreeGift;
import com.magicbeans.xgate.bean.postbean.Payment;
import com.magicbeans.xgate.bean.postbean.Promotion;
import com.magicbeans.xgate.bean.postbean.SelectedShipment;
import com.magicbeans.xgate.bean.shopcart.ShopCart;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.common.AppData;
import com.magicbeans.xgate.databinding.ActivityOrderaddBinding;
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.net.STFormatCallback;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.controller.ShopCartContentController;
import com.magicbeans.xgate.ui.fragment.ShopBagFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

public class OrderAddActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private ActivityOrderaddBinding binding;
    private List<ShopCart> goods;
    private Address address;

    public static void start(Context context, List<ShopCart> goods) {
        if (AppHelper.User.isLogin()) {
            Intent intent = new Intent(context, OrderAddActivity.class);
            intent.putExtra("goods", ListUtil.transArrayList(goods));
            context.startActivity(intent);
        } else {
            LoginActivity.start(context);
        }
    }

    @Override
    public void onCommonEvent(EventBean event) {
        switch (event.getEvent()) {
            case EventBean.EVENT_GET_ADDRESS:
                Address address = (Address) event.get("address");
                setAddress(address);
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
    }

    private void initView() {
    }

    private void initCtrl() {
        binding.bundleview.setOnBundleLoadImgListener(new BundleImgView.OnBundleLoadImgListener() {
            @Override
            public void onloadImg(ImageView imageView, String imgurl, int defaultSrc) {
                GlideUtil.loadImg(imageView, imgurl);
            }
        });
        binding.textCount.setText("总计：" + goods.size());
        binding.textTotalPrice.setText(AppHelper.getPriceSymbol(null) + ShopCartContentController.calcuPrice(goods));
        //TODO:运费和积分抵扣暂时没有
        binding.textTransPrice.setText(AppHelper.getPriceSymbol(null) + "0.00");
        binding.textGradeDeprice.setText(AppHelper.getPriceSymbol(null) + "-0.00");
        binding.textPayPrice.setText("应付：" + AppHelper.getPriceSymbol(null) + ShopCartContentController.calcuPrice(goods));
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
                AddressActivity.startForResult(this);
                break;
            case R.id.lay_address_select:
                AddressActivity.startForResult(this);
                break;
            case R.id.btn_go:
                if (address != null) {
                    CreateOrderPost orderPost = createOrderPost();
                    netCheckout(orderPost);
                } else {
                    ToastUtil.showToastShort("请先填写送货地址");
                }
                break;
        }
    }

    /////////////////////////////////

    private CreateOrderPost createOrderPost() {
        //创建Post对象，进行赋值
        CreateOrderPost post = new CreateOrderPost();
        post.setCart(new Cart(CreateOrderPost.tansProdList(goods)));
        post.setCurrId("CNY");
        post.setRegion("CN");
        //Customer
        Customer customer = new Customer();
        customer.setEmail("liaoinstan@qq.com");
        customer.setIDCardNumber("511322199211044654");
        customer.setToken(Token.getLocalToken());
        customer.setOpenId(AppData.App.getOpenId());
        //Customer
        customer.setBillAddr(address.transToAddr());
        customer.setShipAddr(address.transToAddr());
        post.setCustomer(customer);
        //Payment
        post.setPayment(new Payment("Wechat AY"));

        return post;
    }

    //获取默认地址
    public void netGetDefaultAddress(boolean isBillAddr) {
        setVisibleType(0);
        Map<String, Object> param = new NetParam()
                .put("addrtype", isBillAddr ? 1 : 2)    //addrtype 1: Billing Address 2: Delivery Address
                .put("AccountID", Token.getLocalAccountId())
                .put("token", Token.getLocalToken())
                .build();
        NetApi.NI().netGetAddressList(param).enqueue(new STCallback<AddressWrap>(AddressWrap.class) {
            @Override
            public void onSuccess(int status, AddressWrap wrap, String msg) {
                List<Address> addressList = wrap.getAddresses();
                Address defaultAddress = AddressWrap.getDefaultAddressEx(addressList);
                setAddress(defaultAddress);
                if (defaultAddress == null) ToastUtil.showToastShort("您还没有设置配送地址，请先添加地址");
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                setVisibleType(1);
            }
        });
    }

    //checkout
    private void netCheckout(final CreateOrderPost post) {
        showLoadingDialog();
        RequestBody requestBody = NetParam.buildJsonRequestBody(post);
        NetApi.NI().netCheckout(requestBody).enqueue(new STFormatCallback<CommonEntity>(CommonEntity.class) {
            @Override
            public void onSuccess(int status, CommonEntity com, String msg) {
                ToastUtil.showToastShort("check out 成功");
                netAddOrder(post);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort("check out 失败：" + msg);
                dismissLoadingDialog();
            }
        });
    }

    //下单
    private void netAddOrder(CreateOrderPost post) {
        RequestBody requestBody = NetParam.buildJsonRequestBody(post);
        NetApi.NI().netAddOrder(requestBody).enqueue(new STFormatCallback<CommonEntity>(CommonEntity.class) {
            @Override
            public void onSuccess(int status, CommonEntity com, String msg) {
                ToastUtil.showToastShort("下单成功");
                dismissLoadingDialog();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort("下单失败：" + msg);
                dismissLoadingDialog();
            }
        });
    }
}
