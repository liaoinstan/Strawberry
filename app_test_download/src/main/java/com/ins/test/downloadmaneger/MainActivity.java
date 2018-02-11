package com.ins.test.downloadmaneger;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.ins.version.VersionHelper;
import com.ins.version.bean.UpdateInfo;
import com.ins.version.listener.OnUpdateListener;

public class MainActivity extends AppCompatActivity {

    private VersionHelper versionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onClick(View view) {
        versionHelper = VersionHelper.with(this)
                .checkUrl("http://7xnfyf.com1.z0.glb.clouddn.com/version.json")
                .check(new OnUpdateListener() {
                    @Override
                    public void onStartCheck() {
                        Log.e("MainActivity", "onStartCheck");
                    }

                    @Override
                    public void onFinishCheck(UpdateInfo info) {
                        Log.e("MainActivity", "onFinishCheck");
                    }

                    @Override
                    public void onStartDownload() {
                        Log.e("MainActivity", "onStartDownload");
                    }

                    @Override
                    public void onDownloading(int progress) {
                        Log.e("MainActivity", "onDownloading");
                    }

                    @Override
                    public void onFinshDownload(Uri uri) {
                        Log.e("MainActivity", "onFinshDownload");
                    }

                    @Override
                    public void onInstallApk() {
                        Log.e("MainActivity", "onInstallApk");
                    }
                });
    }

    public void onClick2(View view) {
        versionHelper = VersionHelper.with(this)
                .checkUrl("http://7xnfyf.com1.z0.glb.clouddn.com/version.json")
                .ignoreEnable(false)
                .check();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (versionHelper != null) {
            versionHelper.onDestory();
        }
    }
}
