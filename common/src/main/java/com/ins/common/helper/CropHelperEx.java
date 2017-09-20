package com.ins.common.helper;

import android.view.View;

import com.ins.common.ui.dialog.DialogPopupPhoto;

import java.lang.ref.WeakReference;

/**
 * Created by liaoinstan on 2016/1/19.
 * Update 2017/6/19
 * CropHelper的拓展类，增加了默认弹窗的功能，如果弹窗不需要定制可以使用这个
 * 使用弱引用持有dialog，以便及时回收
 * 详细请参见基类 {@link CropHelper}
 */
public class CropHelperEx extends CropHelper {

    //使用弱引用持有dialog，以便及时回收
    private WeakReference<DialogPopupPhoto> dialogPopupPhoto;

    public CropHelperEx(Object activityOrfragment, CropInterface cropInterface) {
        super(activityOrfragment, cropInterface);

        dialogPopupPhoto = new WeakReference(newInstanceDialog());
    }

    private DialogPopupPhoto newInstanceDialog() {
        DialogPopupPhoto dialog = new DialogPopupPhoto(context);
        dialog.setOnStartListener(new DialogPopupPhoto.OnStartListener() {
            @Override
            public void onPhoneClick(View v) {
                startPhoto();
            }

            @Override
            public void onCameraClick(View v) {
                startCamera();
            }
        });
        return dialog;
    }

    public void showDefaultDialog() {
        if (dialogPopupPhoto.get() == null) {
            dialogPopupPhoto = new WeakReference(newInstanceDialog());
        }
        dialogPopupPhoto.get().show();
    }

    public void hideDefaultDialog() {
        if (dialogPopupPhoto.get() != null) {
            dialogPopupPhoto.get().hide();
        }
    }

    public void dismissDefaultDialog() {
        if (dialogPopupPhoto.get() != null) {
            dialogPopupPhoto.get().dismiss();
        }
    }
}