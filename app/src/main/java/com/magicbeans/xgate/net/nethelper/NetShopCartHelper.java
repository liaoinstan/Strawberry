package com.magicbeans.xgate.net.nethelper;

import com.ins.common.utils.L;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.bean.common.CommonEntity;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.bean.user.User;
import com.magicbeans.xgate.common.AppData;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;
import com.magicbeans.xgate.net.STFormatCallback;

import java.util.Map;

/**
 * Created by Marie on 2018/1/22.
 * 管理购物车的网络请求
 */

public class NetShopCartHelper {
    private static NetShopCartHelper instance;

    private NetShopCartHelper() {
    }

    public static synchronized NetShopCartHelper getInstance() {
        if (instance == null) instance = new NetShopCartHelper();
        return instance;
    }

    /////////////////////////////////////

    //添加购物车
    public void netAddShopCart(String ProdId) {
        Map<String, Object> param = new NetParam()
                .put("ProdId", ProdId)
                .put("token", Token.getLocalToken())
                .build();
        String url = NetParam.createUrl(AppData.Url.apiAddShopCart(), param);
        NetApi.NI().commonNetwork(url).enqueue(new STFormatCallback<CommonEntity>(CommonEntity.class) {
            @Override
            public void onSuccess(int status, CommonEntity com, String msg) {
                ToastUtil.showToastShort(msg);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }

    //获取购物车列表
    public void netGetShopCartList() {
        Map<String, Object> param = new NetParam()
                .put("token", Token.getLocalToken())
                .build();
        String url = NetParam.createUrl(AppData.Url.apiGetShopCartList(), param);
        NetApi.NI().commonNetwork(url).enqueue(new STFormatCallback<CommonEntity>(CommonEntity.class) {
            @Override
            public void onSuccess(int status, CommonEntity com, String msg) {
                ToastUtil.showToastShort(msg);
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort(msg);
            }
        });
    }
}
