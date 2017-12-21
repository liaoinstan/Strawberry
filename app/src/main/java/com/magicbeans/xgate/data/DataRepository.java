package com.magicbeans.xgate.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.ins.common.utils.StrUtil;
import com.ins.common.utils.ToastUtil;
import com.magicbeans.xgate.bean.eva.Eva;
import com.magicbeans.xgate.bean.eva.EvaWrap;
import com.magicbeans.xgate.net.NetApi;
import com.magicbeans.xgate.net.NetParam;
import com.magicbeans.xgate.net.STCallback;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/6.
 */

public class DataRepository {

    private static DataRepository INSTANCE;

    private DataRepository() {
    }

    public static DataRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (DataRepository.class) {
                INSTANCE = new DataRepository();
            }
        }
        return INSTANCE;
    }

    public void queryResults(String prodId, int page, final DataCallback callback) {
        //发起网络请求，请求评论数据
        Map<String, Object> param = new NetParam()
                .put("page", page)
                .put("prodId", prodId)
                .build();
        if (callback != null) callback.onStart();
        NetApi.NI().netProductReview(param).enqueue(new STCallback<EvaWrap>(EvaWrap.class) {
            @Override
            public void onSuccess(int status, EvaWrap bean, String msg) {
                List<Eva> evas = bean.getProducts();
                if (!StrUtil.isEmpty(evas)) {
                    if (callback != null) callback.onSuccess(status, evas, msg);
                } else {
                    if (callback != null) callback.onEmpty(status, msg);
                }
            }

            @Override
            public void onError(int status, String msg) {
                if (callback != null) callback.onError(status, msg);
            }
        });
    }

    public interface DataCallback {
        void onSuccess(int status, List<Eva> evas, String msg);

        void onEmpty(int status, String msg);

        void onError(int status, String msg);

        void onStart();

    }
}
