package com.magicbeans.xgate.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.magicbeans.xgate.R;
import com.magicbeans.xgate.ui.base.BaseAppCompatActivity;

public class SuggestActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private TextView btn_right;
    private EditText edit_suggest;


    public static void start(Context context) {
        Intent intent = new Intent(context, SuggestActivity.class);
        context.startActivity(intent);
//        if (AppData.App.getUser() != null) {
//            Intent intent = new Intent(context, SuggestActivity.class);
//            context.startActivity(intent);
//        } else {
//            LoginActivity.start(context);
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);
        setToolbar();
        initBase();
        initView();
        initCtrl();
        initData();
    }

    private void initBase() {
    }

    private void initView() {
//        edit_suggest = (EditText) findViewById(R.id.edit_suggest);
//        btn_right.setOnClickListener(this);
//        btn_right.setEnabled(false);
    }

    private void initCtrl() {
//        edit_suggest.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (AppVali.content(s.toString()) == null) {
//                    btn_right.setEnabled(true);
//                } else {
//                    btn_right.setEnabled(false);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.btn_right:
//                String content = edit_suggest.getText().toString();
//                break;
        }
    }
}
