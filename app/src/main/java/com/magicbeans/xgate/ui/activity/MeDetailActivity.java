package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.bigkoo.pickerview.TimePickerView;
import com.ins.common.helper.CropHelper;
import com.ins.common.helper.CropHelperEx;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.TimeUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.user.User;
import com.magicbeans.xgate.common.AppData;
import com.magicbeans.xgate.databinding.ActivityMedetailBinding;
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.view.DayPicker;

import java.util.Date;

public class MeDetailActivity extends BaseAppCompatActivity implements CropHelper.CropInterface, TimePickerView.OnTimeSelectListener {

    private DayPicker dayPicker;
    private CropHelperEx cropHelperEx;
    private ActivityMedetailBinding binding;

    public static void start(Context context) {
        if (!AppHelper.User.isLogin()) {
            LoginActivity.start(context);
        } else {
            Intent intent = new Intent(context, MeDetailActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_medetail);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
        cropHelperEx = new CropHelperEx(this, this);
        cropHelperEx.setNeedCrop(true);
        dayPicker = new DayPicker(this, this);
    }

    private void initView() {
    }

    private void initCtrl() {
    }

    private void initData() {
        User user = AppData.App.getUser();
        if (user != null) {
            binding.textMedetailNick.setText(user.getNickname());
            GlideUtil.loadCircleImg(binding.imgMedetailHeader, R.drawable.default_header, user.getHeadImageURL());
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                break;
            case R.id.lay_medetail_header:
                cropHelperEx.showDefaultDialog();
                break;
            case R.id.lay_medetail_name:
                break;
            case R.id.lay_medetail_gender:
                break;
            case R.id.lay_medetail_birthday:
                dayPicker.show();
                break;
            case R.id.lay_medetail_address:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cropHelperEx.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void cropResult(String path) {
        GlideUtil.loadCircleImg(binding.imgMedetailHeader, R.drawable.default_header, path);
    }

    @Override
    public void cancel() {
    }

    @Override
    public void onTimeSelect(Date date, View v) {
        binding.textMedetailBirthday.setText(TimeUtil.getTimeFor("yyyy年MM月dd日", date));
    }
}
