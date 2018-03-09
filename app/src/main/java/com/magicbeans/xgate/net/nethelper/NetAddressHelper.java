package com.magicbeans.xgate.net.nethelper;

import android.text.TextUtils;

import com.magicbeans.xgate.bean.address.Address;
import com.magicbeans.xgate.bean.address.AddressWrap;
import com.magicbeans.xgate.bean.common.CommonEntity;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by liaoinstan on 2018/1/22.
 * 地址管理相关接口管理，外部处理只需关心传参及回调结果，无需关心请求细节，该类负责处理请求细节
 */

public class NetAddressHelper {
    private static NetAddressHelper instance;

    private NetAddressHelper() {
    }

    public static synchronized NetAddressHelper getInstance() {
        if (instance == null) instance = new NetAddressHelper();
        return instance;
    }

    /////////////////////////////////////

    //获取地址列表
    public void netGetAddressList(boolean isBillAddr, final OnAddressListCallback callback) {
        Map<String, Object> param = new NetParam()
                .put("addrtype", isBillAddr ? 1 : 2)    //addrtype 1: Billing Address 2: Delivery Address
                .put("AccountID", Token.getLocalAccountId())
                .put("token", Token.getLocalToken())
                .build();
        NetApi.NI().netGetAddressList(param).enqueue(new STCallback<AddressWrap>(AddressWrap.class) {
            @Override
            public void onSuccess(int status, AddressWrap wrap, String msg) {
                if (callback != null) callback.onSuccess(wrap.getAddresses());
            }

            @Override
            public void onError(int status, String msg) {
                if (callback != null) callback.onError(msg);
            }
        });
    }

    //删除地址
    public void netDeleteAddress(final String addressId, final OnAddressSimpleCallback callback) {
        Map<String, Object> param = new NetParam()
                .put("AccountID", Token.getLocalAccountId())
                .put("token", Token.getLocalToken())
                .put("AddId", addressId)
                .build();
        NetApi.NI().netDeleteAddress(param).enqueue(new STCallback<CommonEntity>(CommonEntity.class) {
            @Override
            public void onSuccess(int status, CommonEntity com, String msg) {
                if (com.getReponseCode() == 5) {
                    if (callback != null) callback.onSuccess();
                } else {
                    onError(com.getReponseCode(), msg);
                }
            }

            @Override
            public void onError(int status, String msg) {
                if (callback != null) callback.onError(status + ":" + msg);
            }
        });
    }

    //设置默认地址
    public void netSetDefaultAddress(boolean isBillAddr, final String addressId, final OnAddressSimpleCallback callback) {
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
                    if (callback != null) callback.onSuccess();
                } else {
                    onError(com.getReponseCode(), msg);
                }
            }

            @Override
            public void onError(int status, String msg) {
                if (callback != null) callback.onError(status + ":" + msg);
            }
        });
    }

    //新增或者更新地址
    //有AddId则更新，无则新增
    public void netAddOrUpdateAddress(String AddId, boolean isBillAddr, String addrNickname, String tel, String country, String city, String state, String postCode, String address, final OnAddressSimpleCallback callback) {
        Map<String, Object> param = new NetParam()
                .put("AddId", AddId)
                .put("AccountID", Token.getLocalAccountId())
                .put("token", Token.getLocalToken())
                .put("addrtype", isBillAddr ? 1 : 2)
                .put("addrNickname", addrNickname)
                .put("firstname", "none")//TODO:UI与接口不匹配
                .put("lastname", "none")//TODO:UI与接口不匹配
                .put("tel", tel)
                .put("address1", address)
                .put("state", state)
                .put("city", city)
                .put("country", country)
                .put("postcode", postCode)
                .build();
        Call<ResponseBody> call;
        if (TextUtils.isEmpty(AddId)) {
            call = NetApi.NI().netAddAddress(param);
        } else {
            call = NetApi.NI().netUpdateAddress(param);
        }
        call.enqueue(new STCallback<CommonEntity>(CommonEntity.class) {
            @Override
            public void onSuccess(int status, CommonEntity com, String msg) {
                if (com.getReponseCode() == 0 || com.getReponseCode() == 1) {
                    if (callback != null) callback.onSuccess();
                } else {
                    onError(com.getReponseCode(), msg);
                }
            }

            @Override
            public void onError(int status, String msg) {
                if (callback != null) callback.onError(status + ":" + msg);
            }
        });
    }

    //############### 接口回调 ###############

    public interface OnAddressListCallback {
        void onSuccess(List<Address> addressList);

        void onError(String msg);
    }

    public interface OnAddressSimpleCallback {
        void onSuccess();

        void onError(String msg);
    }
}
