package com.magicbeans.xgate.bean;

import com.ins.common.view.bundleimgview.BundleImgEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 */

public class Eva implements Serializable {

    private int id;

    private List<BundleImgEntity> imgs;

    public Eva() {
    }

    public Eva(List<BundleImgEntity> imgs) {
        this.imgs = imgs;
    }

    public List<BundleImgEntity> getImgs() {
        return imgs;
    }

    public void setImgs(List<BundleImgEntity> imgs) {
        this.imgs = imgs;
    }
}
