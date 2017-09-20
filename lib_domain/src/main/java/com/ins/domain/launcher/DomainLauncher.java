package com.ins.domain.launcher;

import android.content.Context;
import android.content.Intent;

import com.ins.domain.bean.Domain;
import com.ins.domain.ui.activity.DomainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class DomainLauncher {

    private static DomainLauncher domainLauncher = new DomainLauncher();

    private Context context;
    private Class<?> launcherActivity;
    private SettingChangeCallback settingChangeCallback;
    private List<Domain> domains = new ArrayList<>();

    private DomainLauncher() {
    }

    public static DomainLauncher getInstance() {
        return domainLauncher;
    }

    public void startDomainActivity(Context context, Class<?> launcherActivity) {
        this.context = context;
        this.launcherActivity = launcherActivity;
        Intent intent = new Intent(context, DomainActivity.class);
        context.startActivity(intent);
    }

    ///////////////////////


    public Class<?> getLauncherActivity() {
        return launcherActivity;
    }

    public void setSettingChangeCallback(SettingChangeCallback settingChangeCallback) {
        this.settingChangeCallback = settingChangeCallback;
    }

    public SettingChangeCallback getSettingChangeCallback() {
        return settingChangeCallback;
    }

    public DomainLauncher addDomain(String ip, String note) {
        domains.add(new Domain(ip, note));
        return domainLauncher;
    }

    public List<Domain> getDomains() {
        return domains;
    }

    ///////////////////////

    public interface SettingChangeCallback {
        void onDomainChange(String domain);
        void onDomainResChange(String domainRes);
    }
}
