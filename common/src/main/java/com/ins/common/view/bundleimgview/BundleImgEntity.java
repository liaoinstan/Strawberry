package com.ins.common.view.bundleimgview;

/**
 * Created by Administrator on 2016/6/12 0012.
 */
public class BundleImgEntity {

    private String path;

    public BundleImgEntity() {
    }

    public BundleImgEntity(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "BundleImgEntity{" +
                "path='" + path + '\'' +
                '}';
    }
}
