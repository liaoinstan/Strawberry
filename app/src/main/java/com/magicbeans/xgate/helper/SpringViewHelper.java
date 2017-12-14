package com.magicbeans.xgate.helper;

import android.os.Handler;

import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;

/**
 * Created by Administrator on 2017/12/14.
 */

public class SpringViewHelper {

    //快速初始化SpringView并添加监听，只用于测试版
    public static void initSpringViewForTest(final SpringView springView) {
        if (springView == null) return;
        springView.setHeader(new AliHeader(springView.getContext(), false));
        springView.setFooter(new AliFooter(springView.getContext(), false));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
    }
}
