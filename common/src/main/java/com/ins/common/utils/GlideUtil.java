package com.ins.common.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.ins.common.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jp.wasabeef.glide.transformations.BlurTransformation;

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

    //上面方法的重载
    public static void loadCircleImg(ImageView imageView, String url) {
        loadCircleImg(imageView, R.drawable.default_bk_img, url);
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

    //上面方法的重载
    public static void loadImg(ImageView imageView, String url) {
        loadImg(imageView, R.drawable.default_bk_img, url);
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
        add("https://a.cdnsbn.com/images/products/l/20857459814.jpg");
        add("https://a.cdnsbn.com/images/products/l/10005703602.jpg");
        add("https://a.cdnsbn.com/images/products/l/12834780402.jpg");
        add("https://b.cdnsbn.com/images/products/l/15403480402.jpg");
        add("https://b.cdnsbn.com/images/products/l/05766096301.jpg");
        add("https://c.cdnsbn.com/images/products/l/04010986801.jpg");
        add("https://c.cdnsbn.com/images/products/l/16588798103.jpg");
        add("https://c.cdnsbn.com/images/products/l/11440582501.jpg");
        add("https://d.cdnsbn.com/images/products/l/07983430803.jpg");
        add("https://d.cdnsbn.com/images/products/l/09104380001.jpg");
        add("https://d.cdnsbn.com/images/products/l/16277591101.jpg");
    }};

    public static void loadCircleImgTest(ImageView imageView) {
        if (imageView == null) return;
        loadCircleImg(imageView, R.drawable.default_bk_img, urls.get(new Random(imageView.hashCode()).nextInt(urls.size() - 1)));
    }

    public static void loadImgTest(ImageView imageView) {
        if (imageView == null) return;
        loadImg(imageView, R.drawable.default_bk_img, urls.get(new Random(imageView.hashCode()).nextInt(urls.size() - 1)));
    }

    public static void loadImgTestByPosition(ImageView imageView, int position) {
        if (imageView == null) return;
        loadImg(imageView, R.drawable.default_bk_img, urls.get(new Random(position).nextInt(urls.size() - 1)));
    }
}
