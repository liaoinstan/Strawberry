package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;

import com.alibaba.fastjson.JSONReader;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.bean.address.Address;
import com.magicbeans.xgate.bean.address.City;
import com.magicbeans.xgate.bean.address.District;
import com.magicbeans.xgate.bean.address.Province;
import com.magicbeans.xgate.common.AppVali;
import com.magicbeans.xgate.databinding.ActivityAddressaddBinding;
import com.magicbeans.xgate.helper.AreaReadHelper;
import com.magicbeans.xgate.helper.AreaReadHelper2;
import com.magicbeans.xgate.net.nethelper.NetAddressHelper;
import com.magicbeans.xgate.ui.adapter.SpinnerCityAdapter;
import com.magicbeans.xgate.ui.adapter.SpinnerDistrictAdapter;
import com.magicbeans.xgate.ui.adapter.SpinnerProviceAdapter;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class AddressAddActivity extends BaseAppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ActivityAddressaddBinding binding;
    private SpinnerProviceAdapter adapterProvince;
    private SpinnerCityAdapter adapterCity;
    private SpinnerDistrictAdapter adapterDistrict;

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
        adapterProvince = new SpinnerProviceAdapter(this);
        binding.spinnerProvince.setAdapter(adapterProvince);
        binding.spinnerProvince.setOnItemSelectedListener(this);

        adapterCity = new SpinnerCityAdapter(this);
        binding.spinnerCity.setAdapter(adapterCity);
        binding.spinnerCity.setOnItemSelectedListener(this);

        adapterDistrict = new SpinnerDistrictAdapter(this);
        binding.spinnerDistrict.setAdapter(adapterDistrict);
        binding.spinnerDistrict.setOnItemSelectedListener(this);

        binding.btnGo.setOnClickListener(this);

//        binding.swichDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                ToastUtil.showToastShort("目前API接口暂时没有此功能，暂且请在添加后手动设置");
//            }
//        });
    }

    private void initData() {
        List<Province> provinces = AreaReadHelper2.getInstance().readProvinceList(this);
        adapterProvince.getResults().clear();
        adapterProvince.getResults().addAll(provinces);
        adapterProvince.notifyDataSetChanged();

        setAddressData();
    }

    private void setAddressData() {
        if (address != null) {
            binding.editName.setText(address.getAddressNickName());
            binding.editPhone.setText(address.getTel());
            binding.swichDefault.setChecked(address.isDefault());
            String[] splits = address.getAddress().split(",");
            //TODO:这个数据结构还要和后台商讨下，目前暂时这样
            binding.editAddress.setText(splits[0]);
            binding.spinnerProvince.setSelection(adapterProvince.setPositionByName(splits[3]));
            binding.spinnerCity.setSelection(adapterCity.setPositionByName(splits[2]));
            binding.spinnerDistrict.setSelection(adapterDistrict.setPositionByName(splits[1]));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_go:
                String name = binding.editName.getText().toString();
                String phone = binding.editPhone.getText().toString();
                String address = binding.editAddress.getText().toString();
                boolean isDefault = binding.swichDefault.isChecked();
                Province province = (Province) binding.spinnerProvince.getSelectedItem();
                City city = (City) binding.spinnerCity.getSelectedItem();
                District district = (District) binding.spinnerDistrict.getSelectedItem();
                String msg = AppVali.addAddress(name, phone, province.getName(), city.getName(), district.getName(), address);
                if (msg != null) {
                    ToastUtil.showToastShort(msg);
                } else {
                    netAddAddress(true, name, phone, province.getName(), city.getName(), district.getName(), district.getPostcode(), address, isDefault);
                }
                break;
        }
    }

    //新增地址
    public void netAddAddress(final boolean isBillAddr, String addrNickname, String tel, String country, String city, String state, String postCode, String addressStr, boolean isDefault) {
        showLoadingDialog();
        String AddId = address != null ? address.getAddressID() : "";
        NetAddressHelper.getInstance().netAddOrUpdateAddress(AddId, isBillAddr, addrNickname, tel, country, city, state, postCode, addressStr, isDefault, new NetAddressHelper.OnAddressSimpleCallback() {
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == binding.spinnerProvince) {
            Province provice = (Province) adapterProvince.getItem(position);
            adapterCity.getResults().clear();
            adapterCity.getResults().addAll(provice.getCitysBean());
            adapterCity.notifyDataSetChanged();
            //设置第三级
            City city = (City) adapterCity.getItem(0);
            adapterDistrict.getResults().clear();
            adapterDistrict.getResults().addAll(city.getDistrictBean());
            adapterDistrict.notifyDataSetChanged();
        } else if (parent == binding.spinnerCity) {
            City city = (City) adapterCity.getItem(position);
            adapterDistrict.getResults().clear();
            adapterDistrict.getResults().addAll(city.getDistrictBean());
            adapterDistrict.notifyDataSetChanged();
        } else if (parent == binding.spinnerDistrict) {
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
