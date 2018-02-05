package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.ins.common.helper.ValiHelper;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.address.AddressWrap;
import com.magicbeans.xgate.bean.common.CommonEntity;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.common.AppVali;
import com.magicbeans.xgate.databinding.ActivityAddressaddBinding;
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Map;

public class AddressAddActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private ActivityAddressaddBinding binding;
    private ArrayAdapter adapterProvince;
    private ArrayAdapter adapterCity;
    private ArrayAdapter adapterDistrict;

    public static void start(Context context) {
        if (AppHelper.User.isLogin()) {
            Intent intent = new Intent(context, AddressAddActivity.class);
            context.startActivity(intent);
        } else {
            LoginActivity.start(context);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addressadd);
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
        adapterProvince = new ArrayAdapter<>(this, R.layout.lay_spinner_item, new ArrayList<String>());
        adapterProvince.setDropDownViewResource(R.layout.lay_spinner_item);
        binding.spinnerProvince.setAdapter(adapterProvince);

        adapterCity = new ArrayAdapter<>(this, R.layout.lay_spinner_item, new ArrayList<String>());
        adapterCity.setDropDownViewResource(R.layout.lay_spinner_item);
        binding.spinnerCity.setAdapter(adapterCity);

        adapterDistrict = new ArrayAdapter<>(this, R.layout.lay_spinner_item, new ArrayList<String>());
        adapterDistrict.setDropDownViewResource(R.layout.lay_spinner_item);
        binding.spinnerDistrict.setAdapter(adapterDistrict);

        binding.btnGo.setOnClickListener(this);
    }

    private void initData() {
        //TODO:目前还没有相关接口，暂时使用假数据
        adapterProvince.clear();
        adapterProvince.add("四川省");
        adapterProvince.add("福建省");
        adapterProvince.add("guangdong");
        adapterProvince.notifyDataSetChanged();

        adapterCity.clear();
        adapterCity.add("成都市");
        adapterCity.add("宜宾市");
        adapterCity.add("绵阳市");
        adapterCity.add("guangzhou");
        adapterCity.notifyDataSetChanged();

        adapterDistrict.clear();
        adapterDistrict.add("成华区");
        adapterDistrict.add("高新区");
        adapterDistrict.add("武侯区");
        adapterDistrict.add("锦江区");
        adapterDistrict.add("青阳区");
        adapterDistrict.add("金牛区");
        adapterDistrict.add("天府新区");
        adapterDistrict.add("tianhequ");
        adapterDistrict.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_go:
                String name = binding.editName.getText().toString();
                String phone = binding.editPhone.getText().toString();
                String address = binding.editAddress.getText().toString();
                String province = (String) binding.spinnerProvince.getSelectedItem();
                String city = (String) binding.spinnerCity.getSelectedItem();
                String district = (String) binding.spinnerDistrict.getSelectedItem();
                String msg = AppVali.addAddress(name, phone, province, city, district, address);
                if (msg != null) {
                    ToastUtil.showToastShort(msg);
                } else {
                    netAddAddress(true, name, phone, province, city, district, address);
                }
                break;
        }
    }

    //删除地址
    public void netAddAddress(boolean isBillAddr, String addrNickname, String tel, String country, String city, String state, String address) {
        showLoadingDialog();
        Map<String, Object> param = new NetParam()
                .put("AccountID", Token.getLocalAccountId())
                .put("token", Token.getLocalToken())
                .put("addrtype", isBillAddr ? 1 : 2)
                .put("addrNickname", addrNickname)
                .put("firstname", "liaoinstan")//TODO:UI与接口不匹配
                .put("lastname", "albert")//TODO:UI与接口不匹配
                .put("tel", tel)
                .put("address1", address)
                .put("state", state)
                .put("city", city)
                .put("country", country)
                .put("postcode", "610100")//TODO:目前UI与接口不匹配，UI上没有邮政编码，但是接口比传，暂时写个默认的，这个问题需要反馈给后台
                .build();
        NetApi.NI().netAddAddress(param).enqueue(new STCallback<CommonEntity>(CommonEntity.class) {
            @Override
            public void onSuccess(int status, CommonEntity com, String msg) {
                if (com.getReponseCode() == 0) {
                    ToastUtil.showToastShort("添加成功");
                    EventBus.getDefault().post(new EventBean(EventBean.EVENT_REFRESH_ADDRESSLIST));
                    finish();
                } else {
                    ToastUtil.showToastShort(msg);
                }
                dismissLoadingDialog();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                dismissLoadingDialog();
            }
        });
    }


}
