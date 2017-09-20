package com.ins.common.helper;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/11/22.
 * 获取验证码倒计时工具，设置一个textview 调用start就可以开始倒计时（默认60s）
 * 根据需求修改这个helper，它提供了最基础的功能
 *
 * update 2017.9.1
 * 使用{@link android.os.CountDownTimer}来实现更方便，可以考虑重构一下
 */

public class ValiHelper {

    private TextView btn_go_vali;

    public ValiHelper(TextView textView) {
        this.btn_go_vali = textView;
    }

    ////////////////////////////
    //保持验证码
    public String valicode;
    //保持已验证手机号
    public String phone;

    ////////////////////////////
    //获取验证码计时

    private final static int MAXTIME = 60;
    private int time = MAXTIME;
    private final static int WHAT_TIME = 0;

    private void sendTimeMessage() {
        if (mHandler != null) {
            mHandler.removeMessages(WHAT_TIME);
            mHandler.sendEmptyMessageDelayed(WHAT_TIME, 1000);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_TIME) {
                if (time > 0) {
                    btn_go_vali.setText("" + time);
                    time--;
                    sendTimeMessage();
                } else {
                    btn_go_vali.setEnabled(true);
                    btn_go_vali.setText("获取验证码");
                    time = MAXTIME;
                }
            }
        }
    };

    public void start(){
        //开始计时
        time = MAXTIME;
        sendTimeMessage();
        btn_go_vali.setText(time + "");
        btn_go_vali.setEnabled(false);
    }

    public void reset(){
    }
}
