package com.magicbeans.xgate.bean.postbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marie on 2018/1/29.
 */

public class Promotion implements Serializable {
    private String CouponCode;
    private List<FreeGift> SelectedFreeGift;

    //////////////////////////////////////////////

    public void addSelectGift(FreeGift freeGift) {
        if (SelectedFreeGift == null) {
            SelectedFreeGift = new ArrayList<>();
        }
        SelectedFreeGift.add(freeGift);
    }

    //////////////////////////////////////////////

    public Promotion() {
    }

    public Promotion(String couponCode) {
        CouponCode = couponCode;
    }

    public String getCouponCode() {
        return CouponCode;
    }

    public void setCouponCode(String couponCode) {
        CouponCode = couponCode;
    }

    public List<FreeGift> getSelectedFreeGift() {
        return SelectedFreeGift;
    }

    public void setSelectedFreeGift(List<FreeGift> selectedFreeGift) {
        SelectedFreeGift = selectedFreeGift;
    }
}
