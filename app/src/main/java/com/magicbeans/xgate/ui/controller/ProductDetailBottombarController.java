package com.magicbeans.xgate.ui.controller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.ins.common.helper.ShopAnimHelper;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.bean.product.ProductDetail;
//import com.magicbeans.xgate.data.db.AppDatabaseManager;
//import com.magicbeans.xgate.data.db.entity.ShopCart;
import com.magicbeans.xgate.data.db.AppDatabaseManager;
import com.magicbeans.xgate.databinding.LayProductdetailBottombarBinding;
import com.magicbeans.xgate.net.nethelper.NetShopCartHelper;
import com.magicbeans.xgate.ui.activity.ShopcartActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ProductDetailBottombarController implements View.OnClickListener {

    private Context context;
    private LayProductdetailBottombarBinding binding;
    private View root;

    private ProductDetail productDetail;

    public ProductDetailBottombarController(LayProductdetailBottombarBinding binding, View root) {
        this.root = root;
        this.binding = binding;
        this.context = binding.getRoot().getContext();
        initCtrl();
        initData();
    }

    private void initCtrl() {
        binding.textShopbag.setOnClickListener(this);
        binding.textFavo.setOnClickListener(this);
        binding.textAdd.setOnClickListener(this);
    }

    private void initData() {
    }

    public void setData(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_shopbag:
                ShopcartActivity.start(context);
                break;
            case R.id.text_favo:
                NetShopCartHelper.getInstance().netGetShopCartList();
                break;
            case R.id.text_add:
                if (productDetail != null) {
                    Product2 product2 = productDetail.getSelectProduct(productDetail.getProdID());
                    if (product2 != null) {
                        //###### 添加到本地数据库 ######
                        AppDatabaseManager.getInstance().insertShopCartTable(product2);
//                        ToastUtil.showToastLong("测试：\nid：" + product2.getProdID() + "\n类别:" + product2.getSizeText() + "\n数量：" + product2.getCount() + "\n添加成功");
                        EventBus.getDefault().post(new EventBean(EventBean.EVENT_REFRESH_SHOPCART));
                        //###### 添加到服务器 ######
                        NetShopCartHelper.getInstance().netAddShopCart(product2.getProdID());
                        //###### 飞入动画 ######
                        ShopAnimHelper.newInstance().quickStart(binding.textAdd, binding.textShopbag, (ViewGroup) root, product2.getHeaderImg());
                    }
                }
                break;
        }
    }
}
