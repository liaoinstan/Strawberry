package com.ins.common.view.virtualKeyboardView;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.ins.common.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 虚拟键盘
 */
public class VirtualKeyboardView extends RelativeLayout {

    Context context;

    //因为就6个输入框不会变了，用数组内存申请固定空间，比List省空间（自己认为）
    private GridView gridView;    //用GrideView布局键盘，其实并不是真正的键盘，只是模拟键盘的功能

    private ArrayList<Map<String, String>> valueList;    //有人可能有疑问，为何这里不用数组了？
    //因为要用Adapter中适配，用数组不能往adapter中填充

    private RelativeLayout layoutBack;

    public VirtualKeyboardView(Context context) {
        this(context, null);
    }

    public VirtualKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        View view = View.inflate(context, R.layout.keyboard_view, null);

        valueList = new ArrayList<>();

        layoutBack = (RelativeLayout) view.findViewById(R.id.layoutBack);

        gridView = (GridView) view.findViewById(R.id.gv_keybord);

        initValueList();

        setupView();

        addView(view);      //必须要，不然不显示控件

        enterAnim = AnimationUtils.loadAnimation(context, R.anim.dialog_bottom_in);
        exitAnim = AnimationUtils.loadAnimation(context, R.anim.dialog_bottom_out);
        getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
    }

    private Animation enterAnim;
    private Animation exitAnim;

    public void show() {
        if (getVisibility() != VISIBLE) {
            startAnimation(enterAnim);
            setVisibility(View.VISIBLE);
        }
    }

    public void hide() {
        if (getVisibility() == VISIBLE) {
            startAnimation(exitAnim);
            setVisibility(View.GONE);
        }
    }

    public RelativeLayout getLayoutBack() {
        return layoutBack;
    }

    public ArrayList<Map<String, String>> getValueList() {
        return valueList;
    }

    private void initValueList() {

        // 初始化按钮上应该显示的数字
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<>();
            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 10) {
                map.put("name", ".");
            } else if (i == 11) {
                map.put("name", String.valueOf(0));
            } else if (i == 12) {
                map.put("name", "");
            }
            valueList.add(map);
        }
    }

    public GridView getGridView() {
        return gridView;
    }

    private void setupView() {
        KeyBoardAdapter keyBoardAdapter = new KeyBoardAdapter(context, valueList);
        gridView.setAdapter(keyBoardAdapter);
        gridView.setOnItemClickListener(onItemClickListener);
    }

    //设置一个edittext 与之关联
    private EditText editText;

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    private OnKeyBordClickListener onKeyBordClickListener;

    public void setOnKeyBordClickListener(OnKeyBordClickListener onKeyBordClickListener) {
        this.onKeyBordClickListener = onKeyBordClickListener;
    }

    public interface OnKeyBordClickListener {
        void onNumClick(int num);

        void onDotClick();

        void onDelClick();
    }

    public void setClickShowKeybord(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickKeybord();
            }
        });
    }

    public void clickKeybord() {
        if (getVisibility() != VISIBLE) {
            show();
        } else {
            hide();
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            if (position < 11 && position != 9) {    //点击0~9按钮
                if (onKeyBordClickListener != null) {
                    onKeyBordClickListener.onNumClick(Integer.parseInt(valueList.get(position).get("name")));
                }
                if (editText != null) {
                    String amount = editText.getText().toString().trim();
                    amount = amount + valueList.get(position).get("name");
                    editText.setText(amount);
                    Editable ea = editText.getText();
                    editText.setSelection(ea.length());
                }
            } else {
                if (position == 9) {      //点击退格键
                    if (onKeyBordClickListener != null) {
                        onKeyBordClickListener.onDotClick();
                    }
                    if (editText != null) {
                        String amount = editText.getText().toString().trim();
                        if (!amount.contains(".")) {
                            amount = amount + valueList.get(position).get("name");
                            editText.setText(amount);
                            Editable ea = editText.getText();
                            editText.setSelection(ea.length());
                        }
                    }
                }

                if (position == 11) {      //点击退格键
                    if (onKeyBordClickListener != null) {
                        onKeyBordClickListener.onDelClick();
                    }
                    if (editText != null) {
                        String amount = editText.getText().toString().trim();
                        if (amount.length() > 0) {
                            amount = amount.substring(0, amount.length() - 1);
                            editText.setText(amount);
                            Editable ea = editText.getText();
                            editText.setSelection(ea.length());
                        }
                    }
                }
            }
        }
    };
}
