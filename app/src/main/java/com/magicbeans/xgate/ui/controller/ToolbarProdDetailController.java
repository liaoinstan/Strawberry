package com.magicbeans.xgate.ui.controller;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.bean.product.ProductDetail;
import com.magicbeans.xgate.databinding.LayToobbarProductdetailBinding;
import com.magicbeans.xgate.sharesdk.ShareDialog;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ToolbarProdDetailController extends BaseController<LayToobbarProductdetailBinding> {

    ProductDetail productDetail;

    public ToolbarProdDetailController(LayToobbarProductdetailBinding binding) {
        super(binding);
        initCtrl();
        initData();
    }

    public void initCtrl() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_back);
        binding.toolbar.bringToFront();
        binding.toolbar.setTitle("");
        ((AppCompatActivity) context).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) context).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productDetail != null) {
                    Product2 product2 = productDetail.getSelectProduct(productDetail.getProdID());
                    if (product2 != null) {
                        product2.setBrandName(productDetail.getBrandName());
                        new ShareDialog(context).setShareProduct2(product2).show();
                    }
                }
            }
        });
    }

    public void initData() {
    }

    //#########  对外方法 ############

    public void setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener listener) {
        binding.radiogroupHeader.setOnCheckedChangeListener(listener);
    }

    public int getHeight() {
        return binding.getRoot().getHeight();
    }

    //根据滚动位置反向设置tab的切换
    public void setTabByScrollHeight(int hightEva, int hightDetail, int hightRecomment, int scrollY, int oldScrollY) {
        if (scrollY <= hightEva && oldScrollY > hightEva) {
            //从hightEva往上滚动，高亮产品
            binding.radiogroupHeader.check(R.id.radio_product);
        } else if ((scrollY >= hightEva && oldScrollY < hightEva)
                || (scrollY <= hightDetail && oldScrollY > hightDetail)) {
            //从hightEva往下滚动，高亮评论
            //从hightDetail往上滚动，高亮评论
            binding.radiogroupHeader.check(R.id.radio_eva);
        } else if ((scrollY >= hightDetail && oldScrollY < hightDetail)
                || (scrollY <= hightRecomment && oldScrollY > hightRecomment)) {
            //从hightDetail往下滚动，高亮详情
            //从hightRecomment往上滚动，高亮详情
            binding.radiogroupHeader.check(R.id.radio_detail);
        } else if (scrollY >= hightRecomment && oldScrollY < hightRecomment) {
            //从hightRecomment往下滚动，高亮推荐
            binding.radiogroupHeader.check(R.id.radio_recommend);
        }
    }

    //radioButton是否处于被按下的状态（用于判断是否是用户手动点击触发的view事件）
    public boolean isRadioPressed() {
        //检查radiogroup的所有rodiobutton，如果有一个处于按下状态则返回true
        int childCount = binding.radiogroupHeader.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (binding.radiogroupHeader.getChildAt(i).isPressed()) {
                return true;
            }
        }
        return false;
    }

    public void setData(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }
}
