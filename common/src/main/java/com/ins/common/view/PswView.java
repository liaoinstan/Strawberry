package com.ins.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ins.common.R;

/**
 * Created by Administrator on 2016/11/1.
 */

public class PswView extends LinearLayout {

    private ViewGroup root;
    private Context context;
    private LayoutInflater inflater;

    private TextView[] tvList;      //用数组保存6个TextView，为什么用数组？
    private ImageView[] imgList;
    private int currentIndex = 0;    //用于记录当前输入密码格位置

    public PswView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PswView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public PswView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        root = (ViewGroup) inflater.inflate(R.layout.pswview, this, true);
        initBase();
        initView();
        initCtrl();
    }

    private void initBase() {
    }

    private void initView() {

        tvList = new TextView[6];
        imgList = new ImageView[6];

        tvList[0] = (TextView) root.findViewById(R.id.tv_pass1);
        tvList[1] = (TextView) root.findViewById(R.id.tv_pass2);
        tvList[2] = (TextView) root.findViewById(R.id.tv_pass3);
        tvList[3] = (TextView) root.findViewById(R.id.tv_pass4);
        tvList[4] = (TextView) root.findViewById(R.id.tv_pass5);
        tvList[5] = (TextView) root.findViewById(R.id.tv_pass6);


        imgList[0] = (ImageView) root.findViewById(R.id.img_pass1);
        imgList[1] = (ImageView) root.findViewById(R.id.img_pass2);
        imgList[2] = (ImageView) root.findViewById(R.id.img_pass3);
        imgList[3] = (ImageView) root.findViewById(R.id.img_pass4);
        imgList[4] = (ImageView) root.findViewById(R.id.img_pass5);
        imgList[5] = (ImageView) root.findViewById(R.id.img_pass6);
    }

    private void initCtrl() {

    }

    public void addNum(int num) {
        if (currentIndex >= 0 && currentIndex <= tvList.length - 1) {
            tvList[currentIndex].setText("" + num);
            imgList[currentIndex].setVisibility(VISIBLE);
            currentIndex++;
        }
    }

    public void back() {
        if (currentIndex - 1 >= 0) {
            tvList[currentIndex - 1].setText("");
            imgList[currentIndex - 1].setVisibility(INVISIBLE);
            currentIndex--;
        }
    }

    public String getPsw() {
        String psw = "";
        for (TextView tv : tvList) {
            psw += tv.getText();
        }
        return psw;
    }
}
