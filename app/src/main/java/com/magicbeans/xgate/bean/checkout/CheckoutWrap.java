package com.magicbeans.xgate.bean.checkout;

import com.ins.common.utils.StrUtil;
import com.magicbeans.xgate.bean.postbean.FreeGift;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class CheckoutWrap implements Serializable {
    private CheckoutContent CheckoutContents;
    private OrderSummary OrderSummary;
    private List<GiftOption> GiftOptions;


    public OrderSummary getOrderSummary() {
        return OrderSummary;
    }

    public void setOrderSummary(OrderSummary orderSummary) {
        this.OrderSummary = orderSummary;
    }

    public CheckoutContent getCheckoutContents() {
        return CheckoutContents;
    }

    public void setCheckoutContents(CheckoutContent checkoutContents) {
        CheckoutContents = checkoutContents;
    }

    public List<GiftOption> getGiftOptions() {
        return GiftOptions;
    }

    public void setGiftOptions(List<GiftOption> giftOptions) {
        GiftOptions = giftOptions;
    }

    //################  业务方法 ####################

    public boolean hasGift() {
        return StrUtil.isEmpty(getGiftItems()) ? false : true;
    }

    public List<FreeGift> getGiftItems() {
        if (!StrUtil.isEmpty(GiftOptions)) {
            return GiftOptions.get(0).getGiftItems();
        } else {
            return null;
        }
    }

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
