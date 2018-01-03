package com.magicbeans.xgate.app;

import android.app.Application;

import com.ins.common.utils.App;
import com.ins.common.utils.GlideUtil;
import com.ins.common.utils.L;
import com.ins.common.utils.SharedPrefUtilV2;
import com.ins.common.utils.ToastUtil;
import com.ins.domain.launcher.DomainLauncher;
import com.magicbeans.xgate.BuildConfig;
import com.magicbeans.xgate.R;
import com.magicbeans.xgate.common.AppData;
import com.magicbeans.xgate.data.db.AppDataBase;
import com.magicbeans.xgate.net.NetApi;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class StrawberryApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initSetting();
        initLauncher();
        initFonts();
        initJpush();
        initBugly();
    }

    private void initSetting() {
        SharedPrefUtilV2.init(this);
        App.saveApplication(this);
        L.setDEBUG(BuildConfig.DEBUG);
        ToastUtil.init(this);
        ToastUtil.setDebug(BuildConfig.DEBUG);
        GlideUtil.init(this);
        NetApi.setBaseUrl(BuildConfig.BASE_URL);
        //初始化数据库
        AppDataBase.createDataBase(this);
    }

    private void initJpush() {
//        JPushInterface.setDebugMode(BuildConfig.DEBUG);
//        JPushInterface.init(this);            // 初始化 JPush
//        JPushInterface.getRegistrationID(this); //在这里获取一次JpushID
    }

    private void initBugly() {
//        CrashReport.initCrashReport(getApplicationContext());
    }

    private void initLauncher() {
        DomainLauncher.getInstance().setSettingChangeCallback(new DomainLauncher.SettingChangeCallback() {
            @Override
            public void onDomainChange(String domain) {
                NetApi.setBaseUrl("https://" + domain + "/");
            }

            @Override
            public void onDomainResChange(String domainRes) {
//                AppData.Url.domainRes = "http://" + domainRes + "/";
                //GlideUtil.setImgBaseUrl(AppData.Url.domainRes);
            }
        });
        DomainLauncher.getInstance()
                .addDomain("192.168.31.126:8080", "(Web开发服务器：不可用)")
                .addDomain("192.168.31.205:8080", "(开发服务器1：不可用)")
                .addDomain("192.168.31.166", "(开发服务器2：不可用)")
                .addDomain("192.168.31.110:8080", "(内部测试服务器：不可用)")
                .addDomain("139.129.111.76:8110", "(远程测试服务器：不可用)")
//                .addDomain("tiger.magic-beans.cn", "(演示服务器：不可用)")
                .addDomain("demo2017.strawberrynet.com", "(演示服务器)")
                .addDomain("cn.strawberrynet.com", "(正式服务器)");
    }

    private void initFonts() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ltx.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
