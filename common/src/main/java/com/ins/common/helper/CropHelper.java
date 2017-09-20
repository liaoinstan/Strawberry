package com.ins.common.helper;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.ins.common.utils.BitmapUtil;
import com.ins.common.utils.FileUtil;
import com.ins.common.utils.StrUtil;
import com.ins.common.utils.UriUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by liaoinstan on 2016/1/19.
 * Update 2017/6/19
 * 图片资源选择辅助类
 * <p>
 * 主要功能：
 * {@link #startCamera()} 启动相机拍照
 * {@link #startPhoto()} 启动相册选择照片
 * {@link #setNeedCrop(boolean)} 设置是否需要裁剪
 * {@link #setNeedPress(boolean)} 设置是否需要压缩（把照片压缩到100-200K左右，失真不明显，用于上传服务器）
 * <p>
 * 使用方法：
 * 1.new 一个该类实例
 * 2.在Activity的onActivityResult中调用本类的{@link #onActivityResult(int, int, Intent)}方法
 * 3.设置结果监听{@link CropInterface}
 * 4.调用{@link #startCamera(),#startPhoto()}启动相机或相册
 * <p>
 * 其他：
 * {@link #isASync}字段可以指定压缩策略使用同步或异步
 * 同步压缩使用sdk的BitmapFactory直接过滤像素点，异步压缩使用三方的Luban压缩进行
 * 该工具类主要对照片和相机拍摄进行图片处理，处理量小，同步异步基本无异
 * 经测试sdk的压缩方法速度更快，能对压缩过程进行人为干预，处理了三星手机特有的拍照横屏的问题
 * Luban压缩更接近于微信的压缩算法，但无法指定输出路径，各有利弊
 * 默认使用了系统的压缩方法进行同步压缩
 * 如果想要使用其他压缩策略推荐将CropHelper设置为不进行压缩needPress = flase，然后在外部回调中自行进行压缩
 */
public class CropHelper {
    private String path;
    private static final int PHOTO_CAMERA = 0xf311; // 拍照
    private static final int PHOTO_PHOTO = 0xf312;  // 结果
    private static final int PHOTO_CROP = 0xf313;   // 相册
    private boolean needCrop = false;       //默认不需要裁剪
    private boolean needPress = true;       //默认需要压缩
    private boolean isASync = false;        //默认同步压缩
    private boolean needForceLv = true;    //裁剪的时候是否固定裁剪框比例，为false则用户可以自由拖拽边框裁剪不同形状aspectlv值失效
    private int aspectX = 1;                 //裁剪的时候选择框宽
    private int aspectY = 1;                 //裁剪的时候选择框高
    private CropInterface cropInterface;
    private Object activityOrfragment;
    protected Context context;

    public CropHelper(Object activityOrfragment, CropInterface cropInterface) {
        this.cropInterface = cropInterface;
        this.activityOrfragment = activityOrfragment;

        if (activityOrfragment instanceof Activity) {
            context = ((Activity) activityOrfragment);
        } else if (activityOrfragment instanceof Fragment) {
            context = ((Fragment) activityOrfragment).getActivity();
        } else if (activityOrfragment instanceof android.support.v4.app.Fragment) {
            context = ((android.support.v4.app.Fragment) activityOrfragment).getActivity();
        }
    }

    /**
     * 在Activity的onActivityResult中调用这个方法
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    //拍照成功
                    if (needCrop) {
                        //如果需要裁剪，则启调系统裁剪，裁剪后的图片覆盖源文件
                        startCrop(UriUtil.getUriFromFile(context, path));
                    } else {
                        //不需要裁剪，压缩图片回调成功接口
                        callResultWithCompress(path, path);
                    }
                } else {
                    //拍照失败：清理残留文件，回调失败接口
                    clearFile(path);
                    cropInterface.cancel();
                }
                break;
            case PHOTO_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    //获取照片成功
                    if (data != null) {
                        Uri uri = data.getData();
                        if (needCrop) {
                            //如果需要裁剪，则启调系统裁剪
                            //产生一个新的文件路径作为输出路径（和拍照不同裁剪后的图片不能覆盖源文件）
                            path = FileUtil.getPhotoFullPath();
                            startCrop(uri, UriUtil.getUriFromFile(context, path));
                        } else {
                            //不需要裁剪，压缩图片回调成功接口
                            //获取真实的图片路径
                            String photoPath = FileUtil.PathUtil.getPath(context, uri);
                            callResultWithCompress(photoPath, FileUtil.getPhotoFullPath());
                        }
                    }
                } else {
                    //获取照片失败，回调失败接口
                    cropInterface.cancel();
                }
                break;
            case PHOTO_CROP:
                if (resultCode == Activity.RESULT_OK) {
                    //裁剪图片成功，压缩后回调成功接口
                    callResultWithCompress(path, path);
                } else {
                    //裁剪图片失败，清理残留文件，回调失败接口
                    clearFile(path);
                    cropInterface.cancel();
                }
                break;
        }
    }

    /**
     * 根据标志位，调用同步或异步压缩方法
     */
    private void callResultWithCompress(String fromPath, String toPath) {
        if (isASync) {
            callResultWithCompressAsync(fromPath);
        } else {
            callResultWithCompressSync(fromPath, toPath);
        }
    }

    /**
     * 同步压缩
     * 对图片进行压缩并回调成功接口，可以指定输出文件路径
     * 如果想让输出出牌覆盖原来图片在toPath填和fromPath一样的路径就可以了
     */
    private void callResultWithCompressSync(String fromPath, String toPath) {
        //如果设置了不需要压缩的标志，那么直接返回原路径
        if (!needPress) {
            cropInterface.cropResult(path);
        }
        if (StrUtil.isEmpty(fromPath)) {
            cropInterface.cropResult("");
        }
        Bitmap bitmap = null;
        try {
            int degree = BitmapUtil.getBitmapDegree(fromPath);          //获取旋转角度
            bitmap = BitmapUtil.revitionImageSize(fromPath);            //压缩并获取压缩后的位图
            bitmap = BitmapUtil.rotateBitmap(degree, bitmap);           //根据旋转角度进行旋转
            toPath = BitmapUtil.saveBitmap(bitmap, toPath);             //保存图片到指定路径
        } catch (IOException e) {
            e.printStackTrace();
        }
        cropInterface.cropResult(toPath);
    }

    /**
     * 异步压缩
     * 对图片进行压缩并回调成功接口
     * 该压缩策略使用的是Luban压缩，不能指定输出位置
     */
    private void callResultWithCompressAsync(String fromPath) {
        //如果设置了不需要压缩的标志，那么直接回调成功接口
        if (!needPress) {
            cropInterface.cropResult(path);
        } else {
            ImgCompressHelper.compress(context, fromPath, new ImgCompressHelper.Callback() {
                @Override
                public void onFinish(String path) {
                    cropInterface.cropResult(path);
                }
            });
        }
    }

    /**
     * 根据文件路径清理生成的文件
     */
    private void clearFile(String path) {
        try {
            File file = new File(path);
            if (file != null && file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用摄像头拍照
     */
    public void startCamera() {
        path = FileUtil.getPhotoFullPath();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, UriUtil.getUriFromFile(context, path));
        startActivityForResult(intent, PHOTO_CAMERA);
    }

    /**
     * 调用相册选择照片
     */
    public void startPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_PHOTO);
    }

    /**
     * 调用系统裁剪进行裁剪
     */
    public void startCrop(Uri uriFrom, Uri uriTo) {
        Intent intent = getCropIntent(uriFrom, uriTo);
        startActivityForResult(intent, PHOTO_CROP);
    }

    /**
     * 上面方法的重载，输入覆盖原图片{@link #startCrop(Uri, Uri)}
     */
    public void startCrop(Uri uri) {
        startCrop(uri, uri);
    }

    private void startActivityForResult(Intent intent, int requestCode) {
        if (activityOrfragment instanceof Activity) {
            ((Activity) activityOrfragment).startActivityForResult(intent, requestCode);
        } else if (activityOrfragment instanceof Fragment) {
            ((Fragment) activityOrfragment).startActivityForResult(intent, requestCode);
        } else if (activityOrfragment instanceof android.support.v4.app.Fragment) {
            ((android.support.v4.app.Fragment) activityOrfragment).startActivityForResult(intent, requestCode);
        }
    }

    private Intent getCropIntent(Uri uriFrom, Uri uriTo) {
        Intent intent;
        intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uriFrom, "image/*");
        //裁剪框比例（不设置则可自由拖拽编辑）
        if (needForceLv) {
            intent.putExtra("aspectX", aspectX);
            intent.putExtra("aspectY", aspectY);
        }
//        //图片输出大小（如果不设置那么按原比例）
//        if (needPress) {
//            intent.putExtra("outputX", 512);
//            intent.putExtra("outputY", 512*aspectlv);
//        }
        //其他参数
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriTo);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        return intent;
    }

    //############## get and set ###############

    public void setNeedCrop(boolean needCrop) {
        this.needCrop = needCrop;
    }

    public void setNeedPress(boolean needPress) {
        this.needPress = needPress;
    }

    public void setASync(boolean ASync) {
        isASync = ASync;
    }

    public boolean isNeedForceLv() {
        return needForceLv;
    }

    public void setNeedForceLv(boolean needForceLv) {
        this.needForceLv = needForceLv;
    }

    public int getAspectX() {
        return aspectX;
    }

    public void setAspectX(int aspectX) {
        this.aspectX = aspectX;
    }

    public int getAspectY() {
        return aspectY;
    }

    public void setAspectY(int aspectY) {
        this.aspectY = aspectY;
    }

    public void setCropInterface(CropInterface cropInterface) {
        this.cropInterface = cropInterface;
    }

    public boolean isNeedCrop() {
        return needCrop;
    }

    public boolean isNeedPress() {
        return needPress;
    }

    public boolean isASync() {
        return isASync;
    }

    //############## Interface ###############

    public interface CropInterface {
        void cropResult(String path);

        void cancel();
    }
}