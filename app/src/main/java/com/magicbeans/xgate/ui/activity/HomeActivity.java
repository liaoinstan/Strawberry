package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import com.ins.common.utils.L;
import com.ins.common.utils.PermissionsUtil;
import com.ins.common.utils.StatusBarUtil;
import com.ins.common.view.XRadioGroup;
import com.ins.version.VersionHelper;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.bean.EventBean;
import com.magicbeans.xgate.common.AppData;
import com.magicbeans.xgate.ui.adapter.PagerAdapterHome;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

public class HomeActivity extends BaseAppCompatActivity {

    //    private UpdateHelper updateHelper;
    private XRadioGroup group_tab;
    private ViewPager pager;
    private PagerAdapterHome pagerAdapter;
    private int[] tabsId = new int[]{R.id.tab_1, R.id.tab_2, R.id.tab_3, R.id.tab_4};

    public static void start(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCommonEvent(EventBean event) {
        if (event.getEvent() == EventBean.EVENT_LANGUAGE_CHANGE) {
            recreate();
        } else if (event.getEvent() == EventBean.EVENT_JUMP_BRANDHOT) {
            //跳转到热门品牌页面
            group_tab.check(tabsId[1]);
        } else if (event.getEvent() == EventBean.EVENT_JUMP_HOME) {
            //跳转到home页面
            group_tab.check(tabsId[0]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setNeedDoubleClickExit(true);
        registEventBus();

        //权限检查
        PermissionsUtil.checkAndRequestPermissions(this);
        //版本更新检查
        //检查更新
//        updateHelper = new UpdateHelper.Builder(this).checkUrl(AppData.Url.version).isHintNewVersion(false).build();
//        updateHelper.check();
        VersionHelper.with(this).isShowToast(false).checkUrl(AppData.Url.version).check();

        StatusBarUtil.setBarFollow(this);
        StatusBarUtil.setStatusBarColor(this, R.color.colorPrimaryDark);
        StatusBarUtil.setTextLight(this);

        initBase();
        initView();
        initCtrl();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (updateHelper != null) updateHelper.onDestory();
    }

    private void initBase() {
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.pager_home);
        group_tab = (XRadioGroup) findViewById(R.id.group_tab);
    }

    private void initCtrl() {
        pagerAdapter = new PagerAdapterHome(getSupportFragmentManager());
        pager.setOffscreenPageLimit(4);
        pager.setAdapter(pagerAdapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                group_tab.check(tabsId[position]);
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        EventBus.getDefault().post(new EventBean(EventBean.EVENT_IN_SHOPCART));
                        break;
                    case 3:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        group_tab.setOnCheckedChangeListener(new XRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(XRadioGroup group, @IdRes int checkedId) {
                for (int i = 0; i < tabsId.length; i++) {
                    if (tabsId[i] == checkedId) {
                        pager.setCurrentItem(i, true);
                    }
                }
            }
        });
    }

    private void initData() {
    }
}
