package com.ins.common.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.ins.common.R;
import com.ins.common.common.CircularBitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by liaoinstan on 2016/10/27.
 * 为Glide定制，将调用统一化，主要用于封装一些常用方法方便调用，比如圆形头像、默认图等
 */

public class GlideUtil {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private static String imgBaseUrl;

    private GlideUtil() {
        throw new UnsupportedOperationException();
    }

    public static void init(Application application) {
        context = application.getApplicationContext();
    }

    public static void setImgBaseUrl(String imgBaseUrl) {
        GlideUtil.imgBaseUrl = imgBaseUrl;
    }

    public static String getRealImgPath(String path) {
        if (!TextUtils.isEmpty(path) && !TextUtils.isEmpty(imgBaseUrl) && path.startsWith("upload")) {
            return imgBaseUrl + "images/" + path;
        } else {
            return path;
        }
    }

    ///////////////// 加载方法 ///////////////

    //加载网络图，并设置占位图
    public static void loadCircleImg(ImageView imageView, int errorSrc, String url) {
        url = getRealImgPath(url);
        RequestOptions myOptions = new RequestOptions()
                .circleCrop()
                .placeholder(errorSrc)
                .error(errorSrc);
        Glide.with(context)
                .load(url)
                .apply(myOptions)
                .transition(new DrawableTransitionOptions().crossFade(200))
                .into(imageView);
    }

    public static void loadImg(ImageView imageView, int errorSrc, String url) {
        url = getRealImgPath(url);
        RequestOptions myOptions = new RequestOptions()
                .placeholder(errorSrc)
                .error(errorSrc);
        Glide.with(context)
                .load(url)
                .apply(myOptions)
                .transition(new DrawableTransitionOptions().crossFade(200))
                .into(imageView);
    }

    public static void loadCircleImg(ImageView imageView, int src) {
        RequestOptions myOptions = new RequestOptions()
                .circleCrop();
        Glide.with(context)
                .load(src)
                .apply(myOptions)
                .transition(new DrawableTransitionOptions().crossFade(200))
                .into(imageView);
    }

    public static void loadImg(ImageView imageView, int src) {
        Glide.with(context)
                .load(src)
                .transition(new DrawableTransitionOptions().crossFade(200))
                .into(imageView);
    }

    //加载一张图进行高斯模糊处理
    public static void loadBlurImg(Context context, ImageView imageView, String url) {
        url = getRealImgPath(url);
        RequestOptions myOptions = new RequestOptions()
                .transform(new BlurTransformation(25));
        Glide.with(context)
                .load(url)
                .apply(myOptions)
                .transition(new DrawableTransitionOptions().crossFade(200))
                .into(imageView);
    }

    //#########################################
    //########## 以下方法仅用于测试
    //#########################################
    private static List<String> urls = new ArrayList<String>() {{
//        add("http://tupian.qqjay.com/tou3/2016/0725/037697b0e2cbb48ccb5a8c4d1ef0f65c.jpg");
//        add("http://img05.tooopen.com/images/20150529/tooopen_sy_127162097995.jpg");
//        add("http://rescdn.qqmail.com/dyimg/20140630/7D38689E0A7A.jpg");
//        add("http://img15.3lian.com/2016/h1/143/2.jpg");
//        add("http://img3.duitang.com/uploads/item/201512/10/20151210101534_4jRcJ.thumb.700_0.jpeg");
//        add("http://img4.duitang.com/uploads/item/201402/01/20140201220806_JKXsh.thumb.600_0.jpeg");
        add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505980220888&di=6ccd47917cb41bb052d31fa2dc6cdee5&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F017ee1554b06b400000115a88bac5e.jpg");
        add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505980253336&di=8446ab30bdf49880f4b93d82ab8a26fb&imgtype=0&src=http%3A%2F%2Fs10.sinaimg.cn%2Fmiddle%2F7155a039g982394372ad9%26690");
        add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505980261959&di=e1088d6d1de8774bcd4530b14ebfc008&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F17%2F55%2F34%2F04n58PICCqJ_1024.jpg");
        add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505980273506&di=8bfc3697bced4f0bedc022ca8df57ea4&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F66%2F89%2F43y58PICsSt_1024.jpg");
        add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505980301640&di=c0a4e76ec5cca898b19016e315445e99&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D86127594ac1ea8d39e2f7c47ff635a3b%2Ffc1f4134970a304e90f762d5dbc8a786c8175ccc.jpg");
        add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506575040&di=e4021207b94cde244f7a80cfe1d48356&imgtype=jpg&er=1&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F16%2F64%2F75%2F27X58PICRYf_1024.jpg");
        add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506575068&di=4993260dac5167ffbd9417ce3f1e760a&imgtype=jpg&er=1&src=http%3A%2F%2Fpic24.nipic.com%2F20121018%2F11167740_091226535158_2.jpg");
        add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506575119&di=b2c310a4470b8bdc82c312873e4c57f0&imgtype=jpg&er=1&src=http%3A%2F%2Fsc.jb51.net%2Fuploads%2Fallimg%2F141222%2F11-14122216021J60.jpg");
    }};

    public static void loadCircleImgTest(ImageView imageView) {
        if (imageView == null) return;
        loadCircleImg(imageView, R.drawable.default_bk_img, urls.get(new Random(imageView.hashCode()).nextInt(urls.size() - 1)));
    }

    public static void loadImgTest(ImageView imageView) {
        if (imageView == null) return;
        loadImg(imageView, R.drawable.default_bk_img, urls.get(new Random(imageView.hashCode()).nextInt(urls.size() - 1)));
    }
}
