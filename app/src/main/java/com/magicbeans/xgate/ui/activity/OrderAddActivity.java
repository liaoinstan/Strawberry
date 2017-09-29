package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.common.utils.GlideUtil;
import com.ins.common.view.bundleimgview.BundleImgEntity;
import com.ins.common.view.bundleimgview.BundleImgView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.databinding.ActivityOrderaddBinding;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import java.util.ArrayList;

public class OrderAddActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private ActivityOrderaddBinding binding;

    public static void start(Context context) {
        Intent intent = new Intent(context, OrderAddActivity.class);
        context.startActivity(intent);
//        if (AppData.App.getUser() != null) {
//            Intent intent = new Intent(context, SuggestActivity.class);
//            context.startActivity(intent);
//        } else {
//            LoginActivity.start(context);
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_orderadd);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
    }

    private void initCtrl() {
        binding.bundleview.setOnBundleLoadImgListener(new BundleImgView.OnBundleLoadImgListener() {
            @Override
            public void onloadImg(ImageView imageView, String imgurl, int defaultSrc) {
                GlideUtil.loadImgTest(imageView);
            }
        });
    }

    private void initData() {
        binding.bundleview.setPhotos(new ArrayList<BundleImgEntity>() {{
            add(new BundleImgEntity());
            add(new BundleImgEntity());
        }});
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_addressadd:
                AddressAddActivity.start(this);
                break;
            case R.id.btn_go:
                PayActivity.start(this);
                break;
        }
    }
}
