package com.magicbeans.xgate.net;

import com.ins.common.utils.L;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by liaointan on 2017/7/17.
 * 网络请求参数封装，便于统一管理，拓展后期可能增加的统一参数，加密设置等处理过程
 */

public class NetParam {
    private Map<String, Object> paramMap = new LinkedHashMap<>();

    public static NetParam newInstance() {
        return new NetParam();
    }

    public NetParam() {
        paramMap.put("region", "cn");
    }

    //添加参数
    public NetParam put(String key, Object value) {
        paramMap.put(key, value);
        return this;
    }

    public NetParam put(Map<String, Object> map) {
        paramMap.putAll(map);
        return this;
    }

    //构建参数集合
    public Map<String, Object> build() {
        return paramMap;
    }

    //构造一个上传文件的Bodypart
    public static MultipartBody.Part buildFileBodyPart(String partName,String path) {
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
        return body;
    }

    //构造一个RequestBody
    public static RequestBody buildRequestBody(String value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), value);
    }

    //测试方法，打印出参数
    private void pritParam() {
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            L.e(entry.getKey() + ":" + entry.getValue().toString());
        }
    }
}
