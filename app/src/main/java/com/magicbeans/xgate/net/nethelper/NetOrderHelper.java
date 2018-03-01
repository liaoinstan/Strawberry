package com.magicbeans.xgate.net.nethelper;

import com.magicbeans.xgate.bean.order.Order;
import com.magicbeans.xgate.bean.order.OrderDetail;
import com.magicbeans.xgate.bean.order.OrderWrap;
import com.magicbeans.xgate.bean.user.Token;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;

import java.util.List;
import java.util.Map;

/**
 * Created by liaoinstan on 2018/1/22.
 * 订单管理相关接口管理，外部处理只需关心传参及回调结果，无需关心请求细节，该类负责处理请求细节
 */

public class NetOrderHelper {
    private static NetOrderHelper instance;

    private NetOrderHelper() {
    }

    public static synchronized NetOrderHelper getInstance() {
        if (instance == null) instance = new NetOrderHelper();
        return instance;
    }

    /////////////////////////////////////

    //订单历史列表
    public void netOrderHistory(final OnOrderListCallback callback) {
        Map<String, Object> param = new NetParam()
                .put("token", Token.getLocalToken())
                .put("years", "30d")
                .build();
        NetApi.NI().netOrderHistory(param).enqueue(new STCallback<OrderWrap>(OrderWrap.class) {
            @Override
            public void onSuccess(int status, OrderWrap wrap, String msg) {
                if (wrap.getResponseCode() == 0) {
                    List<Order> orders = wrap.getOrders();
                    if (callback != null) callback.onOrderList(orders);
                } else {
                    onError(wrap.getResponseCode(), wrap.getResponseMsg());
                }
            }

            @Override
            public void onError(int status, String msg) {
                if (callback != null) callback.onError(status + ":" + msg);
            }
        });
    }


    //订单详情
    public void netOrderDetail(String SOID, final OnOrderDetailCallback callback) {
        Map<String, Object> param = new NetParam()
                .put("token", Token.getLocalToken())
                .put("SOId", SOID)
                .build();
        NetApi.NI().netOrderDetail(param).enqueue(new STCallback<OrderDetail>(OrderDetail.class) {
            @Override
            public void onSuccess(int status, OrderDetail orderDetail, String msg) {
                if (callback != null) callback.onOrderDetail(orderDetail);
            }

            @Override
            public void onError(int status, String msg) {
                if (callback != null) callback.onError(status + ":" + msg);
            }
        });
    }


    //############# 回调接口 #############
    public interface OnOrderListCallback {
        void onOrderList(List<Order> orderList);

        void onError(String msg);
    }

    public interface OnOrderDetailCallback {
        void onOrderDetail(OrderDetail orderDetail);

        void onError(String msg);
    }
}
