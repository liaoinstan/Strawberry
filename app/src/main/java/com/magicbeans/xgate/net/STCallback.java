package com.magicbeans.xgate.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ins.common.utils.L;
import com.ins.common.utils.UnicodeUtil;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.common.AppData;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/4/5.
 * 统一处理请求回调，提供全局功能控制，
 * 例如：
 * 返回异地登录后，强制当前用户下线
 * 账户被冻结，锁定APP
 */

public abstract class STCallback<T> implements Callback<ResponseBody> {

    private Gson gson = new Gson();
    private Type type;

    public STCallback(Type type) {
        this.type = type;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        try {
            ResponseBody body = response.body();
            if (body == null) {
                onError(0, "服務器异常：" + response.code());
                return;
            }
            String data = body.string();
            L.e(data);
            T t;
            if (data != null && !data.equals("")) {
                t = gson.fromJson(data, type);
            } else {
                t = null;
            }
            onSuccess(200, t, "请求成功");
        } catch (Exception e) {
            onError(0, "未知错误");
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        onError(0, "请求失败");
    }

    abstract public void onSuccess(int status, T t, String msg);

    abstract public void onError(int status, String msg);
}
