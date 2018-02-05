package com.magicbeans.xgate.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ins.common.helper.SelectHelper;
import com.ins.common.interfaces.OnRecycleItemClickListener;
import com.ins.common.ui.dialog.DialogSure;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.ins.common.view.LoadingLayout;
import com.liaoinstan.springview.widget.SpringView;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.address.Address;
import com.magicbeans.xgate.bean.address.AddressWrap;
import com.magicbeans.xgate.bean.common.CommonEntity;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.databinding.ItemAddressBinding;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;
import com.magicbeans.xgate.ui.dialog.DialogLoading;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecycleAdapterAddress extends RecyclerView.Adapter<RecycleAdapterAddress.Holder> {

    private Context context;
    private List<Address> results = new ArrayList<>();

    private SpringView springView;
    private LoadingLayout loadingLayout;

    public List<Address> getResults() {
        return results;
    }

    public RecycleAdapterAddress(Context context) {
        this.context = context;
    }

    @Override
    public RecycleAdapterAddress.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder((ItemAddressBinding) DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_address, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecycleAdapterAddress.Holder holder, final int position) {
        final Address address = results.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(holder, position);
            }
        });
        holder.binding.imgItemAddressEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToastShort("建设中");
            }
        });
        holder.binding.textItemAddressDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSure.showDialog(context, "确认要删除该地址？", new DialogSure.CallBack() {
                    @Override
                    public void onSure() {
                        netDeleteAddress(address.getAddressID());
                    }
                });
            }
        });
        holder.binding.checkItemAddressDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netSetDefaultAddress(true, address.getAddressID());
            }
        });

        holder.binding.checkItemAddressDefault.setSelected(address.isDefault());
        holder.binding.textName.setText(address.getAddressNickName() + " " + address.getTelEncry());
        holder.binding.textAddress.setText(address.getAddress());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ItemAddressBinding binding;

        public Holder(ItemAddressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    //############## 业务方法 ###############

    //设置默认地址
    private void setDefaultAddress(String addressId) {
        for (Address address : results) {
            if (addressId.equals(address.getAddressID())) {
                address.setIsDefault(true);
            } else {
                address.setIsDefault(false);
            }
        }
    }

    //获取默认地址
    private Address getSelectItem() {
        return AddressWrap.getDefaultAddress(results);
    }

    private void showLoadingDialog() {
        if (context instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) context).showLoadingDialog();
        }
    }

    private void dismissLoadingDialog() {
        if (context instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) context).dismissLoadingDialog();
        }
    }

    //########## get & set ###########

    public void setSpringView(SpringView springView) {
        this.springView = springView;
    }

    public void setLoadingLayout(LoadingLayout loadingLayout) {
        this.loadingLayout = loadingLayout;
    }

    //########## 对外接口 ###########

    private OnRecycleItemClickListener listener;

    public void setOnItemClickListener(OnRecycleItemClickListener listener) {
        this.listener = listener;
    }

    //########## 网络接口 ###########

    //获取地址列表
    public void netGetAddressList(boolean isBillAddr, final boolean needLoading) {
        if (needLoading) showLoadingDialog();
        Map<String, Object> param = new NetParam()
                .put("addrtype", isBillAddr ? 1 : 2)    //addrtype 1: Billing Address 2: Delivery Address
                .put("AccountID", Token.getLocalAccountId())
                .put("token", Token.getLocalToken())
                .build();
        NetApi.NI().netGetAddressList(param).enqueue(new STCallback<AddressWrap>(AddressWrap.class) {
            @Override
            public void onSuccess(int status, AddressWrap wrap, String msg) {
                List<Address> addressList = wrap.getAddresses();
                if (!StrUtil.isEmpty(addressList)) {
                    results.clear();
                    results.addAll(addressList);
                    notifyDataSetChanged();
                    if (loadingLayout != null) loadingLayout.showOut();
                } else {
                    if (loadingLayout != null) loadingLayout.showLackView();
                }
                if (needLoading) dismissLoadingDialog();
                if (springView != null) springView.onFinishFreshAndLoad();
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
                if (springView != null) springView.onFinishFreshAndLoad();
                if (needLoading) dismissLoadingDialog();
            }
        });
    }

    //删除地址
    public void netDeleteAddress(final String addressId) {
        showLoadingDialog();
        Map<String, Object> param = new NetParam()
                .put("AccountID", Token.getLocalAccountId())
                .put("token", Token.getLocalToken())
                .put("AddId", addressId)
                .build();
        NetApi.NI().netDeleteAddress(param).enqueue(new STCallback<CommonEntity>(CommonEntity.class) {
            @Override
            public void onSuccess(int status, CommonEntity com, String msg) {
                if (com.getReponseCode() == 5) {
                    //更新列表
                    AddressWrap.removeById(results, addressId);
                    if (!StrUtil.isEmpty(results)) {
                        if (loadingLayout != null) loadingLayout.showOut();
                    } else {
                        if (loadingLayout != null) loadingLayout.showLackView();
                    }
                    notifyDataSetChanged();
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

    //设置默认地址
    public void netSetDefaultAddress(boolean isBillAddr, final String addressId) {
        //保存上次的默认地址
        final Address lastSelectAddress = getSelectItem();
        //设置新的默认地址
        setDefaultAddress(addressId);
        notifyDataSetChanged();
        //发起请求
        Map<String, Object> param = new NetParam()
                .put("AccountID", Token.getLocalAccountId())
                .put("token", Token.getLocalToken())
                .put("AddId", addressId)
                .put("addrtype", isBillAddr ? 1 : 2)
                .put("action", isBillAddr ? "SETDEFB" : "SETDEFS")
                .build();
        NetApi.NI().netSetDefaultAddress(param).enqueue(new STCallback<CommonEntity>(CommonEntity.class) {
            @Override
            public void onSuccess(int status, CommonEntity com, String msg) {
                if (com.getReponseCode() == 6 || com.getReponseCode() == 7) {
                    //设置成功
                    ToastUtil.showToastShort("设置默认地址成功");
                } else {
                    //设置失败，回滚到上次的默认地址
                    if (lastSelectAddress != null) {
                        setDefaultAddress(lastSelectAddress.getAddressID());
                        notifyDataSetChanged();
                    }
                    ToastUtil.showToastShort(msg);
                }
            }

            @Override
            public void onError(int status, String msg) {
                //设置失败，回滚到上次的默认地址
                if (lastSelectAddress != null) {
                    setDefaultAddress(lastSelectAddress.getAddressID());
                    notifyDataSetChanged();
                }
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
