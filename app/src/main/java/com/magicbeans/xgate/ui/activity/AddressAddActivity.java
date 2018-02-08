package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.address.Address;
import com.magicbeans.xgate.common.AppVali;
import com.magicbeans.xgate.databinding.ActivityAddressaddBinding;
import com.magicbeans.xgate.helper.AppHelper;
import com.magicbeans.xgate.net.nethelper.NetAddressHelper;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class AddressAddActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private ActivityAddressaddBinding binding;
    private ArrayAdapter adapterProvince;
    private ArrayAdapter adapterCity;
    private ArrayAdapter adapterDistrict;

    private Address address;

    public static void start(Context context) {
        Intent intent = new Intent(context, AddressAddActivity.class);
        context.startActivity(intent);
    }

    //如果有address参数，表示对已有地址进行修改，否则新增
    public static void startForUpdate(Context context, Address address) {
        Intent intent = new Intent(context, AddressAddActivity.class);
        intent.putExtra("address", address);
        context.startActivity(intent);
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
        address = (Address) getIntent().getSerializableExtra("address");
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

        binding.swichDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ToastUtil.showToastShort("目前API接口暂时没有此功能，暂且请在添加后手动设置");
            }
        });
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

        setAddressData();
    }

    private void setAddressData() {
        if (address != null) {
            binding.editName.setText(address.getAddressNickName());
            binding.editPhone.setText(address.getTel());
            binding.editAddress.setText(address.getAddress().split(",")[0]);//这个数据结构还要和后台商讨下，目前暂时这样
        }
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

    //新增地址
    public void netAddAddress(final boolean isBillAddr, String addrNickname, String tel, String country, String city, String state, String addressStr) {
        showLoadingDialog();
        String AddId = address != null ? address.getAddressID() : "";
        NetAddressHelper.getInstance().netAddOrUpdateAddress(AddId, isBillAddr, addrNickname, tel, country, city, state, addressStr, new NetAddressHelper.OnAddressSimpleCallback() {
            @Override
            public void onSuccess() {
                ToastUtil.showToastShort(address != null ? "更新成功" : "添加成功");
                //通知地址管理列表刷新
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_REFRESH_ADDRESSLIST));
                //通知带单页面刷新地址区域
                EventBus.getDefault().post(new EventBean(EventBean.EVENT_REFRESH_ORDERADD_ADDRESS));
                finish();
            }

            @Override
            public void onError(String msg) {
                ToastUtil.showToastShort(msg);
                dismissLoadingDialog();
            }
        });
    }
}
