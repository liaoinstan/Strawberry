package com.ins.version.utils;

import com.ins.version.bean.UpdateInfo;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 把json转实体，由于只有一个请求，手动操作，不导入gson等三方库了
 */
public class JsonUtil {

    public static UpdateInfo toUpdateInfo(InputStream is) {
        try {
            if (is == null) {
                return null;
            }
            String byteData = new String(readStream(is));
            is.close();
            JSONObject jsonObject = new JSONObject(byteData);
            UpdateInfo updateInfo = new UpdateInfo();
            if (jsonObject.has("apkUrl")) updateInfo.setApkUrl(jsonObject.getString("apkUrl"));
            if (jsonObject.has("appName")) updateInfo.setAppName(jsonObject.getString("appName"));
            if (jsonObject.has("versionCode"))
                updateInfo.setVersionCode(jsonObject.getString("versionCode"));
            if (jsonObject.has("versionName"))
                updateInfo.setVersionName(jsonObject.getString("versionName"));
            if (jsonObject.has("changeLog"))
                updateInfo.setChangeLog(URLUtils.replaceBlank(jsonObject.getString("changeLog")).replaceAll("<br\\s*/?>", "\n"));
            if (jsonObject.has("updateTips"))
                updateInfo.setUpdateTips(jsonObject.getString("updateTips"));
            if (jsonObject.has("status")) updateInfo.setStatus(jsonObject.getInt("status"));
            if (jsonObject.has("created_at"))
                updateInfo.setCreated_at(jsonObject.getString("created_at"));
            if (jsonObject.has("size")) updateInfo.setSize(jsonObject.getString("size"));
            if (jsonObject.has("isForce")) updateInfo.setIsForce(jsonObject.getInt("isForce"));
            if (jsonObject.has("isAutoInstall"))
                updateInfo.setIsAutoInstall(jsonObject.getInt("isAutoInstall"));
            return updateInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] readStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] array = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(array)) != -1) {
            outputStream.write(array, 0, len);
        }
        inputStream.close();
        outputStream.close();
        return outputStream.toByteArray();
    }

}
