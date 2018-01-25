package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.databinding.ActivityAddressaddBinding;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import java.util.ArrayList;

public class AddressAddActivity extends BaseAppCompatActivity {

    private ActivityAddressaddBinding binding;
    private ArrayAdapter adapterProvince;
    private ArrayAdapter adapterCity;
    private ArrayAdapter adapterDistrict;

    public static void start(Context context) {
        Intent intent = new Intent(context, AddressAddActivity.class);
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

    }

    private void initData() {
        adapterProvince.clear();
        adapterProvince.add("四川省");
        adapterProvince.add("福建省");
        adapterProvince.notifyDataSetChanged();

        adapterCity.clear();
        adapterCity.add("成都市");
        adapterCity.add("宜宾市");
        adapterCity.add("绵阳市");
        adapterCity.notifyDataSetChanged();

        adapterDistrict.clear();
        adapterDistrict.add("成华区");
        adapterDistrict.add("高新区");
        adapterDistrict.add("武侯区");
        adapterDistrict.add("锦江区");
        adapterDistrict.add("青阳区");
        adapterDistrict.add("金牛区");
        adapterDistrict.add("天府新区");
        adapterDistrict.notifyDataSetChanged();
    }
}
