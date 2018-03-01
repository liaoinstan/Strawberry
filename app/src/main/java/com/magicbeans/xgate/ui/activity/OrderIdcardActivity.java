package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.databinding.ActivityOrderIdcardBinding;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

public class OrderIdcardActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private ActivityOrderIdcardBinding binding;

    public static void start(Context context) {
        Intent intent = new Intent(context, OrderIdcardActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_idcard);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
        binding.btnRight.setOnClickListener(this);
    }

    private void initCtrl() {
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                String idcard = binding.editIdcard.getText().toString();
                EventBean eventBean = new EventBean(EventBean.EVENT_ADD_IDCARD);
                eventBean.put("idcard", idcard);
                EventBus.getDefault().post(eventBean);
                finish();
                break;
        }
    }
}
