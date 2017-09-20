package com.ins.domain.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ins.domain.R;
import com.ins.domain.bean.Domain;
import com.ins.domain.launcher.DomainLauncher;
import com.ins.domain.ui.adapter.DomainListAdapter;
import com.ins.domain.ui.dialog.DomainPopup;

import java.util.ArrayList;

public class DomainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, View.OnClickListener {

    private EditText edit_domain;
    private CheckBox check_domain_vali;
    private CheckBox check_domain_toast;

    private ListView listView;
    private DomainListAdapter adapter;
    private ArrayList<Domain> results = new ArrayList<>();

    private DomainPopup popup;

    private String domain_res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.domain_activity);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        initBase();
        initView();
        initData();
        initCtrl();
    }

    private void initBase() {
        popup = new DomainPopup(this);
        popup.setOnDomainCallback(new DomainPopup.OnDomainCallback() {
            @Override
            public void onContent(Domain domain) {
                int index = results.indexOf(domain);
                if (index >= 0) {
                    //修改
                    results.set(index, domain);
                    adapter.notifyDataSetChanged();
                    saveDomains(results);
                } else {
                    //添加
                    results.add(domain);
                    adapter.notifyDataSetChanged();
                    saveDomains(results);
                }
            }

            @Override
            public void onDel(Domain domain) {
                //删除
                int index = results.indexOf(domain);
                if (index >= 0) {
                    results.remove(index);
                    adapter.notifyDataSetChanged();
                    saveDomains(results);
                }
            }
        });
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.list);
        edit_domain = (EditText) findViewById(R.id.edit_domain);
        check_domain_vali = (CheckBox) findViewById(R.id.check_domain_vali);
        check_domain_toast = (CheckBox) findViewById(R.id.check_domain_toast);

        findViewById(R.id.btn_go).setOnClickListener(this);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_advance).setOnClickListener(this);
    }

    private void initData() {
        ArrayList<Domain> domains = getDomains();
        if (domains != null && domains.size() > 0) {
            results.clear();
            results.addAll(domains);
        } else {
            results.clear();
            results.addAll(DomainLauncher.getInstance().getDomains());
        }
        domain_res = StorageHelper.with(this).getDomainRes();
    }

    private void initCtrl() {
        adapter = new DomainListAdapter(this, results);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        edit_domain.setText(getEditStr());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AdvanceActivity.RESULT_ADVANCE:
                if (resultCode == RESULT_OK) {
                    domain_res = data.getStringExtra("domain_res");
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Domain domain = results.get(position);
        edit_domain.setText(domain.getIp());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        popup.showPopupWindow(view, results.get(position));
        return true;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_go) {
            String domain = edit_domain.getText().toString();
            if (!TextUtils.isEmpty(domain)) {
                //保存本次编辑框的内容
                saveEditStr(domain);
                //回调domain变更接口
                DomainLauncher.getInstance().getSettingChangeCallback().onDomainChange(domain);
                //回调资源服务器domainRes变更接口
                DomainLauncher.getInstance().getSettingChangeCallback().onDomainResChange(!TextUtils.isEmpty(domain_res) ? domain_res : domain);
                //启动APPLaunche页面
                Intent intent = new Intent(this, DomainLauncher.getInstance().getLauncherActivity());
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "请输入域名或者IP", Toast.LENGTH_SHORT).show();
            }
        } else if (i == R.id.btn_add) {
            popup.showPopupWindow(v);
        } else if (i == R.id.btn_advance) {
            AdvanceActivity.startForResult(this);
        }
    }

    //////////////// 存储 获取 方法 ////////////////

    private void saveDomains(ArrayList<Domain> domains) {
        StorageHelper.with(this).putDomains(domains);
    }

    private ArrayList<Domain> getDomains() {
        return StorageHelper.with(this).getDomains();
    }

    private void saveEditStr(String editStr) {
        StorageHelper.with(this).putEditStr(editStr);
    }

    public String getEditStr() {
        return StorageHelper.with(this).getEditStr();
    }
}

