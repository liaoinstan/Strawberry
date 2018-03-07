package com.magicbeans.xgate.ui.controller;

import android.view.View;

import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.address.Address;
import com.magicbeans.xgate.bean.address.AddressWrap;
import com.magicbeans.xgate.bean.product.Product2;
import com.magicbeans.xgate.bean.product.ProductDetail;
import com.magicbeans.xgate.databinding.LayOrderaddAddressBinding;
import com.magicbeans.xgate.databinding.LayProductdetailDescribeBinding;
import com.magicbeans.xgate.net.nethelper.NetAddressHelper;
import com.magicbeans.xgate.ui.activity.AddressActivity;
import com.magicbeans.xgate.ui.activity.AddressAddActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */

public class OrderAddAddressController extends BaseController<LayOrderaddAddressBinding> implements View.OnClickListener{

    private Address address;

    public OrderAddAddressController(LayOrderaddAddressBinding binding) {
        super(binding);
        initCtrl();
        initData();
    }

    private void initCtrl() {
        binding.layAddressAdd.setOnClickListener(this);
        binding.layAddressSelect.setOnClickListener(this);
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lay_address_add:
                AddressAddActivity.start(context);
                break;
            case R.id.lay_address_select:
                AddressActivity.startForResult(context);
                break;
        }
    }

    //0：加载状态  1：新增状态  2：已选择状态
    public void setVisibleType(int type) {
        switch (type) {
            case 0:
                binding.progress.setVisibility(View.VISIBLE);
                binding.layAddressAdd.setVisibility(View.GONE);
                binding.layAddressSelect.setVisibility(View.GONE);
                break;
            case 1:
                binding.progress.setVisibility(View.GONE);
                binding.layAddressAdd.setVisibility(View.VISIBLE);
                binding.layAddressSelect.setVisibility(View.GONE);
                break;
            case 2:
                binding.progress.setVisibility(View.GONE);
                binding.layAddressAdd.setVisibility(View.GONE);
                binding.layAddressSelect.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setAddress(Address address) {
        this.address = address;
        if (address == null) {
            setVisibleType(1);
        } else {
            setVisibleType(2);
            binding.textAddressName.setText(address.getAddressNickName() + " " + address.getTelEncry());
            binding.textAddressDetail.setText(address.getAddress());
        }
    }

    //获取默认地址
    public void netGetDefaultAddress(boolean isBillAddr) {
        setVisibleType(0);
        NetAddressHelper.getInstance().netGetAddressList(isBillAddr, new NetAddressHelper.OnAddressListCallback() {
            @Override
            public void onSuccess(List<Address> addressList) {
                Address defaultAddress = AddressWrap.getDefaultAddressEx(addressList);
                setAddress(defaultAddress);
                if (defaultAddress == null) ToastUtil.showToastShort("您还没有设置配送地址，请先添加地址");
            }

            @Override
            public void onError(String msg) {
                ToastUtil.showToastShort(msg);
                setVisibleType(1);
            }
        });
    }

    ////////////////// get & set ////////////////////

    public Address getAddress() {
        return address;
    }
}
