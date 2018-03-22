package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.text.SpannableString;
import android.view.View;

import com.ins.common.utils.FocusUtil;
import com.ins.common.utils.SpannableStringUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.pay.PayResult;
import com.magicbeans.xgate.databinding.ActivityPayresultBinding;
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.controller.CommonRecommendController;

import org.greenrobot.eventbus.EventBus;

public class PayResultActivity extends BaseAppCompatActivity {

    public static final int STATE_PAY_SUCCESS = 0;
    public static final int STATE_PAY_FAIL = 1;
    public static final int STATE_PAY_CANCLE = 2;

    @IntDef({STATE_PAY_SUCCESS, STATE_PAY_FAIL, STATE_PAY_CANCLE})
    public @interface PayStatus {
    }

    private ActivityPayresultBinding binding;
    private CommonRecommendController commonRecommendController;
    private PayResult payResult;

    public static void start(Context context, PayResult payResult) {
        Intent intent = new Intent(context, PayResultActivity.class);
        intent.putExtra("payResult", payResult);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payresult);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
        FocusUtil.focusToTop(toolbar);
    }

    private void initBase() {
        payResult = (PayResult) getIntent().getSerializableExtra("payResult");
        commonRecommendController = new CommonRecommendController(binding.includeRecommend);
    }

    private void initView() {
    }

    private void initCtrl() {
        switch (payResult.getStatus()) {
            case 0:
                setToolbar("订单支付成功");
                break;
            case 1:
                setToolbar("订单支付失败");
                break;
            case 2:
                setToolbar("订单支付已取消");
                break;
        }
    }

    private void initData() {
        setData();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_orderdetail:
                OrderDetailActivity.start(this, payResult.getSOID());
                finish();
                break;
            case R.id.btn_gohome:
                HomeActivity.start(this);
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_JUMP_HOME));
                break;
        }
    }

    private void setData() {
//        String detail = "支付方式：信用卡支付\n订单金额：" + AppHelper.getPriceSymbol(null) + payResult.getAmount();
        String title1 = "支付方式：";
        String title2 = "\n订单金额：";
        String textPayway = payResult.getPayType();
        String textAmount = AppHelper.getPriceSymbol(null) + payResult.getAmount();
        SpannableString spannableString = SpannableStringUtil.create(this, new String[]{title1, textPayway, title2, textAmount}, new int[]{R.color.com_text_blank, R.color.st_purple_xgate, R.color.com_text_blank, R.color.st_purple_xgate});
        binding.textPayresult.setText(spannableString);
    }
}
