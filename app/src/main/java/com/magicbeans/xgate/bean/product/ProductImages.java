package com.magicbeans.xgate.bean.product;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/11.
 */

public class ProductImages implements Serializable{
    private String imgSrc;
    private String img350Src;
    private String img700Src;

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getImg350Src() {
        return img350Src;
    }

    public void setImg350Src(String img350Src) {
        this.img350Src = img350Src;
    }

    public String getImg700Src() {
        return img700Src;
    }

    public void setImg700Src(String img700Src) {
        this.img700Src = img700Src;
    }
}
