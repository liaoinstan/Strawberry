package com.ins.common.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/8 0008.
 * getCacheDir()方法用于获取/data/data/<application package>/cache目录
 * getFilesDir()方法用于获取/data/data/<application package>/files目录
 * 应用程序在运行的过程中如果需要向手机上保存数据，一般是把数据保存在SDcard中的。
 * 大部分应用是直接在SDCard的根目录下创建一个文件夹，然后把数据保存在该文件夹中。
 * 这样当该应用被卸载后，这些数据还保留在SDCard中，留下了垃圾数据。
 * 上面二个目录分别对应 设置->应用->应用详情里面的”清除数据“与”清除缓存“选项
 * <p>
 * 注意：如果你保存了一些文件在自己设置的路径下，也要注意清除掉
 * getAppCacheSize  clearAPPCache  会获取和清除 数据和缓存包括自定义目录下的文件（需要自己配置好自定义目录，该项目目录:FileUtil.getAppStoragePath()）
 * getTotalCacheSize  clearTotalCache  会获取和清除 数据和缓存 （适合所有需求，但是还是要注意清除自己生成的文件，不要留下垃圾）
 */
public class ClearCacheUtil {

    /**
     * 获取APP缓存大小总和
     */
    public static String getAppCacheSize(Context context){
        return getFormatSize(getAppCacheSizeValue(context));
    }

    /**
     * 获取APP缓存大小总和(返回未格式化的原始大小：long)
     */
    public static long getAppCacheSizeValue(Context context){
        List<File> folders = new ArrayList<>();
        folders.add(new File(FileUtil.getAppStoragePath()));

        long cacheSize = 0;
        cacheSize += getTotalCacheSizeValue(context);
        cacheSize += getFoldersSize(folders.toArray(new File[]{}));
        return cacheSize;
    }

    /**
     * 清除APP缓存
     */
    public static void clearAPPCache(Context context) {
        List<File> folders = new ArrayList<>();
        folders.add(new File(FileUtil.getAppStoragePath()));
        //清除数据和缓存
        clearTotalCache(context);
        //清除自定义产生的文件
        deleteFolders(folders.toArray(new File[]{}));
    }


    /**
     * 获取数据和缓存大小总和
     */
    public static String getTotalCacheSize(Context context){
        return getFormatSize(getTotalCacheSizeValue(context));
    }

    /**
     * 获取数据和缓存大小总和(返回未格式化的原始大小：long)
     */
    public static long getTotalCacheSizeValue(Context context){
        long cacheSize = 0;
        cacheSize += getCacheSizeValue(context);
        cacheSize += getExternalCacheSizeValue(context);
        return cacheSize;
    }

    /**
     * 清除数据和缓存
     */
    public static void clearTotalCache(Context context) {
        //清除数据
        clearCache(context);
        //清除缓存
        clearExternalCache(context);
    }

    /**
     * 获取缓存大小
     */
    public static String getExternalCacheSize(Context context){
        return getFormatSize(getExternalCacheSizeValue(context));
    }

    /**
     * 获取缓存大小(返回未格式化的原始大小：long)
     */
    public static long getExternalCacheSizeValue(Context context){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return getFolderSize(context.getExternalCacheDir());
        } else {
            return 0;
        }
    }

    /**
     * 获取数据大小
     */
    public static String getCacheSize(Context context){
        return getFormatSize(getCacheSizeValue(context));
    }

    /**
     * 获取数据大小(返回未格式化的原始大小：long)
     */
    public static long getCacheSizeValue(Context context){
        return getFolderSize(context.getCacheDir());
    }

    /**
     * 清除缓存
     */
    public static void clearExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    /**
     * 清除数据
     */
    public static void clearCache(Context context) {
        deleteDir(context.getCacheDir());
    }

    ///////////////////////////////////////
    ////////内部方法
    ///////////////////////////////////////

    //下面方法的批处理
    private static void deleteFolders(File[] folders) {
        for (File folder : folders) {
            if (folder != null && folder.exists()) {
                deleteDir(folder);
            }
        }
    }

    //删除一个文件夹和下面所有文件
    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    //下面方法的批处理
    private static long getFoldersSize(File[] folders) {
        long total = 0;
        for (File folder : folders) {
            if (folder != null && folder.exists()) {
                total += getFolderSize(folder);
            }
        }
        return total;
    }

    // 遍历一个文件夹下所有文件大小总和
    private static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    private static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }


}
