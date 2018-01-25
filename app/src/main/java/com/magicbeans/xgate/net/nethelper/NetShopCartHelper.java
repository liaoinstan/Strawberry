package com.magicbeans.xgate.net.nethelper;

import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.bean.common.CommonEntity;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
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
        NetApi.NI().netAddShopCart(param).enqueue(new STFormatCallback<CommonEntity>(CommonEntity.class) {
            @Override
            public void onSuccess(int status, CommonEntity com, String msg) {
                ToastUtil.showToastShort("添加成功");
            }

            @Override
            public void onError(int status, String msg) {
                ToastUtil.showToastShort("添加失败");
            }
        });
    }

    //获取购物车列表
    public void netGetShopCartList() {
        Map<String, Object> param = new NetParam()
                .put("token", Token.getLocalToken())
                .build();
        NetApi.NI().netGetShopCartList(param).enqueue(new STFormatCallback<CommonEntity>(CommonEntity.class) {
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
