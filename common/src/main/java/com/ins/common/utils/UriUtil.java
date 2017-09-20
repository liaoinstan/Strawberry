package com.ins.common.utils;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by liaoinstan on 2017/3/8.
 * Uri工具
 */

public class UriUtil {

    /**
     * 7.0之后废弃了file://协议，使用content://URI ，这里根据系统版本选择不同协议
     * @param context
     * @param path
     * @return
     */
    public static Uri getUriFromFile(Context context, String path){
        if (android.os.Build.VERSION.SDK_INT < 24) {
            //Android 7.0以下，直接获取启调
            return Uri.fromFile(new File(path));
        } else {
            //适配到android 7.0
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, new File(path).getAbsolutePath());
            Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            return uri;
        }
    }
}
