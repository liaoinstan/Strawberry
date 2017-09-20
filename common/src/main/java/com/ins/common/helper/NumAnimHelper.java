package com.ins.common.helper;

import android.widget.TextView;

import com.ins.common.utils.NumUtil;

import java.util.LinkedList;
import java.util.Random;

/**
 * 数字滚动动画工具类，类似支付宝进入余额页面，数字从0迅速滚动到当前金额
 * 调用：startAnim(TextView textV, float num)即可
 */
public class NumAnimHelper {

    //每秒刷新多少次
    private static final int COUNTPERS = 100;

    public static void startAnim(TextView textV, float num) {
        startAnim(textV, num, 500);
    }

    public static void startAnim(TextView textV, float num, long time) {
        if (num == 0) {
            textV.setText(NumUtil.NumberFormat(num, 2));
            return;
        }
        Float[] nums = splitnum(num, (int) ((time / 1000f) * COUNTPERS));
        Counter counter = new Counter(textV, nums, time);
        textV.removeCallbacks(counter);
        textV.post(counter);
    }

    private static Float[] splitnum(float num, int count) {
        Random random = new Random();
        float numtemp = num;
        float sum = 0;
        LinkedList<Float> nums = new LinkedList<Float>();
        nums.add(0f);
        while (true) {
            float nextFloat = NumUtil.NumberFormatFloat((random.nextFloat() * num * 2f) / (float) count, 2);
            System.out.println("next:" + nextFloat);
            if (numtemp - nextFloat >= 0) {
                sum = NumUtil.NumberFormatFloat(sum + nextFloat, 2);
                nums.add(sum);
                numtemp -= nextFloat;
            } else {
                nums.add(num);
                return nums.toArray(new Float[0]);
            }
        }
    }

    static class Counter implements Runnable {

        private final TextView view;
        private Float[] nums;
        private long pertime;

        private int i = 0;

        Counter(TextView view, Float[] nums, long time) {
            this.view = view;
            this.nums = nums;
            this.pertime = time / nums.length;
        }

        @Override
        public void run() {
            if (i > nums.length - 1) {
                view.removeCallbacks(Counter.this);
                return;
            }
            view.setText(NumUtil.NumberFormat(nums[i++], 2));
            view.removeCallbacks(Counter.this);
            view.postDelayed(Counter.this, pertime);
        }
    }
}
