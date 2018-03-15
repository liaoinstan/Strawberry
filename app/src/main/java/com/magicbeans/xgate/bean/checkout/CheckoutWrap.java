package com.magicbeans.xgate.bean.checkout;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class CheckoutWrap implements Serializable{
    private CheckoutContent CheckoutContents;

    public CheckoutContent getCheckoutContents() {
        return CheckoutContents;
    }

    public void setCheckoutContents(CheckoutContent checkoutContents) {
        CheckoutContents = checkoutContents;
    }

    //################  业务方法 ####################

    public String getNoticeStr() {
        if (CheckoutContents != null) {
            return CheckoutContents.getNoticeStr();
        } else {
            return "";
        }
    }

    public String getImportantNoticeStr() {
        if (CheckoutContents != null) {
            return CheckoutContents.getNoticeByOne(CheckoutContents.getImportantNotices());
        } else {
            return "";
        }
    }
}
