package com.ins.common.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ins.common.R;
import com.ins.common.entity.Image;
import com.ins.common.utils.DensityUtil;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.List;

/**
 * Created by liaoinstan on 2017/10/9
 */
public class BannerView2 extends FrameLayout {

    private Context context;
    private UltraViewPager mViewPager;
    private BannerAdapter mBannerAdapter;
    private List<Image> images;

    public BannerView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        LayoutInflater.from(context).inflate(R.layout.banner2, this, true);
        super.onFinishInflate();
        initView();
    }

    private void initView() {
        mViewPager = (UltraViewPager) findViewById(R.id.viewpager);
        mViewPager.getViewPager().setPageMargin(0);
        mViewPager.setInfiniteLoop(true);
        mViewPager.setAutoScroll(3000);
        mViewPager.initIndicator();
        mViewPager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.parseColor("#ffffff"))
                .setNormalColor(Color.parseColor("#33ffffff"))
                .setMargin(0,0, DensityUtil.dp2px(5), DensityUtil.dp2px(5))
                .setRadius(DensityUtil.dp2px(3));
        mViewPager.getIndicator().setGravity(Gravity.RIGHT | Gravity.BOTTOM);
        mViewPager.getIndicator().build();
        mBannerAdapter = new BannerAdapter();
        mViewPager.setAdapter(mBannerAdapter);
    }

    private void initCtrl() {
        mBannerAdapter = new BannerAdapter();
        mViewPager.setAdapter(mBannerAdapter);
    }

    private class BannerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images != null ? images.size() : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View root = LayoutInflater.from(container.getContext()).inflate(R.layout.banner_item, null);
            ImageView imageView = (ImageView) root.findViewById(R.id.banner_img);

            //绑定网络图片
            if (onLoadImgListener != null) {
                onLoadImgListener.onloadImg(imageView, images.get(position).getImg(), R.drawable.default_bk_img);
            }

            //点击事件
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onBannerClickListener != null) {
                        onBannerClickListener.onBannerClick(position);
                    }
                }
            });

            //添加到容器
            container.addView(root);
            return root;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    //#######################接口
    private OnBannerClickListener onBannerClickListener;

    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
    }

    public interface OnBannerClickListener {
        void onBannerClick(int position);
    }

    private OnLoadImgListener onLoadImgListener;

    public void setOnLoadImgListener(OnLoadImgListener onLoadImgListener) {
        this.onLoadImgListener = onLoadImgListener;
    }

    public interface OnLoadImgListener {
        void onloadImg(ImageView imageView, String imgurl, int defaultSrc);
    }

    //#######################对外方法
    public void setDatas(List<Image> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        initCtrl();
    }

    public void showTitle(boolean needShow) {
    }
}
