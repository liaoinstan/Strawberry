package com.ins.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by liaoinstan
 * 视频资源处理工具
 */
public class VideoUtil {

    /**
     * 从一个视频文件的路径获取该视频的第一帧图像Bitmap
     */
    public static Bitmap createVideoThumbnail(String filePath) {
        Class<?> clazz = null;
        Object instance = null;
        try {
            clazz = Class.forName("android.media.MediaMetadataRetriever");
            instance = clazz.newInstance();

            Method method = clazz.getMethod("setDataSource", String.class);
            method.invoke(instance, filePath);

            if (Build.VERSION.SDK_INT <= 9) {
                return (Bitmap) clazz.getMethod("captureFrame").invoke(instance);
            } else {
                byte[] data = (byte[]) clazz.getMethod("getEmbeddedPicture").invoke(instance);
                if (data != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    if (bitmap != null) return bitmap;
                }
                return (Bitmap) clazz.getMethod("getFrameAtTime").invoke(instance);
            }
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } catch (InstantiationException e) {
            Log.e("TAG", "createVideoThumbnail", e);
        } catch (InvocationTargetException e) {
            Log.e("TAG", "createVideoThumbnail", e);
        } catch (ClassNotFoundException e) {
            Log.e("TAG", "createVideoThumbnail", e);
        } catch (NoSuchMethodException e) {
            Log.e("TAG", "createVideoThumbnail", e);
        } catch (IllegalAccessException e) {
            Log.e("TAG", "createVideoThumbnail", e);
        } finally {
            try {
                if (instance != null) {
                    clazz.getMethod("release").invoke(instance);
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }
}
