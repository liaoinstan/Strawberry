package com.ins.common.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liaoinstan on 2015/12/14.
 * 文件操作工具类
 * 规范APP存储目录，使用这些方法来创建目录，便于统一管理
 */
public class FileUtil {

    private static final String AppFolderName = "appFolder";
    private static final String CropFolderName = "photoCrop";
    private static final String VoiceFolderName = "Voice";
    private static final String VideoFolderName = "Video";

    /**
     * 获取不重复的图片文件名
     */
    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    /**
     * 获取手机外部存储路径
     */
    public static String getExternalPath() {
        File sdDir = Environment.getExternalStorageDirectory();// 获取外存目录
        return sdDir.toString();
    }

    /**
     * 获取应用储路径
     */
    public static String getAppStoragePath() {
        return getExternalPath() + File.separator + AppFolderName;
    }

    /**
     * 获取存储照片的路径
     */
    public static String getPhotoFolder() {
        String dir = getAppStoragePath() + File.separator + CropFolderName;
        return createDir(dir);
    }

    /**
     * 获取存储音频的路径
     */
    public static String getVoiceFolder() {
        String dir = getAppStoragePath() + File.separator + VoiceFolderName;
        return createDir(dir);
    }

    /**
     * 获取存储视频的路径
     */
    public static String getVideoFolder() {
        String dir = getAppStoragePath() + File.separator + VideoFolderName;
        return createDir(dir);
    }

    /**
     * 获取一张图片的完整路径，包括文件名
     */
    public static String getPhotoFullPath() {
        return getPhotoFolder() + File.separator + getPhotoFileName();
    }

    /**
     * 检查是否存在path目录，不存在则创建
     */
    private static String createDir(String path) {
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return fileDir.getPath();
    }

    /**
     * 这个内部类封装了从URI获取文件真实路径的方法，只需调用这一个方法：FileUtil.PathUtil.getPath()
     */
    public static class PathUtil {

        /**
         * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
         */
        @SuppressLint("NewApi")
        public static String getPath(final Context context, final Uri uri) {

            final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }

                    // TODO handle non-primary volumes
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }

            return null;
        }

        /**
         * Get the value of the data column for this Uri. This is useful for
         * MediaStore Uris, and other file-based ContentProviders.
         *
         * @param context       The context.
         * @param uri           The Uri to query.
         * @param selection     (Optional) Filter used in the query.
         * @param selectionArgs (Optional) Selection arguments used in the query.
         * @return The value of the _data column, which is typically a file path.
         */
        private static String getDataColumn(Context context, Uri uri, String selection,
                                            String[] selectionArgs) {

            Cursor cursor = null;
            final String column = "_data";
            final String[] projection = {column};

            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                        null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int column_index = cursor.getColumnIndexOrThrow(column);
                    return cursor.getString(column_index);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
            return null;
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is ExternalStorageProvider.
         */
        private static boolean isExternalStorageDocument(Uri uri) {
            return "com.android.externalstorage.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         */
        private static boolean isDownloadsDocument(Uri uri) {
            return "com.android.providers.downloads.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         */
        private static boolean isMediaDocument(Uri uri) {
            return "com.android.providers.media.documents".equals(uri.getAuthority());
        }
    }

}
