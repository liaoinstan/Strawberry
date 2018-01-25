package com.magicbeans.xgate.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.bigkoo.pickerview.TimePickerView;
import com.magicbeans.xgate.R;

import java.util.Calendar;

public class DayPicker {
    private TimePickerView timePickerView;
    private static Calendar startCalendar;

    static {
        startCalendar = Calendar.getInstance();
        startCalendar.setTimeInMillis(0);
    }

    public DayPicker(Context context, TimePickerView.OnTimeSelectListener listener) {
        timePickerView = new TimePickerView.Builder(context, listener).
                setBgColor(Color.WHITE)
                .setCancelText("取消")
                .setSubmitText("确定")
                .setCancelColor(ContextCompat.getColor(context, R.color.com_text_dark_light))
                .setSubmitColor(ContextCompat.getColor(context, R.color.com_text_dark_blank))
                .setSubCalSize(15)
                .setRangDate(startCalendar, Calendar.getInstance())
                .setDate(Calendar.getInstance())
                .setType(new boolean[]{true, true, true, false, false, false}).build();

    }

    public void show() {
        timePickerView.show();
    }

    public void dismiss() {
        timePickerView.dismiss();
    }

    /**
     * 设置所选中的默认日期
     */
    public void setDate(long time) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(time);
        timePickerView.setDate(instance);
    }
}
