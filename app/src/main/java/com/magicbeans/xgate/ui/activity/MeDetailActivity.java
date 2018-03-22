package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.TimePickerView;
import com.ins.common.helper.CropHelper;
import com.ins.common.helper.CropHelperEx;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.TimeUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.user.User;
import com.magicbeans.xgate.common.AppData;
import com.magicbeans.xgate.databinding.ActivityMedetailBinding;
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.net.nethelper.NetTokenHelper;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.dialog.DialogBottomGender;
import com.magicbeans.xgate.ui.view.DayPicker;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;

//TODO:服务器接口尚未完成，这个页面暂时没有对接接口，上线紧急，屏蔽掉该页面入口
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
            if (!TextUtils.isEmpty(user.getGender())) {
                binding.textMedetailGender.setText("1".equals(user.getGender()) ? "男" : "女");
            }
            GlideUtil.loadCircleImg(binding.imgMedetailHeader, R.drawable.default_header, user.getHeadImageURL());
            binding.textMedetailNick.setText(user.getNickname());
            binding.textMedetailName.setText(user.getSurname());
            //TODO:他们的系统里面99是默认值，表示null
            if (!"99".equals(user.getDayOfBirthday()) && !"99".equals(user.getMonthOfBirthday()) && !"99".equals(user.getYearOfBirthday())) {
                binding.textMedetailBirthday.setText(user.getYearOfBirthday() + "年" + user.getMonthOfBirthday() + "月" + user.getDayOfBirthday() + "日");
            }
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_medetail_header:
                //TODO:没有上传头像接口，该版本暂时不做上传头像功能
                //cropHelperEx.showDefaultDialog();
                break;
            case R.id.lay_medetail_gender:
                DialogBottomGender.showDialog(this, new DialogBottomGender.GenderSelectListener() {
                    @Override
                    public void onSelect(String genderStr) {
                        binding.textMedetailGender.setText(genderStr);
                    }
                });
                break;
            case R.id.lay_medetail_birthday:
                dayPicker.show();
                break;
            case R.id.btn_right:
                String nickname = binding.textMedetailNick.getText().toString();
                String name = binding.textMedetailName.getText().toString();
                String gennderStr = binding.textMedetailGender.getText().toString();
                String gender = TextUtils.isEmpty(gennderStr) ? "" : ("男".equals(gennderStr) ? "1" : "0");
                String birthDayStr = binding.textMedetailBirthday.getText().toString();
                Date date = TimeUtil.getDateByStr("yyyy年MM月dd日", birthDayStr);
                netCommit(nickname, name, gender, date);
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

    private void netCommit(String nickname, String surname, String Gender, Date birthday) {
        showLoadingDialog();
        NetTokenHelper.getInstance().netUpdateUserProfile(nickname, surname, Gender, birthday, new NetTokenHelper.UserProfileCallback() {
            @Override
            public void onSuccess(int status, User user, String msg) {
                AppData.App.saveUser(user);
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_UPDATE_USER));
                dismissLoadingDialog();
                finish();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                dismissLoadingDialog();
            }
        });
    }
}
